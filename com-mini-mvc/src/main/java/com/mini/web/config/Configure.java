package com.mini.web.config;

import com.google.inject.Injector;
import com.mini.logger.Logger;
import com.mini.logger.LoggerFactory;
import com.mini.util.MappingUri;
import com.mini.web.annotation.Action;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.interceptor.ActionInterceptor;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.view.FreemarkerView;
import com.mini.web.view.IView;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.util.StringUtil.split;
import static com.mini.util.TypeUtil.*;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

@Singleton
public final class Configure {
    private final Map<Class<?>, Class<? extends ArgumentResolver>> argumentResolvers = new ConcurrentHashMap<>();
    private final Map<Class<? extends HttpServlet>, ServletElement> servlets = new ConcurrentHashMap<>();
    private final Map<Class<? extends Filter>, FilterElement> filters = new ConcurrentHashMap<>();
    private final Map<Action.Method, WebMappingUri> invocation = new ConcurrentHashMap<>();
    private final Map<Class<?>, ArgumentResolver> resolverMap = new ConcurrentHashMap<>();
    private final HashSet<Class<? extends EventListener>> listeners = new HashSet<>();
    private static final Logger logger = LoggerFactory.getLogger(Configure.class);
    private Class<? extends IView> viewClass = FreemarkerView.class;
    private IView view;

    // 基础配置
    private String encodingCharset = "UTF-8"; // 编码
    private String asyncSupported = "true"; // 是否支持异步返回
    private String suffixPattern = "true"; // 是否开启后缀匹配模式
    private String trailingSlash = "true"; // 是否自动后缀路径模式匹配
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
     * 是否开启后缀匹配模式
     * @param suffixPattern "true"-是
     */
    @Inject
    public void setUseSuffixPatternMatch(
            @Named("mini.http.servlet.suffix-pattern")
            @Nullable String suffixPattern) {
        this.suffixPattern = suffixPattern;
    }

