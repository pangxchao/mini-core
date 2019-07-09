package com.mini.web.config;

import com.google.inject.Binder;
import com.google.inject.name.Names;
import com.mini.inject.MiniModule;
import com.mini.inject.annotation.Scanning;
import com.mini.util.ArraysUtil;
import com.mini.util.ClassUtil;
import com.mini.util.MappingUri;
import com.mini.util.StringUtil;
import com.mini.util.reflect.MiniParameter;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Before;
import com.mini.web.annotation.Controller;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.config.HttpServletConfigure.HttpServletElement;
import com.mini.web.filter.CharacterEncodingFilter;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.*;
import com.mini.web.model.factory.*;
import com.mini.web.servlet.DispatcherHttpServlet;
import com.mini.web.view.IView;
import com.mini.web.view.ViewFreemarker;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.lang.reflect.Method;
import java.util.*;

import static com.mini.util.ClassUtil.scanner;
import static com.mini.util.StringUtil.*;
import static com.mini.util.TypeUtil.*;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static javax.servlet.DispatcherType.*;

/**
 * Web 配置信息读取
 * @author xchao
 */
public abstract class WebMvcConfigure extends MiniModule {
    private final ActionInvocationProxyConfigure actionInvocationProxyConfigure = new ActionInvocationProxyConfigure();
    private final ArgumentResolverConfigure argumentResolverConfigure = new ArgumentResolverConfigure();
    private final ModelFactoryConfigure modelFactoryConfigure = new ModelFactoryConfigure();
    private final HttpServletConfigure httpServletConfigure = new HttpServletConfigure();
    private final InterceptorConfigure interceptorConfigure = new InterceptorConfigure();
    private final ListenerConfigure listenerConfigure = new ListenerConfigure();
    private final FilterConfigure filterConfigure = new FilterConfigure();
    private final ViewConfigure viewConfigure = new ViewConfigure();

    @Inject
    @Named("mini.http.multipart.file-size-threshold")
    private String fileSizeThreshold;

    @Inject
    @Named("mini.http.multipart.enabled")
    private String multipartEnabled;

    @Inject
    @Named("mini.http.multipart.max-request-size")
    private String maxRequestSize;

    @Inject
    @Named("mini.http.async.enabled")
    private String asyncEnabled;

    @Inject
    @Named("mini.http.multipart.max-file-size")
    private String maxFileSize;

    @Inject
    @Named("mini.http.servlet.urls")
    private String urlPatterns;

    @Inject
    @Named("mini.http.multipart.location")
    private String location;

    /**
     * Gets the value of fileSizeThreshold.
     * @return The value of fileSizeThreshold
     */
    public final int getFileSizeThreshold() {
        return castToIntVal(fileSizeThreshold);
    }

    /**
     * Gets the value of maxRequestSize.
     * @return The value of maxRequestSize
     */
    public final long getMaxRequestSize() {
        if (StringUtil.isBlank(maxRequestSize)) {
            return -1L;
        }
        return castToLongVal(maxRequestSize);
    }

    /**
     * Gets the value of maxFileSize.
     * @return The value of maxFileSize
     */
    public final long getMaxFileSize() {
        if (StringUtil.isBlank(maxFileSize)) {
            return -1L;
        }
        return castToLongVal(maxFileSize);
    }

    /**
     * Gets the value of location.
     * @return The value of location
     */
    public final String getLocation() {
        return location;
    }

    /**
     * Gets the value of multipartEnabled.
     * @return The value of multipartEnabled
     */
    public final boolean isMultipartEnabled() {
        if (StringUtil.isBlank(multipartEnabled)) {
            return true;
        }
        return castToBoolVal(multipartEnabled);
    }

    /**
     * Gets the value of asyncEnabled.
     * @return The value of asyncEnabled
     */
    public final boolean isAsyncEnabled() {
        if (StringUtil.isBlank(asyncEnabled)) {
            return true;
        }
        return castToBoolVal(asyncEnabled);
    }

    /**
     * Gets the value of UrlPatterns.
     * @return The value of UrlPatterns
     */
    public final String[] getUrlPatterns() {
        return split(urlPatterns, "[,]");
    }

