package com.mini.web.config;

import com.google.inject.Injector;
import com.mini.util.MappingUri;
import com.mini.util.ObjectUtil;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.argument.ArgumentResolverBean;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.util.StringUtil.split;
import static com.mini.util.TypeUtil.*;
import static java.util.Objects.requireNonNull;

@Singleton
public final class Configure {
    private final Map<Class<?>, Class<? extends ArgumentResolver>> argumentResolvers = new ConcurrentHashMap<>();
    private final Map<Class<? extends HttpServlet>, ServletElement> servlets = new ConcurrentHashMap<>();
    private final Map<Class<? extends Filter>, FilterElement> filters = new ConcurrentHashMap<>();
    private final Map<Class<?>, ArgumentResolver> resolverMap = new ConcurrentHashMap<>();
    private final MappingUri<ActionInvocationProxy> invocationProxy = new MappingUri<>();
    private final Set<Class<? extends EventListener>> listeners = new HashSet<>();
    private Class<? extends IView> viewClass;
    private IView view;

    // 基础配置
    private String encodingCharset = "UTF-8"; // 编码
    private String asyncSupported = "true"; // 是否支持异步返回
    private String urlPatterns = "*.htm"; // 拦截路径

    // 默认文件上传配置（大小限制、缓冲区大小配置，路径配置）
    private String multipartEnabled = "true"; // 开启文件上传
    private String fileSizeThreshold = "4096";  // 上传文件缓冲区大小
    private String maxRequestSize = "-1";  // 上传文件总大小限制
    private String maxFileSize = "-1";  // 上传文件单个文件大小限制
    private String location = "temp"; // 上传文件临时路径

    // 默认日期时间格式配置
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private String dateFormat = "yyyy-MM-dd";
    private String timeFormat = "HH:mm:ss";

    // 默认视图配置（视图路径前缀和后缀）
    private String ViewPrefix = "/WEB-INF/";
    private String viewSuffix = ".ftl";

    // 依赖注入容器
    private Injector injector;

    /**
     * 编码
     * @param encodingCharset 编码
     */
    @Inject
    public void setEncodingCharset(
            @Named("mini.http.encoding.charset")
            @Nullable String encodingCharset) {
        this.encodingCharset = encodingCharset;
    }

    /**
     * 是否支持异步返回
     * @param asyncSupported true-是
     */
    @Inject
    public void setAsyncSupported(
            @Named("mini.http.async.supported")
            @Nullable String asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    /**
     * 默认Servlet拦截路径
     * @param urlPatterns 拦截路径
     */
    @Inject
    public void setUrlPatterns(
            @Named("mini.http.servlet.url-patterns")
            @Nullable String urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    /**
     * 是否开启文件上传功能
     * @param multipartEnabled true-是
     */
    @Inject
    public void setMultipartEnabled(
            @Named("mini.http.multipart.enabled")
            @Nullable String multipartEnabled) {
        this.multipartEnabled = multipartEnabled;
    }

    /**
     * 文件上传缓冲区大小
     * @param fileSizeThreshold 缓冲区大小
     */
    @Inject
    public void setFileSizeThreshold(
            @Named("mini.http.multipart.file-size-threshold")
            @Nullable String fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

    /**
     * 文件上传请求大小限制
     * @param maxRequestSize 请求大小限制
     */
    @Inject
    public void setMaxRequestSize(
            @Named("mini.http.multipart.max-request-size")
            @Nullable String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    /**
     * 文件上传单个文件大小限制
     * @param maxFileSize 单个文件大小限制
     */
    @Inject
    public void setMaxFileSize(
            @Named("mini.http.multipart.max-file-size")
            @Nullable String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    /**
     * 文件上传临时目录
     * @param location 临时目录
     */
    @Inject
    public void setLocation(
            @Named("mini.http.multipart.location")
            @Nullable String location) {
        this.location = location;
    }

    /**
     * 日期时间默认格式化
     * @param dateTimeFormat 格式化
     */
    @Inject
    public void setDateTimeFormat(
            @Named("mini.http.datetime-format")
            @Nullable String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     * 日期默认格式化
     * @param dateFormat 格式化
     */
    @Inject
    public void setDateFormat(
            @Named("mini.http.date-format")
            @Nullable String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 时间默认格式化
     * @param timeFormat 格式化
     */
    @Inject
    public void setTimeFormat(
            @Named("mini.http.time-format")
            @Nullable String timeFormat) {
        this.timeFormat = timeFormat;
    }

    /**
     * 默认视图路径前缀
     * @param viewPrefix 视图路径前缀
     */
    @Inject
    public void setViewPrefix(
            @Named("mini.mvc.view.prefix")
            @Nullable String viewPrefix) {
        ViewPrefix = viewPrefix;
    }

    /**
     * 默认视图路径后缀
     * @param viewSuffix 视图路径后缀
     */
    @Inject
    public void setViewSuffix(
            @Named("mini.mvc.view.suffix")
            @Nullable String viewSuffix) {
        this.viewSuffix = viewSuffix;
    }

    /**
     * 依赖注入容器上下文
     * @param injector 容器上下文
     */
    @Inject
    public void setInjector(Injector injector) {
        this.injector = injector;
    }


    /**
     * 获取配置文件编码
     * @return 配置文件编码
     */
    public final String getEncodingCharset() {
        return encodingCharset;
    }

    /**
     * 获取异步支持
     * @return true-支持异步
     */
    public final boolean isAsyncSupported() {
        return castToBoolVal(asyncSupported);
    }

    /**
     * 获取默认 Servlet 访问路径
     * @return Servlet 访问路径
     */
    public final String[] getUrlPatterns() {
        return split(urlPatterns, "[,]");
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
     * 添加一个Action代理对象
     * @param url   访问Action的URL
     * @param proxy 代理对象实例
     * @return 当前对象
     */
    public final Configure addInvocationProxy(String url, @Nonnull ActionInvocationProxy proxy) {
        ActionInvocationProxy actionProxy = this.invocationProxy.putIfAbsent(url, proxy);
        if (actionProxy == null || ObjectUtil.equals(actionProxy, proxy)) return this;
        throw new RuntimeException(String.format("This action url '%s' already exists[%s, %s]", //
                url, actionProxy.getMethod(), proxy.getMethod()));
    }

    /**
     * 根据URI获取一个Action代理对象
     * @param uri  访问Action的URI
     * @param func 回调方法
     * @return Action代理对象
     */
    public final ActionInvocationProxy getInvocationProxy(String uri, BiConsumer<String, String> func) {
        return invocationProxy.get(uri, func);
    }

    /**
     * 获取所有Action代理对象
     * @return Action代理对象
     */
    public final MappingUri<ActionInvocationProxy> getInvocationProxy() {
        return invocationProxy;
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
     * 删除一个默认的过虑器
     * @param filter 过虑器
     * @return 当前对象
     */
    public final Configure removeFilter(Class<? extends Filter> filter) {
        filters.remove(filter);
        return this;
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
    public final ArgumentResolver getResolver(Class<?> type) throws RuntimeException {
        return Configure.this.resolverMap.computeIfAbsent(type, resolverMapKey -> {
            Class<? extends ArgumentResolver> clazz = argumentResolvers.get(type);
            if (clazz == null) clazz = ArgumentResolverBean.class;
            return injector.getInstance(clazz);
        });
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
