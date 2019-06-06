package com.mini.spring;

import com.mini.spring.argument.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;

/**
 * MiniWebInitializer.java
 * @author xchao
 */
@EnableTransactionManagement
public abstract class MiniWebInitializer extends SpringBootServletInitializer implements WebMvcConfigurer {

    private static ServletContext servletContext;

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onStartup(ServletContext servletContext) throws ServletException {
        MiniWebInitializer.servletContext = servletContext;
        super.onStartup(servletContext);
    }

    @Override
    protected final SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(getApplicationClass());
    }

    /**
     * 获取默认主配置类
     * @return 主配置类
     */
    protected abstract Class<?> getApplicationClass();

    /**
     * 自定义参数
     * @param argumentResolvers 自定义参数
     */
    @OverridingMethodsMustInvokeSuper
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageModelViewMethodArgumentResolver());
        argumentResolvers.add(new ListJsonModelMethodArgumentResolver());
        argumentResolvers.add(new MapJsonModelMethodArgumentResolver());
        argumentResolvers.add(new StreamModelMethodArgumentResolver());
        argumentResolvers.add(new PagingMethodArgumentResolver());
        argumentResolvers.add(new UserMethodArgumentResolver());
    }

    /**
     * 设置访问后缀匹配方式
     * @param configurer 访问配置
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true);
        configurer.setUseTrailingSlashMatch(true);
    }

    /**
     * 注册访问后缀
     * @param registry 注册器
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*.htm");
    }

    /**
     * 注册拦截器
     * @param registry 注册器
     */
    @OverridingMethodsMustInvokeSuper
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addWebRequestInterceptor(new MiniWebRequestInterceptor()).addPathPatterns("/*");
        //registry.addInterceptor(new MiniHandlerInterceptor()).addPathPatterns("/*");
    }

    /**
     * 获取全局上下文
     * @return 全局上下文
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * 得到WEB应用的物理地址
     * @return 得到WEB应用的实际物理根地址
     */
    public static String getWebRealPath() {
        if (servletContext == null) return "/";
        return servletContext.getRealPath("/") + "/";
    }

    /**
     * 获取该问该WEB项目的根URL
     * @return WEB项目的根UR
     */
    public static String getWebRootPath() {
        if (servletContext == null) return "/";
        return servletContext.getContextPath() + "/";
    }

}
