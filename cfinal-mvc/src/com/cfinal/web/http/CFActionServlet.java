/**
 * Created the com.cfinal.web.http.CFPorxyServlet.java
 * @created 2017年8月22日 上午11:08:02
 * @version 1.0.0
 */
package com.cfinal.web.http;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cfinal.util.logger.CFLog;
import com.cfinal.web.CFServletContext;
import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.annotaion.CFControl;
import com.cfinal.web.http.interceptor.CFInterceptor;
import com.cfinal.web.http.render.CFRender;
import com.cfinal.web.http.view.CFView;

/**
 * com.cfinal.web.http.CFPorxyServlet.java
 * @author XChao
 */
public final class CFActionServlet {
	private String name; // 代理 Servlet名称
	private CFHttpServlet servlet; // 代理Servlet实例
	private CFControl control; // 代理Servlet实例注解
	private Method method; // 代理 Servlet方法对象
	private CFAction action; // 代理方法注解
	private String urlMapping; // 代理Servlet urlMapping
	private CFRender render; // 代理Servlet 处理器类型
	private CFView view; // 代理Servlet 默认视图处理器
	private String viewPath; // 代理Servlet 视图路径
	private final CFActionParameter parameter = new CFActionParameter(); // 代理Servlet 参数列表
	private final List<CFInterceptor> interceptors = new ArrayList<>(); // 拦截器

	public CFActionServlet(CFServletContext context, CFHttpServlet servlet, String conSuf, String conName,
		String conUrl, List<Class<? extends CFInterceptor>> interceptors, CFControl control, Method method,
		CFAction action) {
		this.servlet = servlet;
		this.control = control;
		this.method = method;
		this.action = action;
		this.view = context.getViewClazz();
		this.render = action.value().getRender(action, context);

		// 获取 Action 方法的 url 注解信息， 如果为空， 则默认为 controlUrl 内容
		String url = StringUtils.defaultIfEmpty(action.url(), method.getName());
		// 获取 Action 方法的 suffix 注解信息， 如果为空， 则默认为 controlSuffix 内容
		String suffix = StringUtils.defaultIfEmpty(action.suffix(), conSuf);
		if(StringUtils.isNotBlank(action.interceptor())) {
			// 如果Action配置了拦截器，则使用自己的拦截器
			interceptors = context.getInterceptor(action.interceptor());
		}
		// 设置CFHttpServlet 的 URL
		urlMapping = ("/" + conUrl + "/" + url + suffix).replaceAll("[/]+", "/");
		// 设置Action 方法名称， Action代理类名称
		name = (context.getContextPath() + "/" + urlMapping).replaceAll("[/]+", "/");
		// 如果该控制器连接存在时,则抛出异常
		if(context.getCFServlet(name) != null) {
			throw new RuntimeException("The request url is exists: " + name);
		}
		// 设置Action 方法所对应的默认渲染页面的绝对路径FullPath
		viewPath = ("pages/" + conName + "/" + method.getName()).replaceAll("[/]+", "/");
		// 处理拦截器堆栈
		for (int i = 0; interceptors != null && i < interceptors.size(); i++) {
			this.interceptors.add(context.getBean(interceptors.get(i)));
		}
		CFLog.debug("Scanner Action name ： " + name);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the servlet
	 */
	public CFHttpServlet getServlet() {
		return servlet;
	}

	/**
	 * @return the control
	 */
	public CFControl getControl() {
		return control;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @return the action
	 */
	public CFAction getAction() {
		return action;
	}

	/**
	 * @return the urlMapping
	 */
	public String getUrlMapping() {
		return urlMapping;
	}

	/**
	 * @return the render
	 */
	public CFRender getRender() {
		return render;
	}

	/**
	 * @return the view
	 */
	public CFView getView() {
		return view;
	}

	/**
	 * @return the viewPath
	 */
	public String getViewPath() {
		return viewPath;
	}

	/**
	 * @return the parameter
	 */
	public CFActionParameter getParameter() {
		return parameter;
	}

	/**
	 * @return the interceptors
	 */
	public List<CFInterceptor> getInterceptors() {
		return interceptors;
	}

	public String execute(Object... args) throws Exception {
		Object result = method.invoke(servlet, args);
		if(result != null && String.class.isInstance(result)) {
			return String.valueOf(result);
		}
		return this.viewPath;
	}
}