    @Override
    protected void configures(Binder binder) throws Exception, Error {
        // 绑定并初始化 actionInvocationProxyConfigure
        requestInjection(actionInvocationProxyConfigure);
        actionInvocationProxyConfigure(actionInvocationProxyConfigure);

        // 绑定并初始化 argumentResolverConfigure
        requestInjection(argumentResolverConfigure);
        argumentResolverConfigure(argumentResolverConfigure);

        // 绑定并初妈化 modelFactoryConfigure
        requestInjection(modelFactoryConfigure);
        modelFactoryConfigure(modelFactoryConfigure);

        // 绑定并初始化 httpServletConfigure
        requestInjection(httpServletConfigure);
        httpServletConfigure(httpServletConfigure);

        // 绑定并初始化 interceptorConfigure
        requestInjection(interceptorConfigure);
        interceptorConfigure(interceptorConfigure);

        // 绑定并初始化 listenerConfigure
        requestInjection(listenerConfigure);
        listenerConfigure(listenerConfigure);

        // 绑定并初始化 filterConfigure
        requestInjection(filterConfigure);
        filterConfigure(filterConfigure);

        // 绑定并初始化 viewConfigure
        requestInjection(viewConfigure);
        viewConfigure(viewConfigure);

        // 自定义初始化
        this.onStartup();

        // 绑定 Invocation Proxy 对象
        actionInvocationProxyConfigure.getInvocationProxy().values().forEach(proxy -> {
            bind(proxy.getClazz());  //
        });

        // 绑定 Resolver Configure 对象
        argumentResolverConfigure.getArgumentResolvers().values().forEach(resolver -> {
            com.google.inject.name.Named named = Names.named(resolver.getName());
            bind(ArgumentResolver.class).annotatedWith(named).to(resolver);
        });

        // 绑定 Model Factory 对象
        modelFactoryConfigure.getModelFactory().forEach((model, factory) -> {
            com.google.inject.name.Named named = Names.named(model.getName());
            bind(ModelFactory.class).annotatedWith(named).to(factory);
        });

        // 绑定 Http Servlet 对象
        httpServletConfigure.getElements().forEach(element -> {
            com.google.inject.name.Named named = Names.named(element.getServletName());
            Class<? extends HttpServlet> servlet = element.getServletClass();
            bind(HttpServlet.class).annotatedWith(named).to(servlet);
        });

        // 绑定 Action Interceptor 对象

        interceptorConfigure.getInterceptors().forEach(interceptor -> {
            com.google.inject.name.Named named = Names.named(interceptor.getName());
            bind(ActionInterceptor.class).annotatedWith(named).to(interceptor);
        });

        // 绑定 Event Listener 对象

        listenerConfigure.getListeners().forEach(listener -> {
            com.google.inject.name.Named named = Names.named(listener.getName());
            bind(EventListener.class).annotatedWith(named).to(listener);
        });

        // 绑定 Filter 对象

        filterConfigure.getElements().forEach(element -> {
            com.google.inject.name.Named named = Names.named(element.getFilterName());
            Class<? extends Filter> filter = element.getFilterClass();
            bind(Filter.class).annotatedWith(named).to(filter);
        });

        // 绑定 IView 对象
        bind(IView.class).to(viewConfigure.getViewClass());
    }

    /** 初始化所有配置信息 */
    protected abstract void onStartup() throws Exception, Error;

