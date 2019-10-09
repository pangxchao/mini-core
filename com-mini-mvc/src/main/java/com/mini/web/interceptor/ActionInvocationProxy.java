package com.mini.web.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Nonnull;

import com.mini.web.annotation.Action;
import com.mini.web.model.IModel;
import com.mini.web.util.RequestParameter;

public interface ActionInvocationProxy {

    /**
     * 获取目标方法对象
     * @return 目标方法对象
     */
    @Nonnull
    Method getMethod();

    /**
     * 获取Controller类Class对象
     * @return Class对象
     */
    @Nonnull
    Class<?> getClazz();


    /**
     * 获取数据模型实现类型
     * @return 数据模型实现类型
     */
    @Nonnull
    Class<? extends IModel<?>> getModel();

    /**
     * 获取控制器支持的方法
     * @return 控制器支持的方法
     */
    @Nonnull
    Action.Method[] getSupportMethod();

    /**
     * 获取所有拦截器对象
     * @return 拦截器对象
     */
    @Nonnull
    List<Class<? extends ActionInterceptor>> getInterceptors();

    /**
     * 获取目标方法的所有参数信息
     * @return 所有参数信息
     */
    @Nonnull
    RequestParameter[] getParameters();

    /**
     * 获取 Action 视图路径
     * @return Action 视图路径
     */
    String getViewPath();
}
