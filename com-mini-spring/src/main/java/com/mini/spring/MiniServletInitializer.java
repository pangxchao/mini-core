package com.mini.spring;

import com.mini.spring.argument.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.util.List;

@Component("initializer")
public abstract class MiniServletInitializer extends SpringBootServletInitializer implements WebMvcConfigurer {

    private static ServletContext servletContext;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        MiniServletInitializer.servletContext = servletContext;
        super.onStartup(servletContext);
    }

    @Override
    protected final SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(getApplicationClass());
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
     * 获取jndi数据源
     * @param jndiName jndi name
     * @return 数据源
     */
    protected static DataSource getJndiDataSource(String jndiName) {
        return new JndiDataSourceLookup().getDataSource(jndiName);
    }

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
