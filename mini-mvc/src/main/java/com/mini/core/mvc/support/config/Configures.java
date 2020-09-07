package com.mini.core.mvc.support.config;

import com.mini.core.mvc.argument.ArgumentResolver;
import com.mini.core.mvc.factory.DefaultLocaleFactory;
import com.mini.core.mvc.factory.LocaleFactory;
import com.mini.core.mvc.handler.ExceptionHandler;
import com.mini.core.mvc.interceptor.ActionInterceptor;
import com.mini.core.mvc.support.ActionSupportProxy;
import com.mini.core.mvc.view.JspPageViewResolver;
import com.mini.core.mvc.view.PageViewResolver;
import com.mini.core.mvc.annotation.Action;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;
import static javax.servlet.DispatcherType.*;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public final class Configures implements EventListener, Serializable {
    private static final PathMatcher MATCHER = new AntPathMatcher();
    private static final Logger log = getLogger(Configures.class);
    private static final String TEMP_KEY = "java.io.tmpdir";

    private final AnnotationConfigApplicationContext application;

    @Autowired
    public Configures(AnnotationConfigApplicationContext application) {
        this.application = application;
    }

    /**
     * 字符集编码
     */
    private String encoding = UTF_8.name();

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    /**
     * 是否支持异步处理
     */
    private boolean asyncSupported = true;

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    /**
     * 获取默认请求拦截
     */
    private String defaultMapping = "*.htm";

    public void setDefaultMapping(String defaultMapping) {
        this.defaultMapping = defaultMapping;
    }

    public String getDefaultMapping() {
        return defaultMapping;
    }

    /**
     * 是否支持文件上传
     */
    private boolean multipartEnabled = true;

    public void setMultipartEnabled(boolean multipartEnabled) {
        this.multipartEnabled = multipartEnabled;
    }

    public boolean isMultipartEnabled() {
        return multipartEnabled;
    }

    /**
     * 上传文件缓冲区大小
     */
    private int fileSizeThreshold = 0;

    public void setFileSizeThreshold(int fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

    public int getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    /**
     * 上传文件总大小限制
     */
    private long maxRequestSize = -1;

    public void setMaxRequestSize(long maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    public long getMaxRequestSize() {
        return maxRequestSize;
    }

    /**
     * 单个文件大小限制
     */
    private long maxFileSize = -1;

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * 上传文件临时路径
     */
    private String locationPath = Optional.of(TEMP_KEY)
            .map(System::getProperty)
            .map(File::new)
            .filter(f -> f.exists() || f.mkdirs())
            .map(File::getAbsolutePath)
            .orElseThrow();

    public void setLocationPath(String locationPath) {
        this.locationPath = locationPath;
    }

    public String getLocationPath() {
        return locationPath;
    }

    /**
     * 日期时间格式
     */
    private String dateTimeFormat = "yyyy-MM-dd HH[:mm[:ss[.SSS]]]";

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * 日期格式
     */
    private String dateFormat = "yyyy[-MM[-dd]]";

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * 时间格式
     */
    private String timeFormat = "HH[:mm[:ss]]";

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    /**
     * 视图前缀
     */
    private String ViewPrefix = "/WEB-INF/";

    public void setViewPrefix(String viewPrefix) {
        ViewPrefix = viewPrefix;
    }

    public String getViewPrefix() {
        return ViewPrefix;
    }

    /**
     * 视图后缀
     */
    private String viewSuffix = ".jsp";

    public void setViewSuffix(String viewSuffix) {
        this.viewSuffix = viewSuffix;
    }

    public String getViewSuffix() {
        return viewSuffix;
    }

    /**
     * 跨域请求方法配置
     */
    private String accessControlAllowMethods = "POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD";

    public void setAccessControlAllowMethods(String accessControlAllowMethods) {
        this.accessControlAllowMethods = accessControlAllowMethods;
    }

    public String getAccessControlAllowMethods() {
        return accessControlAllowMethods;
    }

    /**
     * 跨域请求头设置
     */
    private String accessControlAllowHeaders = "x-requested-with, Content-Type";

    public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
        this.accessControlAllowHeaders = accessControlAllowHeaders;
    }

    public String getAccessControlAllowHeaders() {
        return accessControlAllowHeaders;
    }

    /**
     * 跨域请求域名设置
     */
    private String accessControlAllowOrigin = "*";

    public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
        this.accessControlAllowOrigin = accessControlAllowOrigin;
    }

    public String getAccessControlAllowOrigin() {
        return accessControlAllowOrigin;
    }

    /**
     * 跨域超时时间设置
     */
    private int accessControlMaxAge = 3600;

    public void setAccessControlMaxAge(int accessControlMaxAge) {
        this.accessControlMaxAge = accessControlMaxAge;
    }

    public int getAccessControlMaxAge() {
        return accessControlMaxAge;
    }

    /**
     * 跨域是否允许自定义请求头
     */
    private boolean accessControlAllowCredentials = true;

    public void setAccessControlAllowCredentials(boolean accessControlAllowCredentials) {
        this.accessControlAllowCredentials = accessControlAllowCredentials;
    }

    public boolean isAccessControlAllowCredentials() {
        return accessControlAllowCredentials;
    }

    /**
     * 请求映射容器
     */
    private final Map<String, Map<Action.Method, ActionSupportProxy>> mappings = new ConcurrentHashMap<>();

    /**
     * 根据URI获取一个Action代理对象
     *
     * @param requestUri 访问Action的URI
     * @return Action 代理对象
     */
    public final Map<Action.Method, ActionSupportProxy> getActionProxy(String requestUri) {
        String uri = Optional.ofNullable(requestUri).map(v -> { //
            return v.startsWith("/") ? v.substring(1) : v;
        }).orElseThrow();
        return Optional.ofNullable(mappings.get(uri)).orElseGet(() -> { //
            return mappings.keySet().stream().filter(k -> { //
                return MATCHER.match(k, uri); //
            }).min(MATCHER.getPatternComparator(uri))
                    .map(mappings::get).orElse(null);
        });
    }

    /**
     * 添加一个Action代理对象
     *
     * @param requestUri 请求URI
     * @param proxy      代理对象实例
     */
    public void addActionProxy(String requestUri, @NotNull ActionSupportProxy proxy) {
        String uri = Optional.ofNullable(requestUri).map(v -> {
            return v.startsWith("/") ? v.substring(1) : v; //
        }).orElseThrow();
        Optional.ofNullable(getActionProxy(requestUri)).ifPresentOrElse(map -> {
            Stream.of(proxy.getSupportMethod()).forEach(m -> {
                if (map.get(m) != null) {
                    String message = "The url '%s' already exists \n%s \n%s ";  //
                    throw new RuntimeException(format(message, uri, map.get(m).getMethod(), proxy.getMethod()));
                }
                map.put(m, proxy);
            });
            mappings.put(uri, map);
        }, () -> {
            // 创建请求映射
            HashMap<Action.Method, ActionSupportProxy> map = new HashMap<>();
            for (Action.Method method : proxy.getSupportMethod()) {
                map.put(method, proxy); //
            }
            // 添加对象到请求映射
            mappings.put(uri, map);
            log.debug("Register Action: " + uri);
        });
    }

    /**
     * 获取所有的Action代理对象
     *
     * @return 所有的Action代理对象
     */
    @NotNull
    public Set<ActionSupportProxy> getActionProxySet() {
        return mappings.values().stream().flatMap(v -> {
            return v.values().stream(); //
        }).collect(Collectors.toSet());
    }

    /**
     * 监听器容器
     */
    private final Set<EventListener> listeners = new HashSet<>();

    public void addListener(Class<? extends EventListener> listener) {
        listeners.add(requireNonNull(application.getBean(listener)));
    }

    @NotNull
    public Set<EventListener> getListeners() {
        return listeners;
    }

    /**
     * Http Servlet 容器
     */
    private final Map<Class<? extends HttpServlet>, RegistrationServlet> servlets = new ConcurrentHashMap<>();

    /**
     * 添加一个Servlet
     *
     * @param servlet  HttpServlet Class 对象
     * @param consumer HttpServlet 设置回调
     */
    public void addServlet(Class<? extends HttpServlet> servlet, Consumer<RegistrationServlet> consumer) {
        RegistrationServlet register = servlets.computeIfAbsent(servlet, clazz -> {
            var instance = requireNonNull(application.getBean(clazz));
            RegistrationServlet val = new RegistrationServlet(instance);
            val.setFileSizeThreshold(getFileSizeThreshold());
            val.setMultipartEnabled(isMultipartEnabled());
            val.setMaxRequestSize(getMaxRequestSize());
            val.setAsyncSupported(isAsyncSupported());
            val.setMaxFileSize(getMaxFileSize());
            val.setName(servlet.getName());
            val.setLoadOnStartup(1);
            return val;
        });
        consumer.accept(register);
    }

    /**
     * 获取所有Servlet
     *
     * @return Servlet
     */
    @NotNull
    public Collection<RegistrationServlet> getServlets() {
        return servlets.values();
    }

    /**
     * Filter 容器
     */
    private final Map<Class<? extends Filter>, RegistrationFilter> filters = new ConcurrentHashMap<>();

    /**
     * 添加一个过虑器
     *
     * @param filter   Filter Class 对象
     * @param consumer Filter 设置回调
     */
    public void addFilter(Class<? extends Filter> filter, Consumer<RegistrationFilter> consumer) {
        RegistrationFilter register = filters.computeIfAbsent(filter, clazz -> {
            var instance = requireNonNull(application.getBean(clazz));
            RegistrationFilter val = new RegistrationFilter(instance);
            val.addDispatcherType(REQUEST, FORWARD, INCLUDE, ASYNC, ERROR);
            val.setAsyncSupported(asyncSupported);
            val.setName(filter.getName());
            val.setMatchAfter(true);
            return val;
        });
        consumer.accept(register);
    }

    /**
     * 删除一个系统默认的过虑器
     *
     * @param filter Filter Class 对象
     */
    public void removeFilter(Class<? extends Filter> filter) {
        filters.remove(filter);
    }

    /**
     * 获取所有过虑器
     *
     * @return 过虑器
     */
    @NotNull
    public Collection<RegistrationFilter> getFilters() {
        return filters.values();
    }

    /**
     * 参数解析器容器
     */
    private final Set<ArgumentResolver> argumentResolverSet = new ConcurrentSkipListSet<>();

    public void addArgumentResolver(Class<? extends ArgumentResolver> resolver) {
        argumentResolverSet.add(requireNonNull(application.getBean(resolver)));
    }

    @NotNull
    public Set<ArgumentResolver> getArgumentResolverSet() {
        return argumentResolverSet;
    }

    /**
     * 全局拦截器容器
     */
    private final List<ActionInterceptor> interceptorList = new CopyOnWriteArrayList<>();

    public void addInterceptor(Class<? extends ActionInterceptor> interceptor) {
        interceptorList.add(requireNonNull(application.getBean(interceptor)));
    }

    @NotNull
    public List<ActionInterceptor> getInterceptorList() {
        return interceptorList;
    }

    /**
     * 全局异常处理器容器
     */
    private final List<ExceptionHandler> exceptionHandlerList = new CopyOnWriteArrayList<>();

    /**
     * 注册一个异常处理器
     *
     * @param handler 处理器
     */
    public void addExceptionHandler(Class<? extends ExceptionHandler> handler) {
        exceptionHandlerList.add(requireNonNull(application.getBean(handler)));
        exceptionHandlerList.sort(comparingInt(ExceptionHandler::handlerOnExecute));
    }

    @NotNull
    public List<ExceptionHandler> getExceptionHandlerList() {
        return exceptionHandlerList;
    }

    /**
     * 页面类型视图解析器
     */
    private PageViewResolver pageViewResolver = new JspPageViewResolver(this);

    public void setPageViewResolver(Class<? extends PageViewResolver> resolver) {
        pageViewResolver = requireNonNull(application.getBean(resolver));
    }

    @NotNull
    public PageViewResolver getPageViewResolver() {
        return pageViewResolver;
    }

    /**
     * 国际化工厂
     */
    private LocaleFactory localeFactory = new DefaultLocaleFactory();

    public void setLocaleFactory(Class<? extends LocaleFactory> factory) {
        localeFactory = requireNonNull(application.getBean(factory));
    }

    @NotNull
    public LocaleFactory getLocaleFactory() {
        return localeFactory;
    }
}
