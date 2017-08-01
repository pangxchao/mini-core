/**
 * Created the com.xcc.web.request.XHttpServletRequest.java
 * @created 2016年11月18日 上午10:18:09
 * @version 1.0.0
 */
package com.cfinal.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * 重新包装HttpServletRequest对象的接口方法
 * <ul>
 * <li>1: get 方式提交, 不设置enctype属性, 可以获取参数, 但不能获取文件参数, 后台打印 Content-Type=null; isMultipartContent=false</li>
 * <li>2: get 方式提交, 设置enctype属性 enctype="application/x-www-form-urlencoded", 可以获取参数, 但不能获取文件参数 后台打印 Content-Type=null;
 * isMultipartContent=false</li>
 * <li>3: get 方式提交 , 设置enctype属性 enctype="multipart/form-data" 可以获取参数, 但不能获取文件参数, 后台打印 Content-Type=null;
 * isMultipartContent=false</li>
 * <li>4: get 方式提交 , 设置enctype属性 enctype="text/plain" 可以获取参数, 但不能获取文件参数,后台打印 Content-Type=null; isMultipartContent=false
 * </li>
 * <li>5: post 方式提交, 不设置enctype属性, 可以获取参数, 但不能获取文件参数,后台打倒 Content-Type 时为
 * application/x-www-form-urlencoded,isMultipartContent=false</li>
 * <li></li>
 * <li>6: post 方式提交, 设置enctype属性 enctype="application/x-www-form-urlencoded" 同方式 5</li>
 * <li>7: post 方式提交, 设置enctype属性 enctype="multipart/form-data" 可以获取参数,也可以获取文件参数后台打印 Content-Type=multipart/form-data;
 * boundary=----WebKitFormBoundary0YXRJFPyA1aqN0t1,isMultipartContent=true (需要注意的是: cFinal框架在使用的过程中,如果配置文件上传的临时目录找不到时,
 * 这种方式无法获取数据)</li>
 * <li>8: post 方式提交 , 设置enctype属性 enctype="text/plain" 无法获取参数 false</li>
 * </ul>
 * @author XChao
 */
public class CFRequest implements HttpServletRequest {
	private boolean isMultipartContent = false;
	protected HttpServletRequest httpServletRequest;

	public CFRequest(HttpServletRequest request) {
		this.isMultipartContent = ServletFileUpload.isMultipartContent(request);
		this.httpServletRequest = request;
	}

	protected final HttpServletRequest getRequest() {
		return this.httpServletRequest;
	}

	@Override
	public AsyncContext getAsyncContext() {
		return this.httpServletRequest.getAsyncContext();
	}

	@Override
	public Object getAttribute(String arg0) {
		return this.httpServletRequest.getAttribute(arg0);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return this.httpServletRequest.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return this.httpServletRequest.getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return this.httpServletRequest.getContentLength();
	}

	@Override
	public String getContentType() {
		return this.httpServletRequest.getContentType();
	}

	@Override
	public DispatcherType getDispatcherType() {
		return this.httpServletRequest.getDispatcherType();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return this.httpServletRequest.getInputStream();
	}

	@Override
	public String getLocalAddr() {
		return this.httpServletRequest.getLocalAddr();
	}

	@Override
	public String getLocalName() {
		return this.httpServletRequest.getLocalName();
	}

	@Override
	public int getLocalPort() {
		return this.httpServletRequest.getLocalPort();
	}

	@Override
	public Locale getLocale() {
		return this.httpServletRequest.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return this.httpServletRequest.getLocales();
	}

	@Override
	public String getParameter(String arg0) {
		return this.httpServletRequest.getParameter(arg0);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return this.httpServletRequest.getParameterMap();
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return this.httpServletRequest.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String arg0) {
		return this.httpServletRequest.getParameterValues(arg0);
	}

	@Override
	public String getProtocol() {
		return this.httpServletRequest.getProtocol();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return this.httpServletRequest.getReader();
	}

	@Override
	@Deprecated
	public String getRealPath(String arg0) {
		return this.httpServletRequest.getRealPath(arg0);
	}

	@Override
	public String getRemoteAddr() {
		return this.httpServletRequest.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return this.httpServletRequest.getRemoteHost();
	}

	@Override
	public int getRemotePort() {
		return this.httpServletRequest.getRemotePort();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		return this.httpServletRequest.getRequestDispatcher(arg0);
	}

	@Override
	public String getScheme() {
		return this.httpServletRequest.getScheme();
	}

	@Override
	public String getServerName() {
		return this.httpServletRequest.getServerName();
	}

	@Override
	public int getServerPort() {
		return this.httpServletRequest.getServerPort();
	}

	@Override
	public ServletContext getServletContext() {
		return this.httpServletRequest.getServletContext();
	}

	@Override
	public boolean isAsyncStarted() {
		return this.httpServletRequest.isAsyncStarted();
	}

	@Override
	public boolean isAsyncSupported() {
		return this.httpServletRequest.isAsyncSupported();
	}

