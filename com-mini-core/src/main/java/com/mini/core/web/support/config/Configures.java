package com.mini.core.web.support.config;

import com.google.inject.Injector;
import com.mini.core.util.matcher.PathMatcher;
import com.mini.core.util.matcher.PathMatcherAnt;
import com.mini.core.web.annotation.Action.Method;
import com.mini.core.web.argument.ArgumentResolver;
import com.mini.core.web.handler.ExceptionHandler;
import com.mini.core.web.interceptor.ActionInterceptor;
import com.mini.core.web.support.ActionSupportProxy;
import com.mini.core.web.view.PageViewResolver;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;
import static javax.servlet.DispatcherType.*;
import static org.slf4j.LoggerFactory.getLogger;

@Singleton
public final class Configures implements EventListener, Serializable {
	private static final Logger LOGGER = getLogger(Configures.class);
	private static final PathMatcher MATCHER = new PathMatcherAnt();
	private static final long serialVersionUID = -1L;
	private final Injector injector;
	
	@Inject
	public Configures(Injector injector) {
		this.injector = injector;
	}
	
	@Inject
	@Named("CharsetEncoding")
	private String charsetEncoding;
	
	/**
	 * 获取字符集编码
	 * @return 默认为“UTF-8”
	 */
	public String getCharsetEncoding() {
		return charsetEncoding;
	}
	
	/**
	 * 设置字符集编码
	 * @param charsetEncoding 字符集编码
	 */
	public void setCharsetEncoding(String charsetEncoding) {
		this.charsetEncoding = charsetEncoding;
	}
	
	@Inject
	@Named("AsyncSupported")
	private boolean asyncSupported;
	
	/**
	 * 是否支持异步处理
	 * @return 默认为“true”
	 */
	public boolean isAsyncSupported() {
		return asyncSupported;
	}
	
	/**
	 * 是否支持异步处理
	 * @param asyncSupported true-是
	 */
	public void setAsyncSupported(boolean asyncSupported) {
		this.asyncSupported = asyncSupported;
	}
	
	@Inject
	@Named("DefaultMapping")
	private String defaultMapping;
	
	/**
	 * 获取默认请求拦截
	 * @return 默认为“*.htm”
	 */
	public String getDefaultMapping() {
		return defaultMapping;
	}
	
	/**
	 * 设置默认请求拦截
	 * @param defaultMapping 请求拦截
	 */
	public void setDefaultMapping(String defaultMapping) {
		this.defaultMapping = defaultMapping;
	}
	
	@Inject
	@Named("MultipartEnabled")
	private boolean multipartEnabled;
	
	/**
	 * 是否支持文件上传
	 * @return 默认为“true”
	 */
	public boolean isMultipartEnabled() {
		return multipartEnabled;
	}
	
	/**
	 * 是否支持文件上传
	 * @param multipartEnabled true-是
	 */
	public void setMultipartEnabled(boolean multipartEnabled) {
		this.multipartEnabled = multipartEnabled;
	}
	
	@Inject
	@Named("FileSizeThreshold")
	private int fileSizeThreshold;
	
	/**
	 * 获取上传文件缓冲区大小
	 * @return 默认为“0”
	 */
	public int getFileSizeThreshold() {
		return fileSizeThreshold;
	}
	
	/**
	 * 上传文件缓冲区大小
	 * @param fileSizeThreshold 0-直接写入文件
	 */
	public void setFileSizeThreshold(int fileSizeThreshold) {
		this.fileSizeThreshold = fileSizeThreshold;
	}
	
	@Inject
	@Named("MaxRequestSize")
	private long maxRequestSize;
	
	/**
	 * 获取上传文件总大小限制
	 * @return 默认为“-1”表示不限制
	 */
	public long getMaxRequestSize() {
		return maxRequestSize;
	}
	
	/**
	 * 上传文件总大小限制
	 * @param maxRequestSize -1 不限制
	 */
	public void setMaxRequestSize(int maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}
	
	@Inject
	@Named("MaxFileSize")
	private long maxFileSize;
	
