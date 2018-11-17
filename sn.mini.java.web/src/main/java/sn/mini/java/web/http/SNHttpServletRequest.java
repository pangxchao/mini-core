package sn.mini.java.web.http;

import sn.mini.java.util.lang.StringUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

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
 * boundary=----WebKitFormBoundary0,isMultipartContent=true (需要注意的是: cFinal框架在使用的过程中,如果配置文件上传的临时目录找不到时,
 * 这种方式无法获取数据)</li>
 * <li>8: post 方式提交 , 设置enctype属性 enctype="text/plain" 无法获取参数 false</li>
 * </ul>
 *
 * @author XChao
 */
public class SNHttpServletRequest implements HttpServletRequest {
    private HttpServletRequest httpServletRequest;

    public SNHttpServletRequest(HttpServletRequest request) {
        httpServletRequest = request; // 初始化 httpServletRequest 值
    }

    protected final HttpServletRequest getRequest() {
        return httpServletRequest;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return httpServletRequest.getAsyncContext();
    }

    @Override
    public Object getAttribute(String arg0) {
        return httpServletRequest.getAttribute(arg0);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return httpServletRequest.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return httpServletRequest.getCharacterEncoding();
    }

    @Override
    public int getContentLength() {
        return httpServletRequest.getContentLength();
    }

    @Override
    public String getContentType() {
        return httpServletRequest.getContentType();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return httpServletRequest.getDispatcherType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    @Override
    public String getLocalAddr() {
        return httpServletRequest.getLocalAddr();
    }

    @Override
    public String getLocalName() {
        return httpServletRequest.getLocalName();
    }

    @Override
    public int getLocalPort() {
        return httpServletRequest.getLocalPort();
    }

    @Override
    public Locale getLocale() {
        return httpServletRequest.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return httpServletRequest.getLocales();
    }

    @Override
    public String getParameter(String arg0) {
        return httpServletRequest.getParameter(arg0);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return httpServletRequest.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return httpServletRequest.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String arg0) {
        return httpServletRequest.getParameterValues(arg0);
    }

    @Override
    public String getProtocol() {
        return httpServletRequest.getProtocol();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return httpServletRequest.getReader();
    }

    @Override
    @Deprecated
    public String getRealPath(String arg0) {
        return httpServletRequest.getRealPath(arg0);
    }

    @Override
    public String getRemoteAddr() {
        return httpServletRequest.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return httpServletRequest.getRemoteHost();
    }

    @Override
    public int getRemotePort() {
        return httpServletRequest.getRemotePort();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String arg0) {
        return httpServletRequest.getRequestDispatcher(arg0);
    }

    @Override
    public String getScheme() {
        return httpServletRequest.getScheme();
    }

    @Override
    public String getServerName() {
        return httpServletRequest.getServerName();
    }

    @Override
    public int getServerPort() {
        return httpServletRequest.getServerPort();
    }

    @Override
    public ServletContext getServletContext() {
        return httpServletRequest.getServletContext();
    }

    @Override
    public boolean isAsyncStarted() {
        return httpServletRequest.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return httpServletRequest.isAsyncSupported();
    }

    @Override
    public boolean isSecure() {
        return httpServletRequest.isSecure();
    }

    @Override
    public void removeAttribute(String arg0) {
        httpServletRequest.removeAttribute(arg0);
    }

    @Override
    public void setAttribute(String arg0, Object arg1) {
        httpServletRequest.setAttribute(arg0, arg1);
    }

    @Override
    public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
        httpServletRequest.setCharacterEncoding(arg0);
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return httpServletRequest.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
        return httpServletRequest.startAsync(arg0, arg1);
    }

    @Override
    public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException {
        return httpServletRequest.authenticate(arg0);
    }

    @Override
    public String getAuthType() {
        return httpServletRequest.getAuthType();
    }

    @Override
    public String getContextPath() {
        return httpServletRequest.getContentType();
    }

    @Override
    public Cookie[] getCookies() {
        return httpServletRequest.getCookies();
    }

    @Override
    public long getDateHeader(String arg0) {
        return httpServletRequest.getDateHeader(arg0);
    }

    @Override
    public String getHeader(String arg0) {
        return httpServletRequest.getHeader(arg0);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return httpServletRequest.getHeaderNames();
    }

    @Override
    public Enumeration<String> getHeaders(String arg0) {
        return httpServletRequest.getHeaders(arg0);
    }

    @Override
    public int getIntHeader(String arg0) {
        return httpServletRequest.getIntHeader(arg0);
    }

    @Override
    public String getMethod() {
        return httpServletRequest.getMethod();
    }

    @Override
    public String getPathInfo() {
        return httpServletRequest.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        return httpServletRequest.getPathTranslated();
    }

    @Override
    public String getQueryString() {
        return httpServletRequest.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return httpServletRequest.getRemoteUser();
    }

    @Override
    public String getRequestURI() {
        return httpServletRequest.getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        return httpServletRequest.getRequestURL();
    }

    @Override
    public String getRequestedSessionId() {
        return httpServletRequest.getRequestedSessionId();
    }

    @Override
    public String getServletPath() {
        return httpServletRequest.getServletPath();
    }

    @Override
    public HttpSession getSession() {
        return httpServletRequest.getSession();
    }

    @Override
    public HttpSession getSession(boolean arg0) {
        return httpServletRequest.getSession(arg0);
    }

    @Override
    public Principal getUserPrincipal() {
        return httpServletRequest.getUserPrincipal();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return httpServletRequest.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return httpServletRequest.isRequestedSessionIdFromURL();
    }

    @Override
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return httpServletRequest.isRequestedSessionIdFromUrl();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return httpServletRequest.isRequestedSessionIdValid();
    }

    @Override
    public boolean isUserInRole(String arg0) {
        return httpServletRequest.isUserInRole(arg0);
    }

    @Override
    public void login(String arg0, String arg1) throws ServletException {
        httpServletRequest.login(arg0, arg1);
    }

    @Override
    public void logout() throws ServletException {
        httpServletRequest.logout();
    }

    @Override
    public long getContentLengthLong() {
        return httpServletRequest.getContentLengthLong();
    }

    @Override
    public String changeSessionId() {
        return httpServletRequest.changeSessionId();
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException {
        return httpServletRequest.upgrade(arg0);
    }

    @Override
    public Part getPart(String arg0) throws IOException, ServletException {
        try {
            Part part = httpServletRequest.getPart(arg0);
            return part != null && part.getSize() > 0 ? part : null;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        List<Part> partList = new ArrayList<>();
        try {
            Collection<Part> collections = httpServletRequest.getParts();
            if (collections != null) {
                for (Part part : collections) {
                    if (part != null && part.getSize() > 0) {
                        partList.add(part);
                    }
                }
            }
        } catch (Exception e) {
        }
        return partList;
    }

    public Part[] getParts(String arg0) throws IOException, ServletException {
        List<Part> partList = new ArrayList<>();
        try {
            Collection<Part> collections = httpServletRequest.getParts();
            if (collections != null) {
                for (Part part : collections) {
                    if (part != null && part.getSize() > 0 && StringUtil.isNotBlank(arg0) //
                            && arg0.equals(part.getName())) {
                        partList.add(part);
                    }
                }
            }
        } catch (Exception e) {
        }
        return partList.toArray(new Part[0]);

    }

    public String getBodyString() throws IOException {
        String line;
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = getReader()) {
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        }
        return String.join("\r\n", result);
    }
}