    /**
     * ActionInvocationProxy 配置
     * @param configure 配置信息
     */
    @OverridingMethodsMustInvokeSuper
    protected void actionInvocationProxyConfigure(ActionInvocationProxyConfigure configure) {
        Scanning scanner = this.getClass().getAnnotation(Scanning.class);
        String[] packages = new String[]{this.getClass().getPackageName()};
        if (scanner != null && scanner.value().length > 0) {
            packages = scanner.value();
        }

        ArraysUtil.forEach(scanner(packages, Controller.class), clazz -> {
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller == null) return;

            Before cBefore = clazz.getAnnotation(Before.class);
            ArraysUtil.forEach(clazz.getMethods(), method -> {
                Action action = method.getAnnotation(Action.class);
                if (action == null) return;

                // 创建 Action 代理对象
                Before mBefore = method.getAnnotation(Before.class);
                ActionInvocationProxy proxy = new ActionInvocationProxy() {
                    private List<Class<? extends ActionInterceptor>> list;
                    private String path;

                    @Override
                    public Method getMethod() {
                        return method;
                    }

                    @Override
                    public Class<?> getClazz() {
                        return clazz;
                    }

                    @Override
                    public Class<? extends IModel<?>> getModelClass() {
                        return action.value();
                    }

                    @Override
                    public Action.Method[] getSupportMethod() {
                        return action.method();
                    }

                    @Nonnull
                    @Override
                    public List<Class<? extends ActionInterceptor>> getInterceptors() {
                        return Optional.ofNullable(list).orElseGet(() -> {
                            return list = ofNullable(mBefore).map(b -> {
                                return asList(b.value());       //
                            }).orElseGet(() -> ofNullable(cBefore).map(b -> {
                                return asList(b.value());   //
                            }).orElse(new ArrayList<>())); //
                        });
                    }

                    @Nonnull
                    @Override
                    public MiniParameter[] getParameters() {
                        return ClassUtil.getParameter(method);
                    }

                    @Override
                    public String getViewPath() {
                        return Optional.ofNullable(path).orElseGet(() -> {
                            String cPath = def(controller.path(), "");
                            String mPath = def(action.path(), method.getName());
                            path = join("/", cPath, mPath).replaceAll("(/)+", "/");
                            return path = (path.startsWith("/") ? path.substring(1) : path);
                        });
                    }
                };

                // 获取方法上的 urls 列表
                Arrays.stream(action.url().length > 0 ? action.url() : new String[]{method.getName()}).map(url -> {
                    return StringUtil.join("/", "", controller.url(), def(url, method.getName())); //
                }).map(url -> MappingUri.slashRemove(url.replaceAll("(/)+", "/"))).forEach(url -> {
                    configure.addInvocationProxy(url, proxy); //
                });
            });
        });
    }

    /**
     * Action接收的参数配置
     * @param configure 配置信息
     */
    protected void argumentResolverConfigure(ArgumentResolverConfigure configure) {

    }

    /**
     * 渲染器配置
     * @param configure 配置信息
     */
    @OverridingMethodsMustInvokeSuper
    protected void modelFactoryConfigure(ModelFactoryConfigure configure) {
        configure.addModelFactory(ModelJsonList.class, ModelFactoryJsonList.class);
        configure.addModelFactory(ModelJsonMap.class, ModelFactoryJsonMap.class);
        configure.addModelFactory(ModelStream.class, ModelFactoryStream.class);
        configure.addModelFactory(ModelPage.class, ModelFactoryPage.class);
    }

    /**
     * HttpServlet 配置
     * @param configure 配置信息
     * @return 默认 HttpServlet
     */
    @OverridingMethodsMustInvokeSuper
    protected HttpServletElement httpServletConfigure(HttpServletConfigure configure) {
        return configure.addServlet(DispatcherHttpServlet.class)
                .setFileUploadSupported(isMultipartEnabled())
                .setFileSizeThreshold(getFileSizeThreshold())
                .setServletName("DispatcherHttpServlet")
                .setMaxRequestSize(getMaxRequestSize())
                .setAsyncSupported(isAsyncEnabled())
                .setMaxFileSize(getMaxFileSize())
                .addUrlPatterns(getUrlPatterns())
                .setLocation(getLocation());
    }

    /**
     * 拦截器配置
     * @param configure 配置信息
     */
    protected void interceptorConfigure(InterceptorConfigure configure) { }

    /**
     * 监听器配置
     * @param configure 配置信息
     */
    protected void listenerConfigure(ListenerConfigure configure) { }

    /**
     * 过虑器配置
     * @param configure 配置信息
     */
    @OverridingMethodsMustInvokeSuper
    protected void filterConfigure(FilterConfigure configure) {
        configure.addFilter(CharacterEncodingFilter.class).setMatchAfter(true)
                .addDispatcherTypes(REQUEST, FORWARD, INCLUDE, ERROR)
                .setFilterName("CharacterEncodingFilter")
                .addUrlPatterns("/*");
    }

    /**
     * 视图配置
     * @param configure 配置信息
     */
    protected void viewConfigure(ViewConfigure configure) {
        configure.setViewClass(ViewFreemarker.class);
    }
}
