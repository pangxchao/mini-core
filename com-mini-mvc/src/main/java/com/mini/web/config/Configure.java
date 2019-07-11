package com.mini.web.config;

import com.google.inject.Injector;
import com.mini.logger.Logger;
import com.mini.logger.LoggerFactory;
import com.mini.util.Function;
import com.mini.util.MappingUri;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.model.IModel;
import com.mini.web.model.factory.IModelFactory;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.util.StringUtil.split;
import static com.mini.util.TypeUtil.*;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Singleton
public final class Configure {
    private final Map<Class<? extends IModel<?>>, Class<? extends IModelFactory<?>>> factory = new ConcurrentHashMap<>();
    private final Map<Class<?>, Class<? extends ArgumentResolver>> argumentResolvers = new ConcurrentHashMap<>();
    private final Map<Class<? extends HttpServlet>, ServletElement> servlets = new ConcurrentHashMap<>();
    private final Map<Class<? extends Filter>, FilterElement> filters = new ConcurrentHashMap<>();
    private final Map<Class<?>, ArgumentResolver> resolverMap = new ConcurrentHashMap<>();
    private final MappingUri<ActionInvocationProxy> invocationProxy = new MappingUri<>();
    private final Map<Class<?>, IModelFactory<?>> factoryMap = new ConcurrentHashMap<>();
    private final Set<Class<? extends EventListener>> listeners = new HashSet<>();
    private final Logger logger = LoggerFactory.getLogger(Configure.class);
    private Class<? extends IView> viewClass;
    private IView view;

    // 依赖注入容器
    @Inject
    private Injector injector;

    // 编码
    @Inject
    @Named("mini.http.encoding.charset")
    private String encodingCharset;

    // 访问路径
    @Inject
    @Named("mini.http.servlet.urls")
    private String urlPatterns;

    // 异步支持
    @Inject
    @Named("mini.http.async.supported")
    private String asyncSupported;

    // 开启文件上传
    @Inject
    @Named("mini.http.multipart.enabled")
    private String multipartEnabled;

    // 上传文件缓冲区大小
    @Inject
    @Named("mini.http.multipart.file-size-threshold")
    private String fileSizeThreshold;

    // 上传文件总大小限制
    @Inject
    @Named("mini.http.multipart.max-request-size")
    private String maxRequestSize;

    // 上传文件单个文件大小限制
    @Inject
    @Named("mini.http.multipart.max-file-size")
    private String maxFileSize;

    // 上传文件临时路径
    @Inject
    @Named("mini.http.multipart.location")
    private String location;

    // 默认日期时间格式
    @Inject
    @Named("mini.http.datetime-format")
    private String dateTimeFormat;

    // 默认日期格式
    @Inject
    @Named("mini.http.date-format")
    private String dateFormat;

    // 默认时间格式
    @Inject
    @Named("mini.http.time-format")
    private String timeFormat;

    // 默认视图前缀
    @Inject
    @Named("mini.mvc.view.prefix")
    private String ViewPrefix;

    // 默认视图后缀
    @Inject
    @Named("mini.mvc.view.suffix")
    private String viewSuffix;

    /**
     * 获取配置文件编码
     * @return 配置文件编码
     */
    public final String getEncodingCharset() {
        return encodingCharset;
    }

    /**
     * 获取默认 Servlet 访问路径
     * @return Servlet 访问路径
     */
    public final String[] getUrlPatterns() {
        return split(urlPatterns, "[,]");
    }

    /**
     * 获取异步支持
     * @return true-支持异步
     */
    public final boolean isAsyncSupported() {
        return castToBoolVal(asyncSupported);
    }

    /**
     * 获取是否开启上传文件功能
     * @return true-开启
     */
    public final boolean isMultipartEnabled() {
        return castToBoolVal(multipartEnabled);
    }

    /**
     * 获取文件上传缓冲区大小
     * @return 文件上传缓冲区大小
     */
    public final int getFileSizeThreshold() {
        return castToIntVal(fileSizeThreshold);
    }

    /**
     * 同时上传文件的总大小
     * @return 同时上传文件的总大小
     */
    public final long getMaxRequestSize() {
        return castToLongVal(maxRequestSize);
    }

    /**
     * 单个文件大小限制
     * @return 单个文件大小限制
     */
    public final long getMaxFileSize() {
        return castToLongVal(maxFileSize);
    }

    /**
     * 上传文件临时目录
     * @return 上传文件临时目录
     */
    public final String getLocation() {
        return location;
    }

    /**
     * 默认日期时间格式
     * @return 默认日期时间格式
     */
    public final String getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * 默认日期格式
     * @return 默认日期格式
     */
    public final String getDateFormat() {
        return dateFormat;
    }

    /**
     * 默认时间格式
     * @return 默认时间格式
     */
    public final String getTimeFormat() {
        return timeFormat;
    }

    /**
     * 视图路径前缀
     * @return 视图路径前缀
     */
    public final String getViewPrefix() {
        return ViewPrefix;
    }