	/**
	 * 获取上传文件单个文件大小限制
	 * @return 默认为“-1”表示不限制
	 */
	public long getMaxFileSize() {
		return maxFileSize;
	}
	
	/**
	 * 上传文件单个文件大小限制
	 * @param maxFileSize -1 不限制
	 */
	public void setMaxFileSize(long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	
	@Inject
	@Named("LocationPath")
	private String locationPath;
	
	/**
	 * 获取上传文件临时路径
	 * @return 默认为系统临时目录
	 */
	public String getLocationPath() {
		return locationPath;
	}
	
	/**
	 * 上传文件临时路径
	 * @param locationPath 上传文件临时路径
	 */
	public void setLocationPath(String locationPath) {
		this.locationPath = locationPath;
	}
	
	@Inject
	@Named("DateTimeFormat")
	private String dateTimeFormat;
	
	/**
	 * 获取日期时间格式
	 * @return 默认为“yyyy-MM-dd HH[:mm[:ss]]”
	 */
	public String getDateTimeFormat() {
		return dateTimeFormat;
	}
	
	/**
	 * 日期时间格式
	 * @param dateTimeFormat 日期时间格式
	 */
	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}
	
	@Inject
	@Named("DateFormat")
	private String dateFormat;
	
	/**
	 * 获取日期格式
	 * @return 默认为“yyyy[-MM[-dd]]”
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	
	/**
	 * 日期格式
	 * @param dateFormat 日期格式
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	@Inject
	@Named("TimeFormat")
	private String timeFormat;
	
	/**
	 * 获取时间格式
	 * @return 默认为“HH[:mm[:ss]]”
	 */
	public String getTimeFormat() {
		return timeFormat;
	}
	
	/**
	 * 时间格式
	 * @param timeFormat 时间格式
	 */
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	
	@Inject
	@Named("ViewPrefix")
	private String ViewPrefix;
	
	/**
	 * 获取视图前缀
	 * @return 默认为“/WEB-INF/”
	 */
	public String getViewPrefix() {
		return ViewPrefix;
	}
	
	/**
	 * 视图前缀
	 * @param viewPrefix 视图前缀
	 */
	public void setViewPrefix(String viewPrefix) {
		ViewPrefix = viewPrefix;
	}
	
	@Inject
	@Named("ViewSuffix")
	private String viewSuffix;
	
	/**
	 * 获取视图后缀
	 * @return 默认为“.ftl”
	 */
	public String getViewSuffix() {
		return viewSuffix;
	}
	
	/**
	 * 视图后缀
	 * @param viewSuffix 视图后缀
	 */
	public void setViewSuffix(String viewSuffix) {
		this.viewSuffix = viewSuffix;
	}
	
	@Inject
	@Named("AccessControlAllowMethods")
	private String accessControlAllowMethods;
	
	/**
	 * 获取跨域请求方法配置
	 * @return 默认为“POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD”
	 */
	public String getAccessControlAllowMethods() {
		return accessControlAllowMethods;
	}
	
	/**
	 * 跨域请求方法配置
	 * @param accessControlAllowMethods 跨域请求方法配置
	 */
	public void setAccessControlAllowMethods(String accessControlAllowMethods) {
		this.accessControlAllowMethods = accessControlAllowMethods;
	}
	
	@Inject
	@Named("AccessControlAllowHeaders")
	private String accessControlAllowHeaders;
	
	/**
	 * 获取跨域请求头设置
	 * <p> x-requested-with表示AJAX请求</p>
	 * @return 默认为“x-requested-with, Content-Type”
	 */
	public String getAccessControlAllowHeaders() {
		return accessControlAllowHeaders;
	}
	
