package com.mini.core.mvc.support;

import com.mini.core.mvc.annotation.Action;
import com.mini.core.mvc.annotation.Before;
import com.mini.core.mvc.annotation.Clear;
import com.mini.core.mvc.annotation.Controller;
import com.mini.core.mvc.argument.ArgumentResolverContext;
import com.mini.core.mvc.argument.ArgumentResolverModel;
import com.mini.core.mvc.argument.ArgumentResolverSession;
import com.mini.core.mvc.argument.defaults.*;
import com.mini.core.mvc.argument.header.*;
import com.mini.core.mvc.argument.param.*;
import com.mini.core.mvc.argument.uri.*;
import com.mini.core.mvc.filter.AccessControlAllowOriginFilter;
import com.mini.core.mvc.filter.CharacterEncodingFilter;
import com.mini.core.mvc.handler.ExceptionHandlerDefault;
import com.mini.core.mvc.handler.ExceptionHandlerValidate;
import com.mini.core.mvc.interceptor.ActionInterceptor;
import com.mini.core.mvc.model.IModel;
import com.mini.core.mvc.servlet.DispatcherHttpServlet;
import com.mini.core.mvc.support.config.Configures;
import com.mini.core.mvc.view.JspPageViewResolver;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

@Component
@HandlesTypes(WebApplicationInitializer.class)
public final class MiniApplicationInitializer implements ServletContainerInitializer {
    private static Configures configures;

    @Bean
    @Qualifier("miniApplicationInitializer")
    public MiniApplicationInitializer miniApplicationInitializer() {
        return this;
    }

    @Bean
    @Qualifier("configures")
    public Configures configures(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        return new Configures(annotationConfigApplicationContext);
    }

    @Override
    public final void onStartup(Set<Class<?>> initializer, ServletContext context) {
        var application = new AnnotationConfigApplicationContext(getClass());
        application.register(initializer.toArray(new Class[0]));
        configures = application.getBean(Configures.class);

        // 验证异常处理器/其它普通异常处理器
        configures.addExceptionHandler(ExceptionHandlerValidate.class);
        configures.addExceptionHandler(ExceptionHandlerDefault.class);
        // Servlet容器、数据模型渲染、登录Session相关参数
        configures.addArgumentResolver(ArgumentResolverContext.class);
        configures.addArgumentResolver(ArgumentResolverModel.class);
        configures.addArgumentResolver(ArgumentResolverSession.class);
        // 默认方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
        configures.addArgumentResolver(ArgumentResolverBasicDefault.class);
        configures.addArgumentResolver(ArgumentResolverArrayDefault.class);
        configures.addArgumentResolver(ArgumentResolverBeanDefault.class);
        configures.addArgumentResolver(ArgumentResolverMapDefault.class);
        configures.addArgumentResolver(ArgumentResolverPartDefault.class);
        configures.addArgumentResolver(ArgumentResolverPartDefaultArray.class);
        // Request Param 方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
        configures.addArgumentResolver(ArgumentResolverBasicRequestParam.class);
        configures.addArgumentResolver(ArgumentResolverArrayRequestParam.class);
        configures.addArgumentResolver(ArgumentResolverBeanRequestParam.class);
        configures.addArgumentResolver(ArgumentResolverPartRequestParam.class);
        configures.addArgumentResolver(ArgumentResolverPartArrayRequestParam.class);
        configures.addArgumentResolver(ArgumentResolverMapRequestParam.class);
        configures.addArgumentResolver(ArgumentResolverMapRequestParamArray.class);
        // Request Uri 方式： 支持一般基础数据、一般基础数组、Map 类型的参数
        configures.addArgumentResolver(ArgumentResolverBasicRequestUri.class);
        configures.addArgumentResolver(ArgumentResolverArrayRequestUri.class);
        configures.addArgumentResolver(ArgumentResolverBeanRequestUri.class);
        configures.addArgumentResolver(ArgumentResolverMapRequestUri.class);
        configures.addArgumentResolver(ArgumentResolverMapRequestUriArray.class);
        // Request Header 方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
        configures.addArgumentResolver(ArgumentResolverBasicRequestHeader.class);
        configures.addArgumentResolver(ArgumentResolverArrayRequestHeader.class);
        configures.addArgumentResolver(ArgumentResolverBeanRequestHeader.class);
        configures.addArgumentResolver(ArgumentResolverMapRequestHeader.class);
        configures.addArgumentResolver(ArgumentResolverMapRequestHeaderArray.class);
        // 配置默认视图实现类
        configures.setPageViewResolver(JspPageViewResolver.class);
        // 编码统一管理过虑器
        configures.addFilter(CharacterEncodingFilter.class, registration -> {
            registration.setName("CharacterEncodingFilter");
            registration.addUrlPatterns("/*");
        });
        // 跨域请求过虑器
        configures.addFilter(AccessControlAllowOriginFilter.class, registration -> {
            registration.setName("AccessControlAllowOriginFilter");
            registration.addUrlPatterns("/*");
        });
        // 初始化项目自定义配置信息
        initializer.stream().map(application::getBean).forEach(it -> { //
            ((WebApplicationInitializer) it).onStartup(context, configures);
        });
        // 初始化项目自定义配置信息
        initializer.stream().map(application::getBean).forEach(it -> {  //
            registerActionProxy(application, (WebApplicationInitializer) it);
        });
        // 注册默认 HttpServlet
        configures.addServlet(DispatcherHttpServlet.class, registration -> {
            registration.addUrlPatterns(configures.getDefaultMapping());
            registration.setName("DispatcherHttpServlet");
        });
        // 绑定 Servlet、Filter、listener
        configures.getServlets().forEach(servlet -> servlet.register(context));
        configures.getFilters().forEach(filter -> filter.register(context));
        configures.getListeners().forEach(context::addListener);
    }