    /**
     * 视图路径后缀
     * @return 视图路径后缀
     */
    public final String getViewSuffix() {
        return viewSuffix;
    }


    /**
     * 依赖注入容器上下文
     * @param injector 容器上下文
     */
    public final void setInjector(Injector injector) {
        this.injector = injector;
    }

    /**
     * 添加一个Action代理对象
     * @param url             访问Action的URL
     * @param invocationProxy 代理对象实例
     * @return 当前对象
     */
    public final Configure addInvocationProxy(String url, ActionInvocationProxy invocationProxy) {
        this.invocationProxy.putIfAbsent(url, invocationProxy);
        logger.debug("Register Action: " + url);
        return this;
    }

    /**
     * 根据URI获取一个Action代理对象
     * @param uri  访问Action的URI
     * @param func 回调方法
     * @return Action代理对象
     */
    public final ActionInvocationProxy getInvocationProxy(String uri, Function.F2<String, String> func) {
        return invocationProxy.get(uri, func);
    }

    /**
     * 根据URI获取一个Action代理对象
     * @param uri 访问Action的URI
     * @return Action代理对象
     */
    public final ActionInvocationProxy getInvocationProxy(String uri) {
        return invocationProxy.get(uri);
    }


    /**
     * 添加一个监听器
     * @param listener 监听器
     * @return 当前对象
     */
    public final Configure addListener(Class<? extends EventListener> listener) {
        listeners.add(listener);
        return this;
    }

    /**
     * 获取所有监听器
     * @return 监听器
     */
    public final Set<Class<? extends EventListener>> getListeners() {
        return listeners;
    }

    /**
     * 添加一个Servlet
     * @param servlet Servlet
     * @return Servlet对象
     */
    public final ServletElement addServlet(Class<? extends HttpServlet> servlet) {
        ServletElement ele = new ServletElement().setServlet(servlet);
        return defIfNull(servlets.putIfAbsent(servlet, ele), ele);
    }

    /**
     * 获取所有Servlet
     * @return Servlet
     */
    public final Collection<ServletElement> getServlets() {
        return servlets.values();
    }

    /**
     * 添加一个过虑器
     * @param filter 过虑器
     * @return 过虑器对象
     */
    public final FilterElement addFilter(Class<? extends Filter> filter) {
        FilterElement ele = new FilterElement().setFilter(filter);
        return defIfNull(filters.putIfAbsent(filter, ele), ele);
    }

    /**
     * 获取所有过虑器
     * @return 过虑器
     */
    public final Collection<FilterElement> getFilters() {
        return filters.values();
    }

    /**
     * 添加一个参数解析器
     * @param clazz    参数类型
     * @param resolver 解析器类
     * @return 当前对象
     */
    public final Configure addResolver(Class<?> clazz, Class<? extends ArgumentResolver> resolver) {
        argumentResolvers.putIfAbsent(clazz, resolver);
        return this;
    }

    /**
     * 根据参数类型获取解析器实例
     * @param type 参数类型
     * @return 参数解析器实例
     */
    public final ArgumentResolver getResolver(Class<?> type) {
        Objects.requireNonNull(injector, "Injector can not be null");
        return resolverMap.computeIfAbsent(type, k -> ofNullable(argumentResolvers.get(k)) //
                .map(clazz -> injector.getInstance(clazz)).orElse(null));
    }

    /**
     * 根据拦截器实现类获取拦截器实例
     * @return 拦截器实例
     */
    @Nonnull
    public final ActionInterceptor getInterceptor(Class<? extends ActionInterceptor> interceptor) {
        Objects.requireNonNull(injector, "Injector can not be null");
        return requireNonNull(injector.getInstance(interceptor));
    }

    /**
     * 添加一个数据模型工厂
     * @param factory 数据模型工厂
     * @return 当前对象
     */
    public final Configure addModelFactory(Class<? extends IModel<?>> clazz, Class<? extends IModelFactory<?>> factory) {
        this.factory.putIfAbsent(clazz, factory);
        return this;
    }

    /**
     * 根据数据模型实现类获取数据模型工厂实例
     * @param modelClass 数据模型实现类
     * @return 数据模型工厂实例
     */
    public final IModelFactory<?> getFactory(Class<? extends IModel<?>> modelClass) {
        Objects.requireNonNull(injector, "Injector can not be null");
        return factoryMap.computeIfAbsent(modelClass, k -> ofNullable(factory.get(modelClass)) //
                .map(clazz -> injector.getInstance(clazz)).orElse(null));

    }

    /**
     * 设置视图实现类
     * @param viewClass 视图实现类
     * @return 当前对象
     */
    public final Configure setViewClass(Class<? extends IView> viewClass) {
        this.viewClass = viewClass;
        return this;
    }

    /**
     * 获取视图实现类实例
     * @return 视图实现类实例
     */
    public final IView getView() {
        Objects.requireNonNull(injector, "Injector can not be null");
        return Optional.ofNullable(view).orElseGet(() -> {
            view = injector.getInstance(viewClass);
            return view;
        });
    }
}