    /**
     * 是否自动后缀路径模式匹配
     * @param trailingSlash "true"-是
     */
    @Inject
    public void setUseTrailingSlashMatch(
            @Named("mini.http.servlet.trailing-slash")
            @Nullable String trailingSlash) {
        this.trailingSlash = trailingSlash;
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
     * @param multipartEnabled "true"-是
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

    public synchronized final String getEncodingCharset() {
        return encodingCharset;
    }

    /**
     * 获取异步支持
     * @return true-支持异步
     */
    public synchronized final boolean isAsyncSupported() {
        return castToBoolVal(asyncSupported);
    }

    /**
     * 是否开启后缀匹配模式
     * @return true-是
     */
    public synchronized final boolean isSuffixPattern() {
        return castToBoolVal(suffixPattern);
    }

    /**
     * 是否自动后缀路径模式匹配
     * @return true-是
     */
    public synchronized final boolean isTrailingSlash() {
        return castToBoolVal(trailingSlash);
    }

    /**
     * 获取默认 Servlet 访问路径
     * @return Servlet 访问路径
     */
    public synchronized final String[] getUrlPatterns() {
        return split(urlPatterns, "[,]");
    }

    /**
     * 获取是否开启上传文件功能
     * @return true-开启
     */
    public synchronized final boolean isMultipartEnabled() {
        return castToBoolVal(multipartEnabled);
    }

    /**
     * 获取文件上传缓冲区大小
     * @return 文件上传缓冲区大小
     */
    public synchronized final int getFileSizeThreshold() {
        return castToIntVal(fileSizeThreshold);
    }

    /**
     * 同时上传文件的总大小
     * @return 同时上传文件的总大小
     */
    public synchronized final long getMaxRequestSize() {
        return castToLongVal(maxRequestSize);
    }

    /**
     * 单个文件大小限制
     * @return 单个文件大小限制
     */
    public synchronized final long getMaxFileSize() {
        return castToLongVal(maxFileSize);
    }

    /**
     * 上传文件临时目录
     * @return 上传文件临时目录
     */
    public synchronized final String getLocation() {
        return location;
    }

    /**
     * 默认日期时间格式
     * @return 默认日期时间格式
     */
    public synchronized final String getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * 默认日期格式
     * @return 默认日期格式
     */
    public synchronized final String getDateFormat() {
        return dateFormat;
    }

    /**
     * 默认时间格式
     * @return 默认时间格式
     */

    public synchronized final String getTimeFormat() {
        return timeFormat;
    }

    /**
     * 视图路径前缀
     * @return 视图路径前缀
     */

    public synchronized final String getViewPrefix() {
        return ViewPrefix;
    }

    /**
     * 视图路径后缀
     * @return 视图路径后缀
     */

    public synchronized final String getViewSuffix() {
        return viewSuffix;
    }

    /**
     * 添加一个Action代理对象
     * @param url   访问Action的URL
     * @param proxy 代理对象实例
     * @return 当前对象
     */
    public synchronized final Configure addInvocationProxy(String url, @Nonnull ActionInvocationProxy proxy) {
        logger.debug(format("Register Action[%s %s]", Arrays.toString(proxy.getSupportMethod()), url));
        Arrays.stream(proxy.getSupportMethod()).forEach(method -> { //
            invocation.computeIfAbsent(method, key -> { //
                return new WebMappingUri();
            }).put(url, proxy);
        });
        return this;
    }

    /**
     * 根据URI获取一个Action代理对象
     * @param uri              访问Action的URI
     * @param method           请求的方法
     * @param useSuffixPattern 是否开启后缀匹配模式
     * @param useTrailingSlash 是否自动后缀路径模式匹配
     * @param func             回调方法
     * @return Action代理对象
     */
    public synchronized final ActionInvocationProxy getInvocationProxy(String uri, Action.Method method, //
            boolean useSuffixPattern, boolean useTrailingSlash, BiConsumer<String, String> func) { //
        return requireNonNull(invocation.putIfAbsent(method, new WebMappingUri())) //
                .get(uri, useSuffixPattern, useTrailingSlash, func);
    }

    /**
     * 获取所有的Action代理对象
     * @return 所有的Action代理对象
     */

    public synchronized final Set<ActionInvocationProxy> getInvocationProxyAll() {
        return invocation.values().stream().flatMap(v -> {
            return v.values().stream(); //
        }).collect(Collectors.toSet());
    }


    /**
     * 添加一个监听器
     * @param listener 监听器
     * @return 当前对象
     */

    public synchronized final Configure addListener(Class<? extends EventListener> listener) {
        listeners.add(listener);
        return this;
    }

    /**
     * 获取所有监听器
     * @return 监听器
     */
    public synchronized final Set<Class<? extends EventListener>> getListeners() {
        return listeners;
    }

    /**
     * 添加一个Servlet
     * @param servlet Servlet
     * @return Servlet对象
     */
    public synchronized final ServletElement addServlet(Class<? extends HttpServlet> servlet) {
        ServletElement ele = new ServletElement().setServlet(servlet);
        return defIfNull(servlets.putIfAbsent(servlet, ele), ele);
    }

    /**
     * 获取所有Servlet
     * @return Servlet
     */
    public synchronized final Collection<ServletElement> getServlets() {
        return servlets.values();
    }

    /**
     * 添加一个过虑器
     * @param filter 过虑器
     * @return 过虑器对象
     */
    public synchronized final FilterElement addFilter(Class<? extends Filter> filter) {
        FilterElement ele = new FilterElement().setFilter(filter);
        return defIfNull(filters.putIfAbsent(filter, ele), ele);
    }

    /**
     * 删除一个默认的过虑器
     * @param filter 过虑器
     * @return 当前对象
     */

    public synchronized final Configure removeFilter(Class<? extends Filter> filter) {
        filters.remove(filter);
        return this;
    }

    /**
     * 获取所有过虑器
     * @return 过虑器
     */
    public synchronized final Collection<FilterElement> getFilters() {
        return filters.values();
    }

    /**
     * 添加一个参数解析器
     * @param clazz    参数类型
     * @param resolver 解析器类
     * @return 当前对象
     */

    public synchronized final Configure addResolver(Class<?> clazz, Class<? extends ArgumentResolver> resolver) {
        argumentResolvers.putIfAbsent(clazz, resolver);
        return this;
    }

    /**
     * 根据参数类型获取解析器实例
     * @param type 参数类型
     * @return 参数解析器实例
     */
    @Nonnull
    public synchronized final ArgumentResolver getResolver(Class<?> type) throws UnregisteredException {
        return Configure.this.resolverMap.computeIfAbsent(type, resolverMapKey -> {
            Class<? extends ArgumentResolver> clazz = argumentResolvers.get(type);
            return ofNullable(clazz).map(c -> injector.getInstance(c)).orElseThrow(() -> { //
                return new UnregisteredException("Unregistered parameter types: " + type);
            });
        });
    }

    /**
     * 根据拦截器实现类获取拦截器实例
     * @return 拦截器实例
     */

    @Nonnull
    public synchronized final ActionInterceptor getInterceptor(Class<? extends ActionInterceptor> interceptor) {
        requireNonNull(injector, "Injector can not be null");
        return requireNonNull(injector.getInstance(interceptor));
    }

    /**
     * 设置视图实现类
     * @param viewClass 视图实现类
     * @return 当前对象
     */
    public synchronized final Configure setViewClass(Class<? extends IView> viewClass) {
        this.viewClass = viewClass;
        return this;
    }

    /**
     * 获取视图实现类实例
     * @return 视图实现类实例
     */
    public synchronized final IView getView() {
        requireNonNull(injector, "Injector can not be null");
        return ofNullable(view).orElseGet(() -> {
            view = injector.getInstance(viewClass);
            return view;
        });
    }

    public static class UnregisteredException extends RuntimeException {
        private static final long serialVersionUID = -2227012160825551352L;

        private UnregisteredException(String message) {
            super(message);
        }
    }

    private static final class WebMappingUri implements Serializable {
        private static final long serialVersionUID = 6411134887844922562L;
        private final MappingUri<ActionInvocationProxy> m = new MappingUri<>();

        public synchronized final void put(String uri, ActionInvocationProxy proxy) {
            ActionInvocationProxy actionProxy = m.putIfAbsent(uri, proxy);
            if (actionProxy == null || actionProxy.equals(proxy)) return;
            String m = format("The url '%s' already exists \n%s \n%s ", //
                    uri, actionProxy.getMethod(), proxy.getMethod());
            throw new RuntimeException(m);
        }


        /**
         * 获取路径匹配值
         * @param uri           路径
         * @param suffixPattern 开启后缀匹配模式
         * @param trailingSlash 自动后缀路径模式匹配
         * @param func          回调
         * @return Action代理对象
         */
        public synchronized final ActionInvocationProxy get(String uri, boolean suffixPattern, //
                boolean trailingSlash, BiConsumer<String, String> func) {
            return m.get(uri, suffixPattern, trailingSlash, func);
        }

        /**
         * 获取所有Action代理对象
         * @return Action代理对象
         */
        public synchronized final MappingUri<ActionInvocationProxy> getAll() {
            return m;
        }

        public Collection<ActionInvocationProxy> values() {
            return m.values();
        }
    }
}
