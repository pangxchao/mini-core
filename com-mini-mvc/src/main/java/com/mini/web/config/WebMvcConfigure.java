package com.mini.web.config;

import org.springframework.context.annotation.PropertySource;

import javax.servlet.ServletContext;

/**
 * Web 配置信息读取
 * @author xchao
 */
@PropertySource("classpath:application.properties")
public interface WebMvcConfigure {
    /**
     * 初始化所有配置信息
     * @param servletContext ServletContext 对象
     */
    default void onStartup(ServletContext servletContext) {}

    /**
     * ActionInvocationProxy 配置
     * @param configure 配置信息
     */
    default void actionInvocationProxyConfigure(ActionInvocationProxyConfigure configure) {}


    /**
     * 参数解析器配置
     * @param configure 配置信息
     */
    default void argumentResolverConfigure(ArgumentResolverConfigure configure) {}

    /**
     * 渲染器配置
     * @param configure 配置信息
     */
    default void modelFactoryConfigure(ModelFactoryConfigure configure) {}

    /**
     * HttpServlet 配置
     * @param configure 配置信息
     */
    default void httpServletConfigure(HttpServletConfigure configure) {}


    /**
     * 拦截器配置
     * @param configure 配置信息
     */
    default void interceptorConfigure(InterceptorConfigure configure) {}


    /**
     * 监听器配置
     * @param configure 配置信息
     */
    default void listenerConfigure(ListenerConfigure configure) {}


    /**
     * 过虑器配置
     * @param configure 配置信息
     */
    default void filterConfigure(FilterConfigure configure) {}

    /**
     * 视图配置
     * @param configure 配置信息
     */
    default void viewConfigure(ViewConfigure configure) {}
}
