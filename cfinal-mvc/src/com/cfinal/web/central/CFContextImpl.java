/**
 * Created the com.cfinal.web.central.CFContextImpl.java
 * @created 2016年9月26日 下午3:53:33
 * @version 1.0.0
 */
package com.cfinal.web.central;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.interceptor.CFInterceptorPorxy;
import com.cfinal.web.preprocessor.CFPreprocessorPorxy;
import com.cfinal.web.render.CFRender;
import com.cfinal.web.scheduler.CFTaskPorxy;
import com.cfinal.web.view.CFViewPorxy;

/**
 * com.cfinal.web.central.CFContextImpl.java
 * @author XChao
 */
public class CFContextImpl implements CFContext {
	// 跨域配置信息
	private String accessControl = "*";
	private ServletContext context;
	// web服务器物理绝对地址
	private String webRealPath;
	// web服务Url访问绝对地址(不包含域名部分)
	private String webRootPath;
	// 编码设置
	private String encoding = "UTF-8";
	// URL访问后缀
	private String actionSuffix = ".htm";
	// 主键生成机器唯一编码
	private long workerId;
	// 初始化参数配置信息
	private final Map<String, String> parameters = new HashMap<>();
	// 初始化处理器代理信息
	private CFPreprocessorPorxy preprocessorPorxy;
	// 拦截器代理
	private final Map<String, CFInterceptorPorxy> interceptorsPorxy = new HashMap<>();
	// 拦截器堆栈信息
	private final Map<String, List<String>> interceptorStacks = new HashMap<>();
	// 定时任务执行代理类
	private final Map<String, CFTaskPorxy> tasksPorxy = new HashMap<>();
	// 控制器方法代理
	private final Map<String, CFActionPorxy> actionsPorxy = new HashMap<>();
	// 视图处理器
	private final Map<Class<? extends CFRender>, CFRender> renders = new HashMap<>();
	// 页面视图渲染器
	private final Map<String, CFViewPorxy> viewsPorxy = new HashMap<>();
	// 默认视渲染器名称
	private String defaultView;
	// 文件上传配置信息 上传缓冲区大小
	private int sizeThreshold = 4096;
	// 文件上配置信息 上传临时文件
	private String repository = "/temp";
	// 文件上传配置信息， 一次请求最大文件限制
	private long sizeMax = -1;
	// 登录地址配置
	private String loginUrl;

	public CFContextImpl(ServletContext servletContext) {
		this.context = servletContext;
		this.webRealPath = this.getRealPath("/") + "/";
		this.webRootPath = this.getContextPath() + "/";
	}

