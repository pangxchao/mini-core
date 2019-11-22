package com.mini.web.interceptor;

import com.mini.web.annotation.Action;
import com.mini.web.model.IModel;
import com.mini.web.util.RequestParameter;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.List;

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
     * @param view     视图实现类
     * @return 数据模型实现类型
     */
    @Nonnull
    IModel<?> getModel(IView view);

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
