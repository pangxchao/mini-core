package com.mini.util.ioc;

import com.mini.util.ioc.annotation.Autowired;
import com.mini.util.ioc.annotation.Component;
import com.mini.util.lang.ClassUtil;
import com.mini.util.lang.MiniMap;
import com.mini.util.lang.StringUtil;
import com.mini.util.lang.reflect.MiniParameter;
import com.mini.util.logger.Logger;
import com.mini.util.logger.LoggerFactory;
import com.mini.util.validate.ValidateUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ApplicationContextAnnotation implements ApplicationContext {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextAnnotation.class);
    private final MiniMap<String> beans = new MiniMap<>();

    public ApplicationContextAnnotation(String[] packageNames) {
        try {
            HashSet<Class<?>> set = new HashSet<>();
            for (String packageName : packageNames) {
                ClassUtil.scanner(set, packageName, Component.class);
            }

            Map<String, BeanFactory> map = new HashMap<>();
            for (Class<?> clazz : set) {
                // 接口不处理
                if (clazz.isInterface()) {
                    continue;
                }

                // 注解为空时不处理
                Component component = clazz.getAnnotation(Component.class);
                if (component == null) {
                    continue;
                }
                // 获取Bean名称
                String name = StringUtil.def(component.value(), clazz.getName());
                map.put(name, new BeanFactory() {
                    private boolean isCross = false;
                    private Object instance;

                    public Class<?> getBeanType() {
                        return clazz;
                    }

                    public String getBeanName() {
                        return name;
                    }

                    public synchronized Object getBean() throws Exception {
                        // 已经初始化完成的直接返回
                        if (instance != null) return instance;

                        // Beans 不能交叉注入
                        ValidateUtil.validate(!isCross, 1, "Beans cannot be cross-injected: " + clazz);
                        isCross = true;

                        // Beans 名称不能重复
                        String name = getBeanName();
                        Object bean = ApplicationContextAnnotation.this.getBean(name, clazz);
                        ValidateUtil.validate(bean == null, 1, "Beans already exist: " + name);

                        // 循环调用构造方法，有成功时退出
                        for (Constructor<?> c : clazz.getConstructors()) {
                            MiniParameter[] parameters = ClassUtil.getParameter(c);
                            Object[] values = new Object[parameters.length];

                            // 设置初始化标识
                            boolean unInit = false;
                            for (int i = 0; i < parameters.length; i++) {
                                String n = parameters[i].getName();
                                // 没找到Factory对象不能初始化
                                BeanFactory factory = map.get(n);
                                if (factory == null) {
                                    unInit = true;
                                    break;
                                }
                                // 参数值为空不能初始化
                                values[i] = factory.getBean();
                                if (values[i] == null) {
                                    unInit = true;
                                    break;
                                }
                            }
                            // 不能初始化
                            if (unInit) continue;

                            // 创建对象实例
                            instance = c.newInstance(values);
                            return instance;
                        }
                        return null;
                    }
                });
            }

            // 初始化构造方法注入的类和属性
            for (Map.Entry<String, BeanFactory> entry : map.entrySet()) {
                beans.put(entry.getKey(), entry.getValue().getBean());
            }

            // 初始化属性注入的属性
            for (BeanFactory factory : map.values()) {
                for (Field field : ClassUtil.getAllField(factory.getBeanType())) {
                    // 获取注解信息，没有自动注入注解的属性不处理
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    if (autowired == null) continue;

                    // 获取该属性对应的Bean的值
                    Object value = getBean(field.getName(), field.getType());
                    if (value == null) continue;

                    // 获取当前类对应的实例，实例为空不处理
                    Object instance = factory.getBean();
                    if (instance == null) continue;

                    // 设置跳过权限验证
                    field.setAccessible(true);

                    // 获取该属性的值，值不为空时不处理
                    Object val = field.get(instance);
                    if (val != null) continue;

                    // 设置属性值
                    field.set(instance, value);
                }
            }

            // 初始化方法注入的属性
            for (BeanFactory factory : map.values()) {
                for (Method method : factory.getBeanType().getMethods()) {
                    // 获取注解信息，没有自动注入注解的属性不处理
                    Autowired autowired = method.getAnnotation(Autowired.class);
                    if (autowired == null) continue;

                    // 获取当前类对应的实例，实例为空不处理
                    Object instance = factory.getBean();
                    if (instance == null) continue;

                    // 获取方法参数列表,参数长度为0时不处理
                    MiniParameter[] parameters = ClassUtil.getParameter(method);
                    if (parameters.length == 0) continue;

                    // 参数方法参数对应的值组成数组
                    Object[] values = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        BeanFactory f = map.get(parameters[i].getName());
                        if (f == null) continue;
                        // 设置参数修正
                        values[i] = f.getBean();
                    }

                    // 设置方法注入
                    method.invoke(instance, values);
                }
            }
        } catch (Exception | Error e) {
            logger.error("ERROR!", e);
        }
    }

    @Override
    public <T> T getBean(String name, Class<T> clazz) {
        return beans.getAs(name, clazz);
    }
}