	@Override
	public boolean isSecure() {
		return this.httpServletRequest.isSecure();
	}

	@Override
	public void removeAttribute(String arg0) {
		this.httpServletRequest.removeAttribute(arg0);
	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		this.httpServletRequest.setAttribute(arg0, arg1);
	}

	@Override
	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		this.httpServletRequest.setCharacterEncoding(arg0);
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return this.httpServletRequest.startAsync();
	}

	@Override
	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
		return this.httpServletRequest.startAsync(arg0, arg1);
	}

	@Override
	public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException {
		return this.httpServletRequest.authenticate(arg0);
	}

	@Override
	public String getAuthType() {
		return this.httpServletRequest.getAuthType();
	}

	@Override
	public String getContextPath() {
		return this.httpServletRequest.getContentType();
	}

	@Override
	public Cookie[] getCookies() {
		return this.httpServletRequest.getCookies();
	}

	@Override
	public long getDateHeader(String arg0) {
		return this.httpServletRequest.getDateHeader(arg0);
	}

	@Override
	public String getHeader(String arg0) {
		return this.httpServletRequest.getHeader(arg0);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return this.httpServletRequest.getHeaderNames();
	}

	@Override
	public Enumeration<String> getHeaders(String arg0) {
		return this.httpServletRequest.getHeaders(arg0);
	}

	@Override
	public int getIntHeader(String arg0) {
		return this.httpServletRequest.getIntHeader(arg0);
	}

	@Override
	public String getMethod() {
		return this.httpServletRequest.getMethod();
	}

	@Override
	public String getPathInfo() {
		return this.httpServletRequest.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return this.httpServletRequest.getPathTranslated();
	}

	@Override
	public String getQueryString() {
		return this.httpServletRequest.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return this.httpServletRequest.getRemoteUser();
	}

	@Override
	public String getRequestURI() {
		return this.httpServletRequest.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return this.httpServletRequest.getRequestURL();
	}

	@Override
	public String getRequestedSessionId() {
		return this.httpServletRequest.getRequestedSessionId();
	}

	@Override
	public String getServletPath() {
		return this.httpServletRequest.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return this.httpServletRequest.getSession();
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		return this.httpServletRequest.getSession(arg0);
	}

	@Override
	public Principal getUserPrincipal() {
		return this.httpServletRequest.getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return this.httpServletRequest.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return this.httpServletRequest.isRequestedSessionIdFromURL();
	}

	@Override
	@Deprecated
	public boolean isRequestedSessionIdFromUrl() {
		return this.httpServletRequest.isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return this.httpServletRequest.isRequestedSessionIdValid();
	}

	@Override
	public boolean isUserInRole(String arg0) {
		return this.httpServletRequest.isUserInRole(arg0);
	}

	@Override
	public void login(String arg0, String arg1) throws ServletException {
		this.httpServletRequest.login(arg0, arg1);
	}

	@Override
	public void logout() throws ServletException {
		this.httpServletRequest.logout();
	}

	@Override
	public long getContentLengthLong() {
		return this.httpServletRequest.getContentLengthLong();
	}

	@Override
	public String changeSessionId() {
		return this.httpServletRequest.changeSessionId();
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException {
		return this.httpServletRequest.upgrade(arg0);
	}

	@Override
	public CFPart getPart(String arg0) throws IOException, ServletException {
		if(this.isMultipartContent == false) {
			return null;
		}
		Part part = this.httpServletRequest.getPart(arg0);
		if(part != null && part.getSize() > 0) {
			return new CFPart(part);
		}
		return null;
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		if(this.isMultipartContent == false) {
			return new ArrayList<Part>();
		}
		Collection<Part> collection = this.httpServletRequest.getParts();
		if(collection == null) {
			return new ArrayList<Part>();
		}
		List<Part> partResult = new ArrayList<Part>();
		for (Part part : collection) {
			if(part != null && part.getSize() > 0) {
				partResult.add(new CFPart(part));
			}
		}
		return partResult;
	}

	public CFPart[] getParts(String arg0) throws IOException, ServletException {
		if(this.isMultipartContent == false) {
			return new CFPart[0];
		}
		Collection<Part> collection = this.httpServletRequest.getParts();
		if(collection == null) {
			return new CFPart[0];
		}
		List<CFPart> partResult = new ArrayList<CFPart>();
		for (Part part : collection) {
			if(part != null && part.getSize() > 0 && StringUtils.equals(arg0, part.getName())) {
				partResult.add(new CFPart(part));
			}
		}
		return partResult.toArray(new CFPart[partResult.size()]);
	}

	public String getBodyString() throws IOException {
		String line = null;
		BufferedReader reader = null;
		StringBuilder result = new StringBuilder();
		try {
			reader = this.getReader();
			while ((line = reader.readLine()) != null) {
				result.append(line).append("\r\n");
			}
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
		return result.toString();
	}
}
