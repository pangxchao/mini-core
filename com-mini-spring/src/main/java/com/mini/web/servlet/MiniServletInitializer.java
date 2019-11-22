package com.mini.web.servlet;

import com.google.auto.service.AutoService;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.mini.inject.annotation.PropertySource;
import com.mini.inject.annotation.PropertySources;
import com.mini.logger.Logger;
import com.mini.util.MiniProperties;
import com.mini.util.ObjectUtil;
import com.mini.web.config.Configure;
import com.mini.web.config.WebMvcConfigure;

import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

import static com.mini.logger.LoggerFactory.getLogger;
import static com.mini.util.MiniProperties.createProperties;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;


@Singleton
@HandlesTypes(WebMvcConfigure.class)
@AutoService(ServletContainerInitializer.class)
public final class MiniServletInitializer implements ServletContainerInitializer {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);
    private static final String PRO_NAME = "mini-application.properties";

    // 默认父容器的Module
    public final static class ConfigModule implements Module {
        private final List<WebMvcConfigure> configures = new ArrayList<>();
        private final ServletContext servletContext;

        ConfigModule(ServletContext servletContext, List<WebMvcConfigure> configures) {
            this.servletContext = servletContext;
            this.configures.addAll(configures);
        }

        @Override
        public synchronized void configure(Binder binder) {
            binder.bind(ServletContext.class).toInstance(servletContext);
            MiniProperties properties = createProperties(PRO_NAME);
            configures.forEach(c -> properties(properties, c));
            Names.bindProperties(binder, properties);
            configures.forEach(binder::install);

            // 注入初始化参数
            ServletContext ctx = ConfigModule.this.servletContext;
            Enumeration<String> names = ctx.getInitParameterNames();
            names.asIterator().forEachRemaining(name -> {
                String v = ctx.getInitParameter(name);
                var b = binder.bind(String.class);
                Named named = Names.named(name);
                var a = b.annotatedWith(named);
                a.toInstance(v);
            });
        }

        private synchronized void properties(MiniProperties properties, WebMvcConfigure configure) {
            PropertySources sources = configure.getAnnotation(PropertySources.class);
            PropertySource source = configure.getAnnotation(PropertySource.class);
            properties.putAll(createProperties(sources));
            properties.putAll(createProperties(source));
        }
    }

    @Override
    public final void onStartup(Set<Class<?>> initializer, ServletContext context) {
        try {
            // 获取所有配置信息
            List<WebMvcConfigure> configs = getWebMvcConfigureList(initializer);
            ObjectUtil.require(!configs.isEmpty(), "WebMvcConfigure can not be empty.");

            // 创建Module对象和依赖注入的容器
            ConfigModule configureModule = new ConfigModule(context, configs);
            Injector injector = Guice.createInjector(configureModule);

            // 获取配置信息
            Configure config = injector.getInstance(Configure.class);
            requireNonNull(config, "Configure can not be null.");

            // 调用默认配置信息注册
            configs.stream().map(c -> injector.getInstance(c.getClass()))
                    .forEach(WebMvcConfigure::onStartupRegister);

            // 注册 Listener、Servlet、Filter
            registerListener(injector, config, context);
            registerServlet(injector, config, context);
            registerFilter(injector, config, context);
        } catch (Exception | Error e) {
            LOGGER.error("Initializer Error!", e);
        }
    }

    private List<WebMvcConfigure> getWebMvcConfigureList(Set<Class<?>> initializer) {
        return ofNullable(initializer).stream().flatMap(Collection::stream).filter( //
                WebMvcConfigure.class::isAssignableFrom).map(clazz -> {
            try {
                Constructor<?> constructor = clazz.getConstructor();
                Object instance = constructor.newInstance();
                return (WebMvcConfigure) instance;
            } catch (Exception | Error ex) {
                throw new RuntimeException();
            }
        }).collect(Collectors.toList());
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
            ofNullable(e).ifPresent(register::setMultipartConfig);
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
