package com.mini.web.config;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.mini.core.inject.annotation.ComponentScan;
import com.mini.core.jdbc.transaction.EnableTransaction;
import com.mini.core.jdbc.transaction.Transactional;
import com.mini.core.jdbc.transaction.TransactionalInterceptor;
import com.mini.core.util.Assert;
import com.mini.core.util.ClassUtil;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Before;
import com.mini.web.annotation.Clear;
import com.mini.web.annotation.Controller;
import com.mini.web.argument.ArgumentResolverContext;
import com.mini.web.argument.ArgumentResolverModel;
import com.mini.web.argument.ArgumentResolverSession;
import com.mini.web.argument.defaults.*;
import com.mini.web.argument.paging.ArgumentResolverPagingDefault;
import com.mini.web.argument.paging.ArgumentResolverPagingRequestParam;
import com.mini.web.argument.request.param.*;
import com.mini.web.argument.request.uri.ArgumentResolverArrayRequestUri;
import com.mini.web.argument.request.uri.ArgumentResolverBasicRequestUri;
import com.mini.web.argument.request.uri.ArgumentResolverMapRequestUri;
import com.mini.web.filter.AccessControlAllowOriginFilter;
import com.mini.web.filter.CacheControlFilter;
import com.mini.web.filter.CharacterEncodingFilter;
import com.mini.web.handler.ExceptionHandlerDefault;
import com.mini.web.handler.ExceptionHandlerValidate;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionProxy;
import com.mini.web.model.IModel;
import com.mini.web.servlet.DispatcherHttpServlet;
import com.mini.web.view.FreemarkerView;
import com.mini.web.view.IView;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.mini.core.util.ClassUtil.scanner;
import static com.mini.core.util.FileUtil.getFileExt;
import static java.util.Objects.requireNonNull;
import static javax.servlet.DispatcherType.*;

/**
 * Web 配置信息读取
 * @author xchao
 */
public abstract class WebMvcConfigure implements Module {
    private static final String SEP = "/";
    @Inject
    private ServletContext servletContext;
    @Inject
    private Configure configure;
    @Inject
    private Injector injector;

    @Override
    public synchronized final void configure(Binder binder) {
        WebMvcConfigure.this.onStartupBinding(binder);
        // 是否开启了数据库的注解配置
        if (getAnnotation(EnableTransaction.class) == null) {
            return;
        }
        // 创建拦截器对象
        MethodInterceptor interceptor = new TransactionalInterceptor();
        // 注入对象和拦截器
        binder.bind(MethodInterceptor.class).toInstance(interceptor);
        binder.bindInterceptor(any(), annotatedWith(Transactional.class), interceptor);
    }

    /**
     * 该方法只难做依赖绑定相关操作
     * @param binder 绑定器
     */
    protected synchronized void onStartupBinding(Binder binder) {
    }

    public synchronized final void onStartupRegister() {
        Objects.requireNonNull(servletContext);
        Objects.requireNonNull(configure);
        // 注册默认视图实现类
        registerView(configure);
        // 注册默认Servlet
        registerServlet(configure);
        // 注册默认过虑器
        registerFilter(configure);
        // 注册默认参数解析器
        registerArgumentResolver(configure);
        // 注册默认的异常处理哭喊
        registerExceptionHandler(configure);
        // 注册默认的 ActionInvocationProxy
        registerActionInvocationProxy(configure);
        // 调用自定义初始化方法
        onStartupRegister(servletContext, configure);
    }

    /**
     * 该方法在自动注入之后调用，使用时需要注意顺序
     * @param servletContext ServletContext 对象
     * @param configure      配置信息
     */
    protected void onStartupRegister(ServletContext servletContext, Configure configure) {
    }

    // 获取当前类指定注解信息
    public final <T extends Annotation> T getAnnotation(Class<T> clazz) {
        return this.getClass().getAnnotation(clazz);
    }

