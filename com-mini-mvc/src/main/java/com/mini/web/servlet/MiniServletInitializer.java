package com.mini.web.servlet;

import com.google.auto.service.AutoService;
import com.mini.logger.Logger;
import com.mini.web.config.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServlet;
import java.util.*;

import static com.mini.logger.LoggerFactory.getLogger;
import static java.util.EnumSet.copyOf;

@Named
@Singleton
@HandlesTypes(WebMvcConfigure.class)
@AutoService(ServletContainerInitializer.class)
public final class MiniServletInitializer implements ServletContainerInitializer {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);
    private ServletContext servletContext;

    @Bean
    @Singleton
    @Named("servletContext")
    public ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public final void onStartup(Set<Class<?>> initializer, ServletContext servletContext) {
        try {
            this.servletContext = servletContext;




            //
            //Objects.requireNonNull(initializer, "initializer class can not be null");
            //Collections.addAll(initializer, WebMvcConfigureDefault.class, this.getClass());
            //AnnotationConfigApplicationContext context = getApplicationContext(initializer);
            //WebMvcConfigureDefault configures = context.getBean(WebMvcConfigureDefault.class);
            //Objects.requireNonNull(configures, "WebMvcConfigureDefault can not be null");
            //
            //// 初始化配置信息
            //configures.onStartup(servletContext);
            //
            //// 向 ServletContext 注册 Listener
            //this.registerListener(context, servletContext);
            //// 向 ServletContext 注册 Servlet
            //this.registerServlet(context, servletContext);
            //// 向 ServletContext 注册 Filter
            //this.registerFilter(context, servletContext);
        } catch (Exception | Error e) {
            LOGGER.error("Initializer Error!", e);
        }
    }

    /**
     * 获取 ApplicationContext 容器
     * @param initializer 配置类
     * @return ApplicationContext 容器
     */
    private AnnotationConfigApplicationContext getApplicationContext(@Nonnull Set<Class<?>> initializer) {
        return new AnnotationConfigApplicationContext(initializer.toArray(new Class<?>[0]));
    }

    /**
     * 注册 Servlet
     * @param context        ApplicationContext 对象
     * @param servletContext ServletContext
     */
    private void registerServlet(ApplicationContext context, ServletContext servletContext) {
        HttpServletConfigure configure = context.getBean(HttpServletConfigure.class);
        for (HttpServletConfigure.HttpServletElement element : configure.getElements()) {
            HttpServlet servlet = context.getBean(element.getServletClass());
            var register = servletContext.addServlet(element.getServletName(), servlet);
            register.setAsyncSupported(element.isAsyncSupported());
            register.addMapping(element.getUrlPatterns());

            if (!element.isFileUploadSupported()) continue;
            if (element.getMultipartConfigElement() == null) continue;
            register.setMultipartConfig(element.getMultipartConfigElement());
        }
    }

    /**
     * 注册 Listener
     * @param context        ApplicationContext 对象
     * @param servletContext ServletContext
     */
    private void registerListener(ApplicationContext context, ServletContext servletContext) {
        ListenerConfigure configure = context.getBean(ListenerConfigure.class);
        for (Class<? extends EventListener> element : configure.getListeners()) {
            servletContext.addListener(context.getBean(element));
        }
    }

    /**
     * 注册 Filter
     * @param context        ApplicationContext 对象
     * @param servletContext ServletContext
     */
    private void registerFilter(ApplicationContext context, ServletContext servletContext) {
        FilterConfigure configure = context.getBean(FilterConfigure.class);
        for (FilterConfigure.FilterElement element : configure.getElements()) {
            Filter filter = context.getBean(element.getFilterClass());
            var register = servletContext.addFilter(element.getFilterName(), filter);
            EnumSet<DispatcherType> enumSet = copyOf(element.getDispatcherTypes());
            register.addMappingForUrlPatterns(enumSet, element.isMatchAfter(), element.getUrlPatterns());
        }
    }
}