	/**
	 * 跨域请求头设置
	 * @param accessControlAllowHeaders 跨域请求头设置
	 */
	public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
		this.accessControlAllowHeaders = accessControlAllowHeaders;
	}
	
	@Inject
	@Named("AccessControlAllowOrigin")
	private String accessControlAllowOrigin;
	
	/**
	 * 获取跨域请求域名设置
	 * @return 默认为“*”
	 */
	public String getAccessControlAllowOrigin() {
		return accessControlAllowOrigin;
	}
	
	/**
	 * 跨域请求域名设置
	 * @param accessControlAllowOrigin 跨域请求域名设置
	 */
	public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
		this.accessControlAllowOrigin = accessControlAllowOrigin;
	}
	
	@Inject
	@Named("AccessControlMaxAge")
	private int accessControlMaxAge;
	
	/**
	 * 获取跨域超时时间设置
	 * @return 默认为“3600”
	 */
	public int getAccessControlMaxAge() {
		return accessControlMaxAge;
	}
	
	/**
	 * 跨域超时时间设置
	 * @param accessControlMaxAge 跨域超时时间设置
	 */
	public void setAccessControlMaxAge(int accessControlMaxAge) {
		this.accessControlMaxAge = accessControlMaxAge;
	}
	
	@Inject
	@Named("AccessControlAllowCredentials")
	private boolean accessControlAllowCredentials;
	
	/**
	 * 设置跨域是否允许自定义请求头
	 * @return 跨域是否允许自定义请求头
	 */
	public boolean isAccessControlAllowCredentials() {
		return accessControlAllowCredentials;
	}
	
	/**
	 * 跨域是否允许自定义请求头
	 * @param accessControlAllowCredentials 默认-true
	 */
	public void setAccessControlAllowCredentials(boolean accessControlAllowCredentials) {
		this.accessControlAllowCredentials = accessControlAllowCredentials;
	}
	
	@Inject
	@Named("CacheControl")
	private String cacheControl;
	
	/**
	 * 获取缓存控制器
	 * @return 默认为“No-Cache”
	 */
	public String getCacheControl() {
		return cacheControl;
	}
	
	/**
	 * 缓存控制器
	 * @param cacheControl 缓存控制器
	 */
	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}
	
	@Inject
	@Named("CachePragma")
	private String cachePragma;
	
	/**
	 * 获取缓存标注
	 * @return 默认为“No-Cache”
	 */
	public String getCachePragma() {
		return cachePragma;
	}
	
	/**
	 * 缓存标注
	 * @param cachePragma 缓存标注
	 */
	public void setCachePragma(String cachePragma) {
		this.cachePragma = cachePragma;
	}
	
	@Inject
	@Named("CacheExpires")
	private int cacheExpires;
	
	/**
	 * 获取缓存过期时间
	 * @return 默认为“0”
	 */
	public int getCacheExpires() {
		return cacheExpires;
	}
	
	/**
	 * 缓存过期时间
	 * @param cacheExpires 缓存过期时间
	 */
	public void setCacheExpires(int cacheExpires) {
		this.cacheExpires = cacheExpires;
	}
	
	// 请求映射容器
	private final MappingMap mappings = new MappingMap();
	
	/**
	 * 根据URI获取一个Action代理对象
	 * @param requestUri 访问Action的URI
	 * @return Action 代理对象
	 */
	public final Map<Method, ActionSupportProxy> getActionProxy(String requestUri) {
		// 处理URL开头的 “/”
		String uri = Optional.ofNullable(requestUri).map(v -> {
			return v.startsWith("/") ? v.substring(1) : v; //
		}).orElseThrow();
		// 获取请求映射对象
		return Optional.ofNullable(mappings.get(uri)).orElseGet(() -> //
			mappings.keySet().stream()
				.filter(k -> MATCHER.match(k, uri))
				.min(MATCHER.getPatternComparator(uri))
				.map(mappings::get)
				.orElse(null));
	}
	
	/**
	 * 添加一个Action代理对象
	 * @param requestUri 请求URI
	 * @param proxy      代理对象实例
	 */
	public void addActionProxy(String requestUri, ActionSupportProxy proxy) {
		// 处理URL开头的 “/”
		String uri = Optional.ofNullable(requestUri).map(v -> {
			return v.startsWith("/") ? v.substring(1) : v; //
		}).orElseThrow();
		// 获取方法映射
		Optional.ofNullable(getActionProxy(requestUri)).ifPresentOrElse(map -> {
			// 获取两个对象的交集，如果交集不为空，则表示有冲突，打印异常信息
			Stream.of(proxy.getSupportMethod()).forEach(m -> {
				if (map.get(m) != null) {
					String message = "The url '%s' already exists \n%s \n%s ";  //
					throw new RuntimeException(format(message, uri, map.get(m) //
						.getMethod(), proxy.getMethod()));
				}
				map.put(m, proxy);
			});
			mappings.put(uri, map);
		}, () -> {
			// 创建请求映射
			HashMap<Method, ActionSupportProxy> map = new HashMap<>();
			for (Method method : proxy.getSupportMethod()) {
				map.put(method, proxy); //
			}
			// 添加对象到请求映射
			mappings.put(uri, map);
			LOGGER.debug("Register Action: " + uri);
		});
	}
	
	/**
	 * 获取所有的Action代理对象
	 * @return 所有的Action代理对象
	 */
	@Nonnull
	public final Set<ActionSupportProxy> getActionProxySet() {
		return mappings.values().stream().flatMap(v -> {
			return v.values().stream(); //
		}).collect(Collectors.toSet());
	}
	
	// 监听器容器
	private final EventListeners listeners = new EventListeners();
	
	/**
	 * 添加一个监听器
	 * @param listener 监听器
	 */
	public final void addListener(Class<? extends EventListener> listener) {
		listeners.add(injector.getInstance(listener));
	}
	
	/**
	 * 获取所有监听器
	 * @return 监听器
	 */
	@Nonnull
	public final Set<EventListener> getListeners() {
		return listeners;
	}
	
	// Http Servlet 容器
	private final HttpServlets servlets = new HttpServlets();
	
	/**
	 * 添加一个Servlet
	 * @param servlet  HttpServlet Class 对象
	 * @param consumer HttpServlet 设置回调
	 */
	public final void addServlet(Class<? extends HttpServlet> servlet, Consumer<RegistrationServlet> consumer) {
		servlets.computeIfAbsent(servlet, clazz -> {
			var val = new RegistrationServlet(injector.getInstance(servlet));
			val.setFileSizeThreshold(getFileSizeThreshold());
			val.setMultipartEnabled(isMultipartEnabled());
			val.setMaxRequestSize(getMaxRequestSize());
			val.setAsyncSupported(isAsyncSupported());
			val.setMaxFileSize(getMaxFileSize());
			val.setName(servlet.getName());
			val.setLoadOnStartup(1);
			consumer.accept(val);
			return val;
		});
	}
	
	/**
	 * 获取所有Servlet
	 * @return Servlet
	 */
	@Nonnull
	public final Collection<RegistrationServlet> getServlets() {
		return servlets.values();
	}
	
	// Filter 容器
	private final Filters filters = new Filters();
	
	/**
	 * 添加一个过虑器
	 * @param filter   Filter Class 对象
	 * @param consumer Filter 设置回调
	 */
	public final void addFilter(Class<? extends Filter> filter, Consumer<RegistrationFilter> consumer) {
		filters.computeIfAbsent(filter, clazz -> {
			var val = new RegistrationFilter(injector.getInstance(clazz));
			val.addDispatcherType(REQUEST, FORWARD, INCLUDE, ASYNC, ERROR);
			val.setAsyncSupported(asyncSupported);
			val.setName(filter.getName());
			val.setMatchAfter(true);
			consumer.accept(val);
			return val;
		});
	}
	
	/**
	 * 删除一个系统默认的过虑器
	 * @param filter Filter Class 对象
	 */
	public final void removeFilter(Class<? extends Filter> filter) {
		filters.remove(filter);
	}
	
	/**
	 * 获取所有过虑器
	 * @return 过虑器
	 */
	@Nonnull
	public final Collection<RegistrationFilter> getFilters() {
		return filters.values();
	}
	
	// 参数解析器容器
	private final Arguments arguments = new Arguments();
	
	/**
	 * 添加一个参数解析器
	 * @param resolver 解析器类
	 */
	public final void addArgumentResolver(Class<? extends ArgumentResolver> resolver) {
		arguments.add(injector.getInstance(resolver));
	}
	
	/**
	 * 获取所有的参数解析器类
	 * @return 所有的参数解析器类
	 */
	@Nonnull
	public final Set<ArgumentResolver> getArgumentResolvers() {
		return arguments;
	}
	
	// 全局拦截器容器
	private final Interceptors interceptors = new Interceptors();
	
	/**
	 * 注册一个全局的拦截器
	 * @param interceptor 拦截器类对象
	 */
	public final void addInterceptor(Class<? extends ActionInterceptor> interceptor) {
		interceptors.add(injector.getInstance(interceptor));
	}
	
	/**
	 * 根据拦截器实现类获取拦截器实例
	 * @param interceptor 拦截器类对象
	 * @return 拦截器实例
	 */
	@Nonnull
	public final ActionInterceptor getInterceptor(Class<? extends ActionInterceptor> interceptor) {
		return requireNonNull(injector.getInstance(interceptor));
	}
	
	/**
	 * 获取全局拦截器对象
	 * @return 全局拦截器对象
	 */
	@Nonnull
	public final List<ActionInterceptor> getInterceptors() {
		return interceptors;
	}
	
	
	// 全局异常处理器容器
	private final Exceptions exceptions = new Exceptions();
	
	/**
	 * 注册一个异常处理器
	 * @param handler 处理器
	 */
	public final void addExceptionHandler(Class<? extends ExceptionHandler> handler) {
		exceptions.add(Objects.requireNonNull(injector.getInstance(handler)));
		exceptions.sort(comparingInt(ExceptionHandler::handlerOnExecute));
	}
	
	/**
	 * 获取所有的异常处理器
	 * @return 异常处理器列表
	 */
	@Nonnull
	public final List<ExceptionHandler> getExceptionHandlers() {
		return exceptions;
	}
	
	@Inject
	@Named("DefaultPageViewResolver")
	private PageViewResolver viewResolver;
	
	/**
	 * 设置页面类型视图解析器
	 * @param resolver 视图解析器
	 */
	public final void setPageViewResolver(Class<? extends PageViewResolver> resolver) {
		this.viewResolver = injector.getInstance(resolver);
	}
	
	/**
	 * 获取页面类型视图解析器
	 * @return 默认为“PageViewResolverFreemarker”
	 * @see com.mini.core.web.view.PageViewResolverFreemarker
	 */
	public final PageViewResolver getPageViewResolver() {
		return viewResolver;
	}
	
	// Http Servlet 容器
	private static final class HttpServlets extends HashMap<Class<? extends HttpServlet>, RegistrationServlet> {
		private static final long serialVersionUID = 3834736756734528897L;
	}
	
	// 请求映射容器
	private static final class MappingMap extends HashMap<String, Map<Method, ActionSupportProxy>> {
		private static final long serialVersionUID = 4474337286044300684L;
	}
	
	// Filter 容器
	private static final class Filters extends HashMap<Class<? extends Filter>, RegistrationFilter> {
		private static final long serialVersionUID = 8087460546039570803L;
	}
	
	// 公共拦截器容器
	private static final class Interceptors extends ArrayList<ActionInterceptor> {
		private static final long serialVersionUID = 2057470335933160662L;
	}
	
	// 异常处理器容器
	private static final class Exceptions extends ArrayList<ExceptionHandler> {
		private static final long serialVersionUID = -8302884931600436036L;
	}
	
	// 监听器容器
	private static final class EventListeners extends HashSet<EventListener> {
		private static final long serialVersionUID = 4351139032303720218L;
	}
	
	// 参数解析器容器
	private static final class Arguments extends HashSet<ArgumentResolver> {
		private static final long serialVersionUID = -1405872977825144406L;
	}
	
	
}
