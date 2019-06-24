package com.mini.web.servlet;

import com.google.auto.service.AutoService;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mini.dao.annotation.Transaction;
import com.mini.dao.transaction.TransactionInterceptor;
import com.mini.inject.MiniModule;
import com.mini.inject.PropertiesModule;
import com.mini.logger.Logger;
import com.mini.util.MiniProperties;
import com.mini.util.ObjectUtil;
import com.mini.web.annotation.TransactionEnable;
import com.mini.web.config.WebMvcConfigure;
import com.mini.web.view.IView;

import javax.inject.Singleton;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.mini.logger.LoggerFactory.getLogger;
import static com.mini.util.StringUtil.def;

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

            WebMvcConfigure configure = parent.getInstance(WebMvcConfigure.class);
            parent.createChildInjector(configure, new MiniModule() {
                protected void configures(Binder binder) throws Error {
                    if (clazz.getAnnotation(TransactionEnable.class) != null) {
                        bindInterceptor(new TransactionInterceptor());
                    }
                    // 配置信息注入
                    configure.getFilterConfigureElements().forEach(ele -> requestInjection(ele.getFilter()));
                    configure.getArgumentResolvers().values().forEach(this::requestInjection);
                    configure.getModelFactory().values().forEach(this::requestInjection);
                    configure.getInterceptors().forEach(this::requestInjection);
                    configure.getListeners().forEach(this::requestInjection);
                    bind(IView.class).toInstance(configure.getView());
                }

                // 绑定事务拦截器
                private void bindInterceptor(TransactionInterceptor interceptor) throws Error {
                    bindInterceptor(any(), annotatedWith(Transaction.class), interceptor);
                }
            });

            // 向 ServletContext 注册 Servlet、Filter 和 Listener
            configure.getFilterConfigureElements().forEach(ele -> ele.register(context));
            registerServlet(configure, context, parent.getInstance(HttpServlet.class));
            configure.getListeners().forEach(context::addListener);
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
     * 注册Servlet
     * @param context ServletContext
     * @param servlet HttpServlet 实例
     */
    private void registerServlet(WebMvcConfigure configurer, ServletContext context, HttpServlet servlet) {
        String servletName = def(configurer.getServletName(), servlet.getClass().getName());
        ServletRegistration.Dynamic register = context.addServlet(servletName, servlet);
        register.setAsyncSupported(configurer.isAsyncSupported());
        register.addMapping(configurer.getServletUrlPatterns());

        // 文件上传配置
        if (!configurer.isFileUploadSupported()) return;
        if (configurer.getMultipartConfigElement() == null) return;
        register.setMultipartConfig(configurer.getMultipartConfigElement());
    }
}
