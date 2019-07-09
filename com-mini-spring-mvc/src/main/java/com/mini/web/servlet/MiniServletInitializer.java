package com.mini.web.servlet;

import com.google.auto.service.AutoService;
import com.mini.logger.Logger;
import com.mini.util.ObjectUtil;
import com.mini.web.config.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.mini.logger.LoggerFactory.getLogger;

@Component
@AutoService(ServletContainerInitializer.class)
public final class MiniServletInitializer implements ServletContainerInitializer {
    private static final Logger LOGGER = getLogger(MiniServletInitializer.class);
    private static final String FILE_NAME = "mini-application.properties";

    private final ActionInvocationProxyConfigure actionInvocationProxyConfigure = new ActionInvocationProxyConfigure();
    private final ArgumentResolverConfigure argumentResolverConfigure = new ArgumentResolverConfigure();
    private final ModelFactoryConfigure modelFactoryConfigure = new ModelFactoryConfigure();
    private final HttpServletConfigure httpServletConfigure = new HttpServletConfigure();
    private final InterceptorConfigure interceptorConfigure = new InterceptorConfigure();
    private final ListenerConfigure listenerConfigure = new ListenerConfigure();
    private final FilterConfigure filterConfigure = new FilterConfigure();
    private final ViewConfigure viewConfigure = new ViewConfigure();

    @Override
    public final void onStartup(Set<Class<?>> initializer, ServletContext context) {
        try {
            AnnotationConfigApplicationContext application = new AnnotationConfigApplicationContext("com.mini");


            ServletRegistration.Dynamic registration;
            Map<String, ? extends ServletRegistration> registrations = context.getServletRegistrations();
            for (ServletRegistration value : registrations.values()) {
                if (value instanceof ServletRegistration.Dynamic) {
                    registration = (ServletRegistration.Dynamic) value;
                    System.out.println(registration.getClassName());

                    System.out.println(registration);

                }
            }

            System.out.println(registrations);


            //// 验证必须有一个且仅有一个配置类
            //Objects.requireNonNull(initializer, "No WebMvcConfigure class");
            //Class<?>[] classes = initializer.toArray(new Class<?>[0]);
            //ApplicationContext application = new AnnotationConfigApplicationContext(classes);
            //
            //
            //Class<? extends WebMvcConfigureDefault> clazz = getClazz(initializer);
            //// 获取需要扫描的包
            //ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
            //String[] packages = new String[]{this.getClass().getPackageName()};
            //if (componentScan != null && componentScan.value().length > 0) {
            //    packages = componentScan.value();
            //}


            // context.addServlet()

            // applicationContext.getParent()

            //
            //Injector parent = Guice.createInjector(new PropertiesModule() {
            //    protected void configures(Binder binder, ClassLoader loader) throws Exception {
            //        InputStream stream = loader.getResourceAsStream(FILE_NAME);
            //        setPropertyIfAbsent(new MiniProperties().miniLoad(stream));
            //        // 系统对象注入
            //        bind(HttpServlet.class).to(DispatcherHttpServlet.class);
            //        bind(ServletContext.class).toInstance(context);
            //        requireBinding(DispatcherHttpServlet.class);
            //        bind(WebMvcConfigure.class).to(clazz);
            //    }
            //});
            //
            //WebMvcConfigure configure = parent.getInstance(WebMvcConfigure.class);
            //parent.createChildInjector(configure, new MiniModule() {
            //    protected void configures(Binder binder) throws Error {
            //        configure.getHttpServletElements().forEach(ele -> ele.requestInjection(binder));
            //        configure.getFilterElements().forEach(ele -> ele.requestInjection(binder));
            //        configure.getArgumentResolvers().values().forEach(this::requireBinding);
            //        configure.getModelFactory().values().forEach(this::requestInjection);
            //        configure.getInterceptors().forEach(this::requestInjection);
            //        configure.getListeners().forEach(this::requestInjection);
            //        bind(IView.class).toInstance(configure.getView());
            //    }
            //});
            //
            //// 向 ServletContext 注册 Servlet、Filter 和 Listener
            //configure.getHttpServletElements().forEach(e -> e.register(context, configure));
            //configure.getFilterElements().forEach(e -> e.register(context));
            //configure.getListeners().forEach(context::addListener);
        } catch (Exception | Error e) {
            LOGGER.error("Initializer Error!", e);
        }
    }

    /**
     * 获取并验证 WebMvcConfigurer Class
     * @param initializer 初始化Class列表
     * @return WebMvcConfigurer.class
     */
    private Class<? extends WebMvcConfigureDefault> getClazz(Set<Class<?>> initializer) {
        // 验证必须有一个且仅有一个配置类
        Objects.requireNonNull(initializer, "No configuration class");
        ObjectUtil.require(initializer.size() == 1, "Configuration can only have one");

        // 验证当前类是否为 WebMvcConfigurer 类
        Class<?> clazz = initializer.toArray(new Class<?>[0])[0];
        if (!WebMvcConfigureDefault.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("No configuration class");
        }

        // 验证 WebMvcConfigurer 配置必须有 Configuration 注解
        if (clazz.getAnnotation(Configuration.class) == null) {
            throw new RuntimeException("WebMvcConfigurer must be have a @Configuration");
        }

        // 验证 WebMvcConfigurer 配置必须有 ComponentScan 注解
        if (clazz.getAnnotation(ComponentScan.class) == null) {
            throw new RuntimeException("WebMvcConfigurer must be have a @ComponentScan");
        }

        // 读取属性文件并初始化、注入基础实例信息
        return clazz.asSubclass(WebMvcConfigureDefault.class);
    }
}
