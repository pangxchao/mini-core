package com.mini.web.servlet;

import com.google.auto.service.AutoService;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mini.inject.PropertiesModule;
import com.mini.logger.Logger;
import com.mini.util.MiniProperties;
import com.mini.util.ObjectUtil;
import com.mini.web.config.*;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.Objects;
import java.util.Set;

import static com.mini.logger.LoggerFactory.getLogger;
import static java.util.EnumSet.copyOf;

@Singleton
@HandlesTypes(WebMvcConfigure.class)
@AutoService(ServletContainerInitializer.class)
public final class MiniServletInitializer implements ServletContainerInitializer {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);
    private static final String FILE_NAME = "mini-application.properties";


    @Override
    public final void onStartup(Set<Class<?>> initializer, ServletContext context) {
        try {
            Class<? extends WebMvcConfigure> clazz = getClazz(initializer);
            Injector parent = Guice.createInjector(new PropertiesModule() {
                protected void configures(Binder binder, ClassLoader loader) throws Exception {
                    InputStream stream = loader.getResourceAsStream(FILE_NAME);
                    setPropertyIfAbsent(new MiniProperties().miniLoad(stream));
                    // 系统对象注入
                    bind(HttpServlet.class).to(DispatcherHttpServlet.class);
                    bind(ServletContext.class).toInstance(context);
                    bind(WebMvcConfigure.class).to(clazz);
                    bind(DispatcherHttpServlet.class);
                }
            });

            // 初始化依赖注入配置
            WebMvcConfigure configure = parent.getInstance(WebMvcConfigure.class);
            Injector injector = parent.createChildInjector(configure);

            // 向 ServletContext 注册 Servlet
            this.registerServlet(injector, context);
            // 向 ServletContext 注册 Listener
            this.registerListener(injector, context);
            // 向 ServletContext 注册 Filter
            this.registerFilter(injector, context);
        } catch (Exception | Error e) {
            LOGGER.error("Initializer Error!", e);
        }
    }

    /**
     * 获取并验证 WebMvcConfigurer Class
     * @param initializer 初始化Class列表
     * @return WebMvcConfigurer.class
     */
    private Class<? extends WebMvcConfigure> getClazz(Set<Class<?>> initializer) {
        // 验证必须有一个且仅有一个配置类
        Objects.requireNonNull(initializer, "No configuration class");
        ObjectUtil.require(initializer.size() == 1, "Configuration can only have one");

        // 验证当前类是否为 WebMvcConfigurer 类
        Class<?> clazz = initializer.toArray(new Class<?>[0])[0];
        if (!WebMvcConfigure.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("No configuration class");
        }

        // 验证 WebMvcConfigurer 配置必须是单例
        if (clazz.getAnnotation(com.google.inject.Singleton.class) == null) {
            if (clazz.getAnnotation(javax.inject.Singleton.class) == null) {
                throw new RuntimeException("WebMvcConfigurer must be a Singleton");
            }
        }

        // 读取属性文件并初始化、注入基础实例信息
        return clazz.asSubclass(WebMvcConfigure.class);
    }

    /**
     * 注册 Servlet
     * @param injector Injector 对象
     * @param context  ServletContext
     */
    private void registerServlet(Injector injector, ServletContext context) {
        HttpServletConfigure configure = injector.getInstance(HttpServletConfigure.class);
        MultipartConfigure multipart = injector.getInstance(MultipartConfigure.class);
        for (HttpServletConfigure.HttpServletElement element : configure.getElements()) {
            HttpServlet servlet = injector.getInstance(element.getServletClass());
            var register = context.addServlet(element.getServletName(), servlet);
            register.setAsyncSupported(element.isAsyncSupported());
            register.addMapping(element.getUrlPatterns());

            if (!element.isFileUploadSupported()) continue;
            if (multipart.getMultipartConfigElement() == null) continue;
            register.setMultipartConfig(multipart.getMultipartConfigElement());
        }
    }

    /**
     * 注册 Listener
     * @param injector Injector 对象
     * @param context  ServletContext
     */
    private void registerListener(Injector injector, ServletContext context) {
        ListenerConfigure configure = injector.getInstance(ListenerConfigure.class);
        for (Class<? extends EventListener> element : configure.getListeners()) {
            context.addListener(injector.getInstance(element));
        }
    }

    /**
     * 注册 Filter
     * @param injector Injector 对象
     * @param context  ServletContext
     */
    private void registerFilter(Injector injector, ServletContext context) {
        FilterConfigure configure = injector.getInstance(FilterConfigure.class);
        for (FilterConfigure.FilterElement element : configure.getElements()) {
            Filter filter = injector.getInstance(element.getFilterClass());
            var register = context.addFilter(element.getFilterName(), filter);
            EnumSet<DispatcherType> enumSet = copyOf(element.getDispatcherTypes());
            register.addMappingForUrlPatterns(enumSet, element.isMatchAfter(), element.getUrlPatterns());
        }
    }
}
