package com.mini.core.util.reflect;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public interface ParameterNameDiscoverer {

    /**
     * 获取普通方法 的参数名称
     * @param method 普通方法
     * @return 参数名称数组
     */
    @Nonnull
    String[] getParameterNames(Method method);

    /**
     * 获取构造方法的参数名称
     * @param constructor 构造方法
     * @return 参数名称数组
     */
    @Nonnull
    String[] getParameterNames(Constructor<?> constructor);
}