    // 注册默认的 ActionInvocationProxy
    private void registerActionInvocationProxy(Configure configure) {
        // 获取需要扫描的所有包
        ComponentScan scan = getClass().getAnnotation(ComponentScan.class);
        List<String> packageNames = new ArrayList<>(Arrays.asList(scan.value()));
        packageNames.add(ClassUtils.getPackageName(this.getClass()));
        // 扫描所有 Controlle 类对象，并处理该对象
        scanner(packageNames.toArray(new String[0]), Controller.class).forEach(clazz -> {
            // 获取类上的注解信息
            Controller controller = clazz.getAnnotation(Controller.class);
            requireNonNull(controller, "@Controller can not be null");

            // 获取类上的拦截器信息
            Clear controllerClear = clazz.getAnnotation(Clear.class);
            Before controllerBefore = clazz.getAnnotation(Before.class);

            // 查找当前类下的所有公开方法并处理
            Arrays.stream(clazz.getMethods()).forEach(method -> {

                // 获取方法上的Action注解信息
                Action action = method.getAnnotation(Action.class);
                if (action == null) return;

                // 获取方法上的拦截器信息
                Clear methodClar = method.getAnnotation(Clear.class);
                Before methodBefore = method.getAnnotation(Before.class);

                // 视图文件路径
                String path = getViewPath(clazz, controller, method, action);

                // 获取方法参数信息
                MiniParameter[] parameters = ClassUtil.getParameterByAsm(method);

                // 获取 请求 Action 的路径 并 注册Action
                getRequestUriList(clazz, controller, method, action).stream() //
                        .distinct().forEach(requestUri -> {  //
                    configure.addActionProxy(requestUri, new ActionProxy() {
                        private List<ActionInterceptor> interceptors;
                        private ParameterHandler[] handlers;

                        @Nonnull
                        @Override
                        public Class<?> getClazz() {
                            return clazz;
                        }

                        @Nonnull
                        @Override
                        public Method getMethod() {
                            return method;
                        }

                        @Nonnull
                        @Override
                        public IModel<?> getModel(IView view) {
                            return action.value().getModel(view, getViewPath());
                        }

                        @Nonnull
                        @Override
                        public Action.Method[] getSupportMethod() {
                            return action.method();
                        }

                        @Nonnull
                        @Override
                        public List<ActionInterceptor> getInterceptors() {
                            return Optional.ofNullable(interceptors).orElseGet(() -> {
                                synchronized (this) {
                                    // 创建拦截器列表实例
                                    interceptors = new ArrayList<>();
                                    // 将方法上的拦截器添加到实例列表中
                                    if (methodBefore != null && methodBefore.value().length > 0) {
                                        interceptors.addAll(Stream.of(methodBefore.value())
                                                .map(c -> injector.getInstance(c))
                                                .collect(Collectors.toList()));
                                    }
                                    // 方法上有清除注解时直接返回
                                    if (methodClar != null) {
                                        return interceptors;
                                    }
                                    // 将类上的注解添加到拦截器实例列表之前
                                    if (controllerBefore != null && controllerBefore.value().length > 0) {
                                        interceptors.addAll(0, Stream.of(controllerBefore.value())
                                                .map(c -> injector.getInstance(c))
                                                .collect(Collectors.toList()));
                                    }
                                    if (controllerClear != null) {
                                        return interceptors;
                                    }
                                    // 添加全局拦截器到拦截器实例列表
                                    interceptors.addAll(0, configure.getGlobalInterceptors());
                                    return interceptors;
                                }
                            });
                        }

                        @Nonnull
                        @Override
                        public MiniParameter[] getParameters() {
                            return parameters;
                        }

                        @Nonnull
                        @Override
                        public ParameterHandler[] getParameterHandlers() {
                            return Optional.ofNullable(handlers).orElseGet(() -> {
                                synchronized (this) {
                                    handlers = Stream.of(getParameters()).map(param ->
                                            configure.getArgumentResolvers().stream()
                                                    .filter(r -> r.supportParameter(param))
                                                    .findAny()
                                                    .map(r -> new ParameterHandler(r, param))
                                                    .orElse(null)) //
                                            .toArray(ParameterHandler[]::new);
                                    return handlers;
                                }
                            });
                        }

                        @Override
                        public String getViewPath() {
                            return path;
                        }

                        @Override
                        public String getRequestUri() {
                            return requestUri;
                        }
                    });
                });
            });
        });
    }

    @Nonnull
    private String getViewPath(Class<?> clazz, Controller controller, Method method, Action action) {
        // 处理文件路径
        String typePath = controller.path();
        if (StringUtils.isBlank(typePath)) {
            typePath = clazz.getSimpleName();
        }
        // 去掉类上的路径两边的空格和 “/”
        typePath = StringUtils.strip(typePath);
        typePath = StringUtils.strip(typePath, SEP);
        // typePath 不能为空
        Assert.notBlank(typePath);

        String methodPath = action.path();
        if (StringUtils.isBlank(methodPath)) {
            methodPath = method.getName();
        }

        // 去掉方法上的路径和两边的空格
        methodPath = StringUtils.strip(methodPath);
        methodPath = StringUtils.strip(methodPath, SEP);

        // typePath 不能为空
        Assert.notBlank(methodPath);

        // 获取完整的视图路径
        return typePath + SEP + methodPath;
    }

