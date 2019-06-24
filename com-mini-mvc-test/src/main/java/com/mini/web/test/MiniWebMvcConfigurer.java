package com.mini.web.test;

import com.mini.inject.annotation.Scanning;
import com.mini.web.annotation.TransactionEnable;
import com.mini.web.config.ServletHandlerConfigure;
import com.mini.web.config.WebMvcConfigure;

import javax.inject.Singleton;

@Singleton
@TransactionEnable
@Scanning("com.mini.web.test")
public class MiniWebMvcConfigurer extends WebMvcConfigure {

    @Override
    protected void servletHandlerConfigure(ServletHandlerConfigure configurer) {
        super.servletHandlerConfigure(configurer);
        configurer.addUrlPatterns("/front/*", "*.htm");
    }


}
