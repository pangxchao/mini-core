package com.mini.core.web.servlet;

import com.google.inject.Injector;
import com.mini.core.util.StringUtil;
import com.mini.core.util.matcher.PathMatcher;
import com.mini.core.util.matcher.PathMatcherAnt;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Action;
import com.mini.core.web.interceptor.ActionInterceptor;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.model.IModel;
import com.mini.core.web.support.ActionSupportProxy;
import com.mini.core.web.support.config.Configures;
import com.mini.core.web.util.ResponseCode;
import com.mini.core.web.view.PageViewResolver;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.mini.core.util.ThrowsUtil.getLastInvocationTarget;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Stream.of;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * 默认的Servlet
 * @author xchao
 */
public abstract class AbstractDispatcherHttpServlet extends HttpServlet implements ResponseCode {
	private static final Logger LOGGER = getLogger(AbstractDispatcherHttpServlet.class.getName());
	private static final PathMatcher matcher = new PathMatcherAnt();
	private static final long serialVersionUID = 1L;
	private Configures configure;
	private Injector injector;
	
	public final Configures getConfigure() {
		return configure;
	}
	
	public final Injector getInjector() {
		return injector;
	}
	
	@Inject
	public final void setConfigure(Configures configure) {
		this.configure = configure;
	}
	
	@Inject
	public final void setInjector(Injector injector) {
		this.injector = injector;
	}
	
	@Override
	public final void init(ServletConfig config) throws ServletException {
		requireNonNull(configure);
		requireNonNull(injector);
		super.init(config);
	}
	
	@Override
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.doService(Action.Method.HEAD, request, response);
	}
	
	@Override
	protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.doService(Action.Method.GET, request, response);
	}
	
	@Override
	protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.doService(Action.Method.POST, request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.doService(Action.Method.PUT, request, response);
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.doService(Action.Method.DELETE, request, response);
	}
	
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.doService(Action.Method.OPTIONS, request, response);
	}
	
	/**
	 * ActionInvocation 核心处理方法
	 * @param method   提交数据是以哪种方法提交的
	 * @param request  HttpServletRequest 对象
	 * @param response HttpServletResponse 对象
	 */
	private void doService(Action.Method method, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取请求路径-URI
		String uri = StringUtil.strip(getActionProxyUri(request).strip(), "/"), path;
		// 找出当前 URI 对应的 ActionProxy 对象
		Map<Action.Method, ActionSupportProxy> proxyMap = configure.getActionProxy(uri);
		if (java.util.Objects.isNull(proxyMap)) {
			LOGGER.info("Not Found: " + uri);
			response.sendError(SC_NOT_FOUND);
			return;
		}
		// 根据请求方法获取具体映射,不支持时返回405错误
		final ActionSupportProxy proxy = proxyMap.get(method);
		if (java.util.Objects.isNull(proxy)) {
			response.sendError(SC_METHOD_NOT_ALLOWED);
			return;
		}
		// 根据请求方法获取具体映射,不支持时返回405错误
		if (of(proxy.getSupportMethod()).noneMatch(m -> m == method)) {
			response.sendError(SC_METHOD_NOT_ALLOWED);
			return;
		}
		// 获取 Ant 类型的URL中的参数信息
		Map<String, String> uriParam = new HashMap<>();
		if (matcher.isPattern((path = proxy.getViewPath()))) {
			uriParam.putAll(matcher.extractVariables(path, uri));
		}
		// 获取数据模型实例并验证是否为空
		PageViewResolver resolver = configure.getPageViewResolver();
		final IModel<?> model = proxy.getModel(resolver);
		try {
			// 获取拦截器列表的迭代器对象
			Iterator<ActionInterceptor> iterator = proxy.getInterceptors().iterator();
			// 获取控制类的实例对象
			Object instance = injector.getInstance(proxy.getClazz());
			// 创建 ActionInvocation 对象
			final ActionInvocation action = new ActionInvocation() {
				
				@Nonnull
				@Override
				public final Method getMethod() {
					return proxy.getMethod();
				}
				
				@Nonnull
				@Override
				public final Class<?> getClazz() {
					return proxy.getClazz();
				}
				
				@Nonnull
				@Override
				public final Object getInstance() {
					return instance;
				}
				
				@Nonnull
				@Override
				public final List<ActionInterceptor> getInterceptors() {
					return proxy.getInterceptors();
				}
				
				@Override
				public final String getViewPath() {
					return proxy.getViewPath();
				}
				
				@Nonnull
				@Override
				public final IModel<?> getModel() {
					return model;
				}
				
				@Nonnull
				@Override
				public final HttpServletRequest getRequest() {
					return request;
				}
				
				@Nonnull
				@Override
				public final HttpServletResponse getResponse() {
					return response;
				}
				
				@Override
				public final HttpSession getSession() {
					return request.getSession();
				}
				
				@Override
				public final ServletContext getServletContext() {
					return request.getServletContext();
				}
				
				@Nonnull
				@Override
				public final Map<String, String> getUriParameters() {
					return uriParam;
				}
				
				@Nonnull
				@Override
				public MiniParameter[] getParameters() {
					return proxy.getParameters();
				}
				
				@Nonnull
				@Override
				public synchronized final Object[] getParameterValues() {
					return Stream.of(proxy.getParameterHandlers()).map(param -> {
						return param.getValue(this); //
					}).toArray(Object[]::new);
				}
				
				@Override
				public synchronized final Object invoke() throws Throwable {
					try {
						if (iterator.hasNext()) {
							return iterator.next() //
								.invoke(this);
						}
						Object[] values = getParameterValues();
						return getMethod().invoke(instance, values);
					} catch (InvocationTargetException ex) {
						throw getLastInvocationTarget(ex);
					}
				}
			};
			
			// 调用目标方法
			action.invoke();
		} catch (Throwable exception) {
			//  默认错误码和错误信息
			model.setStatus(INTERNAL_SERVER_ERROR);
			model.setMessage("Service Error!");
			handler_each:
			for (var handler : configure.getExceptionHandlers()) {
				for (var e = exception; e != null; e = e.getCause()) {
					if (!handler.supportException(e)) continue;
					handler.handler(model, e, request, response);
					break handler_each;
				}
			}
		}
		
		try { // 返回数据
			model.onSubmit(request, response);
		} catch (Exception | Error exception) {
			LOGGER.error(exception.getMessage(), exception);
		}
	}
	
	/**
	 * 获取实际的 ActionProxy 对象的 Uri
	 * @param request HttpServletRequest 对象
	 * @return ActionProxy Uri
	 */
	protected abstract String getActionProxyUri(HttpServletRequest request);
}