    @Nonnull
    private List<String> getRequestUriList(Class<?> clazz, Controller controller, Method method, Action action) {
        String typeUrl = controller.url();
        if (StringUtils.isBlank(typeUrl)) {
            typeUrl = clazz.getSimpleName();
        }
        // 去掉类URL上的空格 “/”
        typeUrl = StringUtils.strip(typeUrl);
        typeUrl = StringUtils.strip(typeUrl, SEP);

        // 所有URL
        List<String> urlList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(action.url())) {
            for (String methodUrl : action.url()) {
                methodUrl = StringUtils.strip(methodUrl);
                methodUrl = StringUtils.strip(methodUrl, SEP);

                // 组装成完整的URL
                String url = typeUrl + SEP + methodUrl;
                url = StringUtils.strip(url, SEP);
                // 如果URL上没有配置后缀，添加默认后缀
                if (StringUtils.isBlank(getFileExt(url))) {
                    url = url + action.suffix();
                }
                // 添加路径到列表
                urlList.add(url);
            }
        } else if (!method.getName().isBlank()) {
            // 处理方法路径
            String methodUrl = method.getName();
            methodUrl = methodUrl + action.suffix();
            urlList.add(typeUrl + SEP + methodUrl);
        }
        return urlList;
    }

    // 注册默认Servlet
    private void registerServlet(Configure configure) {
        // 默认Action处理Servlet
        ServletElement element = configure.addServlet(DispatcherHttpServlet.class);
        element.setFileSizeThreshold(configure.getFileSizeThreshold());
        element.setMultipartEnabled(configure.isMultipartEnabled());
        element.setAsyncSupported(configure.isAsyncSupported());
        element.setMaxRequestSize(configure.getMaxRequestSize());
        element.addUrlPatterns(configure.getUrlPatterns());
        element.setMaxFileSize(configure.getMaxFileSize());
        element.setLocation(configure.getLocation());
    }

    // 配置默认过虑器
    private void registerFilter(Configure configure) {
        // 编码统一管理过虑器
        FilterElement element = configure.addFilter(CharacterEncodingFilter.class);
        element.addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ASYNC, ERROR);
        element.addUrlPatterns("/*").setMatchAfter(true);

        // 跨域请求过虑器
        element = configure.addFilter(AccessControlAllowOriginFilter.class);
        element.addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ASYNC, ERROR);
        element.addUrlPatterns("/*").setMatchAfter(true);

        // 静态资源缓存过虑器
        element = configure.addFilter(CacheControlFilter.class);
        element.addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ASYNC);
        element.addUrlPatterns("/*").setMatchAfter(true);
    }

    // 配置默认参数解析器
    private void registerArgumentResolver(Configure configure) {
        // Servlet容器、数据模型渲染、登录Session相关参数
        configure.addArgumentResolvers(ArgumentResolverContext.class);
        configure.addArgumentResolvers(ArgumentResolverModel.class);
        configure.addArgumentResolvers(ArgumentResolverSession.class);

        // 默认方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
        configure.addArgumentResolvers(ArgumentResolverBasicDefault.class);
        configure.addArgumentResolvers(ArgumentResolverArrayDefault.class);
        configure.addArgumentResolvers(ArgumentResolverMapDefault.class);
        configure.addArgumentResolvers(ArgumentResolverPartDefault.class);
        configure.addArgumentResolvers(ArgumentResolverPartDefaultArray.class);

        // Request Param 方式： 支持一般基础数据、一般基础数组、文件、Map 类型的参数
        configure.addArgumentResolvers(ArgumentResolverBasicRequestParam.class);
        configure.addArgumentResolvers(ArgumentResolverArrayRequestParam.class);
        configure.addArgumentResolvers(ArgumentResolverPartRequestParam.class);
        configure.addArgumentResolvers(ArgumentResolverPartArrayRequestParam.class);
        configure.addArgumentResolvers(ArgumentResolverMapRequestParam.class);
        configure.addArgumentResolvers(ArgumentResolverMapRequestParamArray.class);

        // Request Uri 方式： 支持一般基础数据、一般基础数组、Map 类型的参数
        configure.addArgumentResolvers(ArgumentResolverBasicRequestUri.class);
        configure.addArgumentResolvers(ArgumentResolverArrayRequestUri.class);
        configure.addArgumentResolvers(ArgumentResolverMapRequestUri.class);

        // 分页器参数解析
        configure.addArgumentResolvers(ArgumentResolverPagingRequestParam.class);
        configure.addArgumentResolvers(ArgumentResolverPagingDefault.class);
    }

    // 注册异常处理器
    private void registerExceptionHandler(Configure configure) {
        // 验证异常处理器
        configure.registerExceptionHandler(ExceptionHandlerValidate.class);
        // 其它普通异常处理器
        configure.registerExceptionHandler(ExceptionHandlerDefault.class);
    }

    // 配置默认视图实现类
    private void registerView(Configure configure) {
        configure.setViewClass(FreemarkerView.class);
    }
}
