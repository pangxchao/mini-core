package com.mini.util.reflect;


import com.mini.logger.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static com.mini.logger.LoggerFactory.getLogger;
import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.util.StringUtil.eq;
import static com.mini.util.StringUtil.join;
import static java.lang.ClassLoader.getSystemClassLoader;
import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;
import static org.objectweb.asm.Type.getConstructorDescriptor;
import static org.objectweb.asm.Type.getMethodDescriptor;


public class ParameterNameDiscovererAsm implements ParameterNameDiscoverer, Serializable {
    private static final Logger logger = getLogger(ParameterNameDiscovererAsm.class);
    private static final long serialVersionUID = 6718496659106769030L;

    @Nonnull
    @Override
    public String[] getParameterNames(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) return new String[0];

        try (InputStream stream = getClassAsStream(method.getDeclaringClass())) {
            return getParameterNames(stream, getMethodDescriptor(method));
        } catch (Exception | Error e) {
            logger.error("ERROR!", e);
        }
        return new String[0];
    }

    @Nonnull
    @Override
    public String[] getParameterNames(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        if (parameterTypes.length == 0) return new String[0];

        try (InputStream stream = getClassAsStream(constructor.getDeclaringClass())) {
            return getParameterNames(stream, getConstructorDescriptor(constructor));
        } catch (Exception | Error e) {
            logger.error("ERROR!", e);
        }
        return new String[0];
    }

    /**
     * 根据方法的 declaringClass 获取该类的流信息
     * @param declaringClass declaringClass 类
     * @return declaringClass 类流信息
     */
    @Nonnull
    protected final InputStream getClassAsStream(Class<?> declaringClass) {
        ClassLoader classLoader = declaringClass.getClassLoader();
        classLoader = defIfNull(classLoader, getSystemClassLoader());
        return getClassAsStream(classLoader, declaringClass.getName());
    }

    /**
     * 根据方法的 declaringClass 的类名称获取该类流信息
     * @param classLoader 类加载器
     * @param className   declaringClass 类名称
     * @return declaringClass 类流信息
     */
    @Nonnull
    protected final InputStream getClassAsStream(ClassLoader classLoader, String className) {
        String fileName = join("", className.replace('.', '/'), ".class");
        InputStream stream = classLoader.getResourceAsStream(fileName);
        return defIfNull(stream, getClass().getResourceAsStream(fileName));
    }

    /**
     * 获取参数名列表辅助方法
     * @param stream   方法所在类的流信息
     * @param describe 方法描述
     * @return 参数名称列表
     */
    @Nonnull
    private String[] getParameterNames(InputStream stream, String describe) throws IOException {
        return this.getClassNode(stream).methods.stream()
                .filter(node -> eq(describe, node.desc) && node.parameters != null)
                .flatMap(node -> node.parameters.stream())
                .map(parameter -> parameter.name)
                .toArray(String[]::new);
    }

    /**
     * 获取 ClassNode 对象
     * @param stream 类流信息
     * @return ClassNode 对象
     */
    private ClassNode getClassNode(InputStream stream) throws IOException {
        ClassReader r = new ClassReader(stream);
        ClassNode node = new ClassNode();
        r.accept(node, EXPAND_FRAMES);
        return node;
    }
}
