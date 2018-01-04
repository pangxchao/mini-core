/**
 * Created the com.cfinal.web.core.CFServletContext.java
 * @created 2017年8月20日 下午11:29:59
 * @version 1.0.0
 */
package com.cfinal.web;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import com.cfinal.web.http.CFActionServlet;
import com.cfinal.web.http.interceptor.CFInterceptor;
import com.cfinal.web.http.view.CFView;
import com.cfinal.web.timmer.CFTimmer;

/**
 * com.cfinal.web.core.CFServletContext.java
 * @author XChao
 */
public class CFServletContext implements ServletContext {
	private static CFServletContext instance;

	static void createNewInstance(ServletContext servletContext) {
		instance = new CFServletContext(servletContext);
	}
	public static CFServletContext getInstance() {
		if(CFServletContext.instance == null) {
			throw new RuntimeException("Uninitialized instance.");
		}
		
		return CFServletContext.instance;
	}

	private final ServletContext context; // ServletContext 实例
	private String encoding = "UTF-8"; // 编码过虑
	private String actionSuffix = ".htm"; // 默认所有URL后缀名
	private long workerid = 0; // 默认主键生成器的机器编码
	private String loginUrl;
	private CFView viewClazz;
	private MultipartConfigElement multipartConfigElement;
	private final Map<String, List<Class<? extends CFInterceptor>>> interceptor = new HashMap<>();
	private final Map<Class<? extends CFTimmer>, String> timmers = new HashMap<>();
	private final Map<String, CFActionServlet> servlets = new HashMap<>();
	private final Map<Class<?>, Object> beans = new HashMap<>();

	private CFServletContext(ServletContext context) {
		this.context = context;
	}

	/**
	 * 得到web应用的物理地址
	 * @return 得到web应用的实际物理根地址
	 */
	public String getWebRealPath() {
		return this.getRealPath("/") + "/";
	}