	public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
		return this.context.addFilter(arg0, arg1);
	}

	public Dynamic addFilter(String arg0, Filter arg1) {
		return this.context.addFilter(arg0, arg1);
	}

	public Dynamic addFilter(String arg0, String arg1) {
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

	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Class<? extends Servlet> arg1) {
		return this.context.addServlet(arg0, arg1);
	}

	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, Servlet arg1) {
		return this.context.addServlet(arg0, arg1);
	}

	public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0, String arg1) {
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

	/**
	 * response.setHeader("Access-Control-Allow-Origin", "*") 处理
	 * @return
	 */
	public String getAccessControl() {
		return this.accessControl;
	}

	/**
	 * 设置跨域访问
	 * @param accessControlAllowOrigin
	 */
	public void setAccessControl(String accessControl) {
		this.accessControl = accessControl;
	}

	/**
	 * 得到web应用的物理地址
	 * @return 得到web应用的实际物理根地址
	 */
	public String getWebRealPath() {
		return this.webRealPath;
	}

	/**
	 * 获取web地址的根地址,该问该web项目的根URL
	 * @return web地址的根地址
	 */
	public String getWebRootPath() {
		return this.webRootPath;
	}

	/**
	 * 设置web地址的根地址,该问该web项目的根URL
	 * @param webRootPath
	 */
	public void setWebRootPath(String webRootPath) {
		this.webRootPath = webRootPath;
	}

	/**
	 * 获取主键生成的机器码唯一ID
	 * @return the workerId
	 */
	public long getWorkerId() {
		return this.workerId;
	}

	/**
	 * 设置获取主萧索生成的机器唯一码
	 * @param workerId
	 */
	public void setWorkerId(long workerId) {
		this.workerId = workerId;
	}

	/**
	 * 获取使用编码
	 * @return
	 */
	public String getEncoding() {
		return this.encoding;
	}

	/**
	 * 设置编码
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 获取系统url访问后缀
	 * @return
	 */
	public String getActionSuffix() {
		return this.actionSuffix;
	}

	/**
	 * 设置系统Url访问后缀,设置后
	 * @param actionSuffix
	 */
	public void setActionSuffix(String actionSuffix) {
		this.actionSuffix = actionSuffix;
	}

	/**
	 * 获取一个初始化参数
	 * @param name
	 */
	public String getParameter(String name) {
		return this.parameters.get(name);
	}

	/**
	 * 获取所有初始化参数
	 * @return
	 */
	public Map<String, String> getParameters() {
		return this.parameters;
	}

	/**
	 * 添加一个初始化参数
	 * @param name 参数名称
	 * @param value 参数值
	 */
	public void addParameter(String name, String value) {
		this.parameters.put(name, value);
	}

	/**
	 * 获取初始化类
	 * @return
	 */
	public CFPreprocessorPorxy getPreprocessorPorxy() {
		return this.preprocessorPorxy;
	}

	/**
	 * 设置初始化处理类
	 * @param handler
	 */
	public void setPreprocessorPorxy(CFPreprocessorPorxy preprocessor) {
		this.preprocessorPorxy = preprocessor;
	}

	/**
	 * 获取一个拦截器
	 * @param filterName
	 * @return
	 */
	public CFInterceptorPorxy getInterceptor(String interceptorName) {
		return this.interceptorsPorxy.get(interceptorName);
	}

	/**
	 * 添加一个拦截器
	 * @param interceptorName
	 * @param interceptorPorxy
	 */
	public void addInterceptor(String interceptorName, CFInterceptorPorxy interceptorPorxy) {
		this.interceptorsPorxy.put(interceptorName, interceptorPorxy);
	}

	/**
	 * 添加一个拦截器堆栈
	 * @param name
	 * @return
	 */
	public List<String> getInterceptorStack(String name) {
		return this.interceptorStacks.get(name);
	}

	/**
	 * 添加一个拦截器堆栈
	 * @param name
	 * @param interceptorStack
	 */
	public void addInterceptorStack(String name, List<String> interceptorStack) {
		this.interceptorStacks.put(name, interceptorStack);
	}

	/**
	 * 获取一个定时任务
	 * @param taskName
	 * @return
	 */
	public CFTaskPorxy getTask(String taskName) {
		return this.tasksPorxy.get(taskName);
	}

	/**
	 * 添加一个定时任务
	 * @param taskName
	 * @param taskPorxy
	 */
	public void addTask(String taskName, CFTaskPorxy taskPorxy) {
		this.tasksPorxy.put(taskName, taskPorxy);
	}

	/**
	 * 得到一个Action 映射
	 * @param actionName
	 * @return
	 */
	public CFActionPorxy getAction(String actionName) {
		return this.actionsPorxy.get(actionName);
	}

	/**
	 * 获取所有的 Action 映射
	 * @return
	 */
	public Map<String, CFActionPorxy> getActionMap() {
		return this.actionsPorxy;
	}

	/**
	 * 添加一个Action 映射
	 * @param actionName
	 * @param actionPorxy
	 */
	public void addAction(String actionName, CFActionPorxy actionPorxy) {
		this.actionsPorxy.put(actionName, actionPorxy);
	}

	/**
	 * 获取一个处理器
	 * @param clazz
	 * @return
	 */
	public CFRender getRender(Class<? extends CFRender> clazz) {
		return this.renders.get(clazz);
	}

	/**
	 * 添加一个处理器
	 * @param clazz
	 * @param render
	 */
	public void addRender(Class<? extends CFRender> clazz, CFRender render) {
		this.renders.put(clazz, render);
	}

	/**
	 * 得到渲染页面的引擎
	 * @param viewName
	 * @return IView
	 */
	public CFViewPorxy getView(String viewName) {
		return this.viewsPorxy.get(viewName);
	}

	/**
	 * 得到渲染页面的引擎
	 * @param viewName
	 */
	public void addView(String viewName, CFViewPorxy viewPorxy) {
		this.viewsPorxy.put(viewName, viewPorxy);
	}

	/**
	 * 获取默认的视渲染器名称
	 * @return
	 */
	public String getDefaultView() {
		return defaultView;
	}

	/**
	 * 设置默认的视渲染器名称
	 * @param defaultView
	 */
	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}

	/**
	 * 设置文件上传配置中， 上传时的 buffer 区大小
	 * @param sizeThreshold
	 */
	public void setFileUploadSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}

	/**
	 * 获取文件上传配置中， 上传时 buffer 区大小配置
	 * @return
	 */
	public int getFileUploadSizeThreshold() {
		return this.sizeThreshold;
	}

	/**
	 * 设置文件上传配置中，上传临时目录
	 * @param repository
	 */
	public void setFileUploadRepository(String repository) {
		this.repository = repository;
	}

	/**
	 * 获取文件上传配置中， 上传临时目录
	 * @return
	 */
	public String getFileUploadRepository() {
		return this.repository;
	}

	/**
	 * 设置文件上传配置中，一次请求能上传的最大文件限制
	 * @param sizemax
	 */
	public void setFileUploadSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}

	/**
	 * 获取文件上传配置中，一次请求能上的最大文件限制
	 * @return
	 */
	public long getFileUploadSizeMax() {
		return this.sizeMax;
	}

	/**
	 * 获取登录页面地址
	 * @return
	 */
	public String getLoginUrl() {
		return this.loginUrl;
	}

	/**
	 * 设置登录页面地址
	 * @param loginUrl
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
