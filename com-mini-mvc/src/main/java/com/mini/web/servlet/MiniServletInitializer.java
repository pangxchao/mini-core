package com.mini.web.servlet;

import com.google.auto.service.AutoService;
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.mini.inject.annotation.PropertySource;
import com.mini.inject.annotation.PropertySources;
import com.mini.logger.Logger;
import com.mini.util.MiniProperties;
import com.mini.util.ObjectUtil;
import com.mini.web.config.Configure;
import com.mini.web.config.WebMvcConfigure;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import static com.google.inject.Guice.createInjector;
import static com.mini.logger.LoggerFactory.getLogger;
import static java.util.Objects.requireNonNull;

@Named
@Singleton
@HandlesTypes(WebMvcConfigure.class)
@AutoService(ServletContainerInitializer.class)
public final class MiniServletInitializer implements ServletContainerInitializer {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);

    // 默认父容器的Module
    @PropertySource("mini-application.properties")
    private static class InitConfigModule implements Module {
        private final Set<Class<?>> classes = new HashSet<>();
        private final ServletContext context;

        public InitConfigModule(ServletContext context, Set<Class<? extends WebMvcConfigure>> classes) {
            this.classes.add(InitConfigModule.class);
            this.classes.addAll(classes);
            this.context = context;
        }

        @Override
        public synchronized void configure(Binder binder) {
            binder.bind(ServletContext.class).toInstance(context);
            MiniProperties properties = new MiniProperties();
            classes.forEach(c -> properties.putAll(createProperties(c)));
            Names.bindProperties(binder, properties);
        }

        private MiniProperties createProperties(Class<?> clazz) {
            PropertySources sources = clazz.getAnnotation(PropertySources.class);
            MiniProperties properties = MiniProperties.createProperties(sources);
            PropertySource source = clazz.getAnnotation(PropertySource.class);
            properties.putAll(MiniProperties.createProperties(source));
            return properties;
        }
    }

    @Override
    public final void onStartup(Set<Class<?>> initializer, ServletContext context) {
        try {
            // 获取配置文件的Class对象
            Set<Class<? extends WebMvcConfigure>> classes = getClasses(initializer);
            ObjectUtil.require(!classes.isEmpty(), "WebMvcConfigure can not be empty.");

            // 初始化第一个父容器
            Injector parent = createInjector(new InitConfigModule(context, classes));
            Configure configure = parent.getInstance(Configure.class);
            requireNonNull(configure, "Configure can not be null");

            // 获取所有自定义配置信息
            WebMvcConfigure[] configures = getClasses(initializer).stream().map(clazz -> {
                return Objects.requireNonNull(parent.getInstance(clazz)); //
            }).toArray(WebMvcConfigure[]::new);

            // 初始化依赖注入的主容器
            Injector injector = parent.createChildInjector(configures);
            configure.setInjector(injector);

            // 注册 Listener、Servlet、Filter
            registerListener(injector, configure, context);
            registerServlet(injector, configure, context);
            registerFilter(injector, configure, context);
        } catch (Exception | Error e) {
            LOGGER.error("Initializer Error!", e);
        }
    }

    private Set<Class<? extends WebMvcConfigure>> getClasses(Set<Class<?>> initializer) {
        if (initializer == null || initializer.isEmpty()) return new CopyOnWriteArraySet<>();
        AbstractSet<Class<? extends WebMvcConfigure>> classes = new CopyOnWriteArraySet<>();
        initializer.stream().filter(WebMvcConfigure.class::isAssignableFrom).forEach(c -> {
            classes.add(c.asSubclass(WebMvcConfigure.class)); //
        });

        return classes;
    }

    // 注册Servlet
    private void registerServlet(Injector injector, Configure configure, ServletContext context) {
        configure.getServlets().forEach(element -> {
            String servletName = element.getServlet().getName();
            var servlet = injector.getInstance(element.getServlet());
            var register = context.addServlet(servletName, servlet);
            register.setAsyncSupported(element.isAsyncSupported());
            register.addMapping(element.getUrlPatterns());
            // 文件上传相关配置
            MultipartConfigElement e = element.getMultipartConfigElement();
            Optional.ofNullable(e).ifPresent(register::setMultipartConfig);
        });
    }

    // 注册监听器
    private void registerListener(Injector injector, Configure configure, ServletContext context) {
        configure.getListeners().forEach(e -> context.addListener(injector.getInstance(e)));
    }

    // 注册过虑器
    private void registerFilter(Injector injector, Configure configure, ServletContext context) {
        configure.getFilters().forEach(element -> {
            String filterName = element.getFilter().getName();
            var filter = injector.getInstance(element.getFilter());
            Dynamic register = context.addFilter(filterName, filter);
            EnumSet<DispatcherType> enumSet = EnumSet.copyOf(element.getDispatcherTypes());
            register.addMappingForUrlPatterns(enumSet, element.isMatchAfter(), element.getUrlPatterns());
        });
    }
}