	/**
	 * 获取web地址的根地址,该问该web项目的根URL
	 * @return web地址的根地址
	 */
	public String getWebRootPath() {
		return this.getContextPath() + "/";
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the actionSuffix
	 */
	public String getActionSuffix() {
		return actionSuffix;
	}

	/**
	 * @param actionSuffix the actionSuffix to set
	 */
	public void setActionSuffix(String actionSuffix) {
		this.actionSuffix = actionSuffix;
	}

	/**
	 * @return the workerid
	 */
	public long getWorkerid() {
		return workerid;
	}

	/**
	 * @param workerid the workerid to set
	 */
	public void setWorkerid(long workerid) {
		this.workerid = workerid;
	}

	/**
	 * @return the loginUrl
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @param loginUrl the loginUrl to set
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * @return the viewClazz
	 */
	public CFView getViewClazz() {
		return viewClazz;
	}

	/**
	 * @param viewClazz the viewClazz to set
	 */
	public void setViewClazz(CFView viewClazz) {
		this.viewClazz = viewClazz;
	}

	/**
	 * @return the multipartConfigElement
	 */
	public MultipartConfigElement getMultipartConfigElement() {
		return multipartConfigElement;
	}

	/**
	 * @param multipartConfigElement the multipartConfigElement to set
	 */
	public void setMultipartConfigElement(MultipartConfigElement multipartConfigElement) {
		this.multipartConfigElement = multipartConfigElement;
	}

	/**
	 * 添加一个拦截器堆栈
	 * @param name
	 * @param interceptors
	 */
	public void addInterceptor(String name, List<Class<? extends CFInterceptor>> interceptors) {
		this.interceptor.put(name, interceptors);
	}

	/**
	 * 获取一个拦截器堆栈
	 * @param name
	 * @return
	 */
	public List<Class<? extends CFInterceptor>> getInterceptor(String name) {
		return this.interceptor.get(name);
	}

	/**
	 * 获取所有拦截器堆栈
	 * @return
	 */
	public Map<String, List<Class<? extends CFInterceptor>>> getInterceptors() {
		return this.interceptor;
	}

	/**
	 * 添加一个定时任务配置规则
	 * @param timmer 定时任务处理器
	 * @param rule 规则
	 */
	public void addTimmer(Class<? extends CFTimmer> timmer, String rule) {
		this.timmers.put(timmer, rule);
	}

	/**
	 * @return the timmers
	 */
	public Map<Class<? extends CFTimmer>, String> getTimmers() {
		return timmers;
	}

	/**
	 * 添加一个Action 代理
	 * @param action
	 */
	public void addCFServlet(CFActionServlet servlet) {
		this.servlets.put(servlet.getName(), servlet);
	}

	/**
	 * 获取一个Action代理
	 * @param actionName
	 * @return
	 */
	public CFActionServlet getCFServlet(String servletName) {
		return this.servlets.get(servletName);
	}

	/**
	 * 获取所有的 Action 代理
	 * @return
	 */
	public Map<String, CFActionServlet> getAllActionServlet() {
		return this.servlets;
	}

	/**
	 * 添加一个bean实例
	 * @param name
	 * @param instance
	 */
	public void addBean(Object instance) {
		this.beans.put(instance.getClass(), instance);
	}

	/**
	 * 根据名称， 获取实例信息
	 * @param name
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(Class<? extends T> clazz) {
		Object bean = this.beans.get(clazz);
		return bean == null ? null : clazz.cast(bean);
	}

	/**
	 * 根据指定类的名称，获取当前类的所有子类实例
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getBeanList(Class<T> clazz) {
		List<T> list = new ArrayList<>();
		for (Class<?> clazz1 : this.beans.keySet()) {
			Object bean = this.beans.get(clazz1);
			if(clazz1.isAssignableFrom(clazz)) {
				list.add(clazz.cast(bean));
			}
		}
		return list;
	}

	public FilterRegistration.Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
		return this.context.addFilter(arg0, arg1);
	}

	public FilterRegistration.Dynamic addFilter(String arg0, Filter arg1) {
		return this.context.addFilter(arg0, arg1);
	}

	public FilterRegistration.Dynamic addFilter(String arg0, String arg1) {
		return this.context.addFilter(arg0, arg1);
	}

	public void addListener(Class<? extends EventListener> arg0) {
		this.context.addListener(arg0);
	}

	public void addListener(String arg0) {
		this.context.addListener(arg0);
	}

	public <T extends EventListener> void addListener(T arg0) {
		this.context.addListener(arg0);
	}

	public ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1) {
		return this.context.addServlet(arg0, arg1);
	}

	public ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1) {
		return this.context.addServlet(arg0, arg1);
	}

	public ServletRegistration.Dynamic addServlet(String arg0, String arg1) {
		return this.context.addServlet(arg0, arg1);
	}

	public <T extends Filter> T createFilter(Class<T> arg0) throws ServletException {
		return this.context.createFilter(arg0);
	}

	public <T extends EventListener> T createListener(Class<T> arg0) throws ServletException {
		return this.context.createListener(arg0);
	}

	public <T extends Servlet> T createServlet(Class<T> arg0) throws ServletException {
		return this.context.createServlet(arg0);
	}

	public void declareRoles(String... arg0) {
		this.context.declareRoles(arg0);
	}

	public Object getAttribute(String arg0) {
		return this.context.getAttribute(arg0);
	}

	public Enumeration<String> getAttributeNames() {
		return this.context.getAttributeNames();
	}

	public ClassLoader getClassLoader() {
		return this.context.getClassLoader();
	}

	public ServletContext getContext(String arg0) {
		return this.context.getContext(arg0);
	}

	public String getContextPath() {
		return this.context.getContextPath();
	}

	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		return this.context.getDefaultSessionTrackingModes();
	}

	public int getEffectiveMajorVersion() {
		return this.context.getEffectiveMajorVersion();
	}

	public int getEffectiveMinorVersion() {
		return this.context.getEffectiveMinorVersion();
	}

	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return this.context.getEffectiveSessionTrackingModes();
	}

	public FilterRegistration getFilterRegistration(String arg0) {
		return this.context.getFilterRegistration(arg0);
	}

	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return this.context.getFilterRegistrations();
	}

	public String getInitParameter(String arg0) {
		return this.context.getInitParameter(arg0);
	}

	public Enumeration<String> getInitParameterNames() {
		return this.context.getInitParameterNames();
	}

	public JspConfigDescriptor getJspConfigDescriptor() {
		return this.context.getJspConfigDescriptor();
	}

	public int getMajorVersion() {
		return this.context.getMajorVersion();
	}

	public String getMimeType(String arg0) {
		return this.context.getMimeType(arg0);
	}

	public int getMinorVersion() {
		return this.context.getMinorVersion();
	}

	public RequestDispatcher getNamedDispatcher(String arg0) {
		return this.context.getNamedDispatcher(arg0);
	}

	public String getRealPath(String arg0) {
		return this.context.getRealPath(arg0);
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		return this.context.getRequestDispatcher(arg0);
	}

	public URL getResource(String arg0) throws MalformedURLException {
		return this.context.getResource(arg0);
	}

	public InputStream getResourceAsStream(String arg0) {
		return this.context.getResourceAsStream(arg0);
	}

	public Set<String> getResourcePaths(String arg0) {
		return this.context.getResourcePaths(arg0);
	}

	public String getServerInfo() {
		return this.context.getServerInfo();
	}

	@Deprecated
	public Servlet getServlet(String arg0) throws ServletException {
		return this.context.getServlet(arg0);
	}

	public String getServletContextName() {
		return this.context.getServletContextName();
	}

	@Deprecated
	public Enumeration<String> getServletNames() {
		return this.context.getServletNames();
	}

	public ServletRegistration getServletRegistration(String arg0) {
		return this.context.getServletRegistration(arg0);
	}

	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		return this.context.getServletRegistrations();
	}

	@Deprecated
	public Enumeration<Servlet> getServlets() {
		return this.context.getServlets();
	}

	public SessionCookieConfig getSessionCookieConfig() {
		return this.context.getSessionCookieConfig();
	}

	public String getVirtualServerName() {
		return this.context.getVirtualServerName();
	}

	@Deprecated
	public void log(Exception arg0, String arg1) {
		this.context.log(arg0, arg1);
	}

	public void log(String arg0, Throwable arg1) {
		this.context.log(arg0, arg1);
	}

	public void log(String arg0) {
		this.context.log(arg0);
	}

	public void removeAttribute(String arg0) {
		this.context.removeAttribute(arg0);
	}

	public void setAttribute(String arg0, Object arg1) {
		this.context.setAttribute(arg0, arg1);
	}

	public boolean setInitParameter(String arg0, String arg1) {
		return this.context.setInitParameter(arg0, arg1);
	}

	public void setSessionTrackingModes(Set<SessionTrackingMode> arg0) {
		this.context.setSessionTrackingModes(arg0);
	}
}
