package com.mini.util;

import com.mini.util.reflect.MiniParameter;
import com.mini.util.reflect.ParameterNameDiscoverer;
import com.mini.util.reflect.ParameterNameDiscovererAsm;
import com.mini.util.scan.ClassScannerJar;
import com.mini.util.scan.ClassScannerPath;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public final class ClassUtil {

    /**
     * 获取指定Class对象的所有属性，包括父类所有私有属性
     * @param clazz 指定Class对象
     * @return 所有属性
     */
    @Nonnull
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            Collections.addAll(fieldList, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 获取指定Class对象的所有方法，包括父类所有私有方法
     * @param clazz 指定Class对象
     * @return 所有方法
     */
    @Nonnull
    public static List<Method> getDeclaredMethods(Class<?> clazz) {
        ArrayList<Method> methodList = new ArrayList<>();
        while (clazz != null && !clazz.equals(Object.class)) {
            Method[] methods = clazz.getDeclaredMethods();
            Collections.addAll(methodList, methods);
            clazz = clazz.getSuperclass();
        }
        return methodList;
    }

    /**
     * 获取普通方法参数列表的名称
     * @param method 普通方法对象
     * @return 参数名称名称
     */
    @Nonnull
    public static String[] getParameterNames(Method method) {
        ParameterNameDiscoverer d = new ParameterNameDiscovererAsm();
        return d.getParameterNames(method);
    }

    /**
     * 获取构造方法参数列表的名称
     * @param constructor 构造方法对象
     * @return 参数名称名称
     */
    @Nonnull
    public static String[] getParameterNames(Constructor<?> constructor) {
        ParameterNameDiscoverer d = new ParameterNameDiscovererAsm();
        return d.getParameterNames(constructor);
    }

    /**
     * 获取普通方法参数列表
     * @param method 普通方法对象
     * @return 普通方法参数列表
     */
    @Nonnull
    public static MiniParameter[] getParameter(Method method) {
        if (method == null) return new MiniParameter[0];
        String[] names = getParameterNames(method);
        Parameter[] params = method.getParameters();
        int length = Math.min(params.length, names.length);
        MiniParameter[] mParams = new MiniParameter[length];
        for (int i = 0; i < mParams.length; i++) {
            mParams[i] = new MiniParameter(params[i], names[i]);
        }
        return mParams;
    }

    /**
     * 获取构造方法参数列表
     * @param constructor 构造方法对象
     * @return 构造方法参数列表
     */
    @Nonnull
    public static MiniParameter[] getParameter(Constructor<?> constructor) {
        if (constructor == null) return new MiniParameter[0];
        String[] names = getParameterNames(constructor);
        Parameter[] params = constructor.getParameters();
        int length = Math.min(params.length, names.length);
        MiniParameter[] mParams = new MiniParameter[length];
        for (int i = 0; i < mParams.length; i++) {
            mParams[i] = new MiniParameter(params[i], names[i]);
        }
        return mParams;
    }

    /**
     * 递归扫描指定包下所有的Class对象
     * <p>扫描必须指定包的名称至少一级</p>
     * @param set         扫描结果容器
     * @param packageName 指定包名
     * @param annotation  指定注解
     */
    public static void scanner(Set<Class<?>> set, String packageName, Class<? extends Annotation> annotation) {
        Collections.addAll(set, new ClassScannerJar().scanner(packageName, annotation).toArray(new Class<?>[0]));
        Collections.addAll(set, new ClassScannerPath().scanner(packageName, annotation).toArray(new Class<?>[0]));
    }

    /**
     * 递归扫描指定包下所有的Class对象
     * <p>扫描必须指定包的名称至少一级</p>
     * @param packageNames 指定包名数组
     * @param annotation   指定注解
     * @return 扫描结果
     */
    public static Set<Class<?>> scanner(String[] packageNames, Class<? extends Annotation> annotation) {
        HashSet<Class<?>> set = new HashSet<>();
        for (String packages : packageNames) {
            scanner(set, packages, annotation);
        }
        return set;
    }

    /**
     * 递归扫描指定包下所有的Class对象
     * <p>扫描必须指定包的名称至少一级</p>
     * @param packageName 指定包名
     * @param annotation  指定注解
     * @return 扫描结果
     */
    public static Set<Class<?>> scanner(String packageName, Class<? extends Annotation> annotation) {
        HashSet<Class<?>> set = new HashSet<>();
        scanner(set, packageName, annotation);
        return set;
    }


}