    // 注册默认的 ActionInvocationProxy
    private void registerActionProxy(AnnotationConfigApplicationContext application, WebApplicationInitializer config) {
        application.getBeansWithAnnotation(Controller.class).values().forEach(value -> {
            Controller controller = value.getClass().getAnnotation(Controller.class);
            requireNonNull(controller, "@Controller can not be null");

            // 获取类上的拦截器信息
            Clear controllerClear = value.getClass().getAnnotation(Clear.class);
            Before controllerBefore = value.getClass().getAnnotation(Before.class);

            // 查找当前类下的所有公开方法并处理
            stream(value.getClass().getMethods()).forEach(method -> {
                // 获取方法上的Action注解信息
                Action action = method.getAnnotation(Action.class);
                if (action == null) return;
                // 获取方法上的拦截器信息
                Clear methodClear = method.getAnnotation(Clear.class);
                Before methodBefore = method.getAnnotation(Before.class);
                // 视图文件路径
                String path = getViewPath(value.getClass(), controller, method, action);
                // 获取方法参数信息
                MethodParameter[] parameters = new MethodParameter[method.getParameterCount()];
                for (int i = 0, length = method.getParameterCount(); i < length; i++) {
                    final MethodParameter parameter = new MethodParameter(method, i);
                    var nameDiscoverer = new DefaultParameterNameDiscoverer();
                    parameter.initParameterNameDiscovery(nameDiscoverer);
                    parameters[i] = parameter;
                }
                // 获取 请求 Action 的路径 并 注册Action
                getRequestUriList(value.getClass(), controller, method, action).stream().distinct().forEach(requestUri -> {
                    // 根据扫描出来的 Action 对象创建 ActionProxy 并添加到配置信息中
                    configures.addActionProxy(requestUri, new ActionSupportProxy() {
                        private CopyOnWriteArrayList<ActionInterceptor> interceptors;
                        private ParameterHandler[] handlers;

                        @NotNull
                        @Override
                        public final Class<?> getClazz() {
                            return value.getClass();
                        }

                        @NotNull
                        @Override
                        public final Method getMethod() {
                            return method;
                        }

                        @NotNull
                        @Override
                        public final IModel<?> getModel() {
                            return application.getBean(action.value());
                        }

                        @NotNull
                        @Override
                        public final Action.Method[] getSupportMethod() {
                            return action.method();
                        }

                        @NotNull
                        @Override
                        public final CopyOnWriteArrayList<ActionInterceptor> getInterceptors() {
                            return ofNullable(interceptors).orElseGet(() -> {
                                synchronized (this) {
                                    // 创建拦截器列表实例
                                    interceptors = new CopyOnWriteArrayList<>();
                                    // 将方法上的拦截器添加到实例列表中
                                    if (methodBefore != null && methodBefore.value().length > 0) {
                                        interceptors.addAll(of(methodBefore.value())
                                                .map(application::getBean)
                                                .collect(Collectors.toList()));
                                    }
                                    // 方法上有清除注解时直接返回
                                    if (methodClear != null) {
                                        return interceptors;
                                    }
                                    // 将类上的注解添加到拦截器实例列表之前
                                    if (controllerBefore != null && controllerBefore.value().length > 0) {
                                        interceptors.addAll(0, of(controllerBefore.value())
                                                .map(application::getBean)
                                                .collect(Collectors.toList()));
                                    }
                                    if (controllerClear != null) {
                                        return interceptors;
                                    }
                                    // 添加全局拦截器到拦截器实例列表
                                    interceptors.addAll(0, configures.getInterceptorList());
                                    return interceptors;
                                }
                            });
                        }

                        @NotNull
                        @Override
                        public final MethodParameter[] getParameters() {
                            return parameters;
                        }

                        @NotNull
                        @Override
                        public final ParameterHandler[] getParameterHandlers() {
                            return ofNullable(handlers).orElseGet(this::getHandlers);
                        }

                        private synchronized ParameterHandler[] getHandlers() {
                            handlers = of(getParameters()).map(param -> configures.getArgumentResolverSet()
                                    .stream().filter(r -> r.supportParameter(param)).findAny()
                                    .map(r -> new ParameterHandler(r, param))
                                    .orElseThrow(() -> new NullPointerException("Unsupported parameter:" + param)))
                                    .toArray(ParameterHandler[]::new);
                            return handlers;
                        }

                        @Override
                        public final String getViewPath() {
                            return path;
                        }

                        @Override
                        public final String getRequestUri() {
                            return requestUri;
                        }
                    });
                });
            });
        });
    }

