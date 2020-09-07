package com.mini.core.mvc.support;

import com.mini.core.mvc.support.config.Configures;

import javax.servlet.ServletContext;

public interface WebApplicationInitializer {

    /**
     * 该方法在自动注入之后调用，使用时需要注意顺序
     *
     * @param context   ServletContext 对象
     * @param configure 配置信息
     */
    default void onStartup(ServletContext context, Configures configure) {
    }
}
