package com.mini.web.config;

import com.mini.web.view.ViewFreemarker;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * Web 配置信息读取
 * @author xchao
 */
public interface WebMvcConfigure {
    /** 初始化所有配置信息 */
    default void onStartup(ServletContext servletContext, ApplicationContext applicationContext) {}


    /**
     * ActionInvocationProxy 配置
     * @param configure 配置信息
     */
    default void actionInvocationProxyConfigure(ActionInvocationProxyConfigure configure) {}


    /**
     * Action接收的参数配置
     * @param configure 配置信息
     */
    default void argumentResolverConfigure(ArgumentResolverConfigure configure) {}

    /**
     * 渲染器配置
     * @param configure 配置信息
     */
    default void modelFactoryConfigure(ModelFactoryConfigure configure) { }


    /**
     * HttpServlet 配置
     * @param configure 配置信息
     * @return 默认 HttpServlet
     */
    default void httpServletConfigure(HttpServletConfigure configure) { }


    /**
     * 拦截器配置
     * @param configure 配置信息
     */
    default void interceptorConfigure(InterceptorConfigure configure) {
    }


    /**
     * 监听器配置
     * @param configure 配置信息
     */
    default void listenerConfigure(ListenerConfigure configure) {
    }


    /**
     * 过虑器配置
     * @param configure 配置信息
     */
    default void filterConfigure(FilterConfigure configure) { }

    /**
     * 视图配置
     * @param configure 配置信息
     */
    default void viewConfigure(ViewConfigure configure) {
        configure.setViewClass(ViewFreemarker.class);
    }
}
