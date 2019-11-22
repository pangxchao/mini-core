package com.mini.core.util;

import com.mini.core.util.reflect.*;
import org.apache.commons.lang3.ClassUtils;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ClassUtil extends ClassUtils {
    /**
     * 获取普通方法参数列表的名称
     * @param method 普通方法对象
     * @return 参数名称名称
     */
    @Nonnull
    @SuppressWarnings("WeakerAccess")
    public static String[] getParameterNamesByAsm(Method method) {
        ParameterNameDiscoverer d = new ParameterNameDiscovererAsm();
        return d.getParameterNames(method);
    }

    /**
     * 获取构造方法参数列表的名称
     * @param constructor 构造方法对象
     * @return 参数名称名称
     */
    @Nonnull
    @SuppressWarnings("WeakerAccess")
    public static String[] getParameterNamesByAsm(Constructor<?> constructor) {
        ParameterNameDiscoverer d = new ParameterNameDiscovererAsm();
        return d.getParameterNames(constructor);
    }

    /**
     * 获取普通方法参数列表
     * @param method 普通方法对象
     * @return 普通方法参数列表
     */
    @Nonnull
    @SuppressWarnings("DuplicatedCode")
    public static MiniParameter[] getParameterByAsm(Method method) {
        if (method == null) {
            return new MiniParameter[0];
        }
        String[] names = getParameterNamesByAsm(method);
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
    @SuppressWarnings("DuplicatedCode")
    public static MiniParameter[] getParameterByAsm(Constructor<?> constructor) {
        if (constructor == null) {
            return new MiniParameter[0];
        }
        String[] names = getParameterNamesByAsm(constructor);
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
     * <p>
     * 扫描必须指定包的名称至少一级
     * </p>
     * @param set         扫描结果容器
     * @param packageName 指定包名
     * @param annotation  指定注解
     */
    @SuppressWarnings("WeakerAccess")
    public static void scanner(Set<Class<?>> set, String packageName, Class<? extends Annotation> annotation) {
        Collections.addAll(set, new ClassScannerJar().scanner(packageName, annotation).toArray(new Class<?>[0]));
        Collections.addAll(set, new ClassScannerPath().scanner(packageName, annotation).toArray(new Class<?>[0]));
    }

    /**
     * 递归扫描指定包下所有的Class对象
     * <p>
     * 扫描必须指定包的名称至少一级
     * </p>
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
     * <p>
     * 扫描必须指定包的名称至少一级
     * </p>
     * @param packageName 指定包名
     * @param annotation  指定注解
     * @return 扫描结果
     */
    @Nonnull
    public static Set<Class<?>> scanner(String packageName, Class<? extends Annotation> annotation) {
        HashSet<Class<?>> set = new HashSet<>();
        scanner(set, packageName, annotation);
        return set;
    }
}
