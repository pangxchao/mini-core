package com.mini.core.mvc.support;

import com.mini.core.mvc.annotation.Action;
import com.mini.core.mvc.argument.ArgumentResolver;
import com.mini.core.mvc.interceptor.ActionInterceptor;
import com.mini.core.mvc.interceptor.ActionInvocation;
import com.mini.core.mvc.model.IModel;
import com.mini.core.mvc.support.config.Configures;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.EventListener;
import java.util.concurrent.CopyOnWriteArrayList;


public interface ActionSupportProxy {
    /**
     * 获取Controller类Class对象
     *
     * @return Class对象
     */
    @NotNull
    Class<?> getClazz();

    /**
     * 获取目标方法对象
     *
     * @return 目标方法对象
     */
    @NotNull
    Method getMethod();

    /**
     * 获取数据模型实现类型
     *
     * @return 数据模型实现类型
     */
    @NotNull
    IModel<?> getModel();

    /**
     * 获取控制器支持的方法
     *
     * @return 控制器支持的方法
     */
    @NotNull
    Action.Method[] getSupportMethod();

    /**
     * 获取所有拦截器对象
     *
     * @return 拦截器对象
     */
    @NotNull
    CopyOnWriteArrayList<ActionInterceptor> getInterceptors();

    /**
     * 获取目标方法的所有参数信息
     *
     * @return 所有参数信息
     */
    @NotNull
    MethodParameter[] getParameters();

    /**
     * 获取参数处理器列表
     *
     * @return 参数处理器列表
     */
    @NotNull
    ParameterHandler[] getParameterHandlers();

    /**
     * 获取 Action 视图路径
     *
     * @return Action 视图路径
     */
    String getViewPath();

    /**
     * 获取方法的请求路径
     *
     * @return 方法请求路径
     */
    String getRequestUri();

    final class ParameterHandler implements EventListener {
        private final MethodParameter parameter;
        private final ArgumentResolver resolver;

        public ParameterHandler(@NotNull ArgumentResolver resolver, @NotNull MethodParameter parameter) {
            this.parameter = parameter;
            this.resolver = resolver;
        }

        public final Object getValue(ActionInvocation invocation) {
            return resolver.getValue(parameter, invocation);
        }
    }
}