    @NotNull
    private String getViewPath(Class<?> clazz, Controller controller, Method method, Action action) {
        String typePath = getPathOrUrl(controller.path(), clazz.getSimpleName());
        String methodPath = getPathOrUrl(action.path(), method.getName());
        return (typePath + "/" + methodPath).replaceAll("(/)+", "/");
    }

    @NotNull
    private List<String> getRequestUriList(Class<?> clazz, Controller controller, Method method, Action action) {
        final String typeUrl = getPathOrUrl(controller.url(), clazz.getSimpleName());
        return concat(stream(action.url().clone()), of(method.getName())).distinct()
                .map(it -> getPathOrUrl(it, "")).filter(path -> !path.isBlank())
                .map(it -> (typeUrl + "/" + it).replaceAll("(/)+", "/"))
                .collect(Collectors.toList());
    }

    @NotNull
    private static String getPathOrUrl(String value, String defaultValue) {
        if ((value = value.strip()).isEmpty()) {
            value = defaultValue;
        }
        // 去前面的空格和 "/"
        while ((value = value.strip()).startsWith("/")) {
            value = value.substring(1);
        }
        // 去后面的空格和 "/"
        while ((value = value.strip()).endsWith("/")) {
            final int maxLength = value.length() - 1;
            value = value.substring(0, maxLength);
        }
        return value;
    }
}
