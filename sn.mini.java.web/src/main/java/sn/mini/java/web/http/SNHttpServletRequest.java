/**
 * Created the sn.mini.java.web.http.SNHttpServletRequest.java
 *
 * @created 2017年10月25日 下午4:57:41
 * @version 1.0.0
 */
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
 * boundary=----WebKitFormBoundary0YXRJFPyA1aqN0t1,isMultipartContent=true (需要注意的是: cFinal框架在使用的过程中,如果配置文件上传的临时目录找不到时,
 * 这种方式无法获取数据)</li>
 * <li>8: post 方式提交 , 设置enctype属性 enctype="text/plain" 无法获取参数 false</li>
 * </ul>
 *
 * @author XChao
 */
public class SNHttpServletRequest implements HttpServletRequest {
    private HttpServletRequest httpServletRequest;

    public SNHttpServletRequest(HttpServletRequest request) {
        this.httpServletRequest = request; // 初始化 httpServletRequest 值
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
    public Part getPart(String arg0) throws IOException, ServletException {
        try {
            return this.httpServletRequest.getPart(arg0);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        List<Part> partList = new ArrayList<>();
        try {
            Collection<Part> collections = this.httpServletRequest.getParts();
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
            Collection<Part> collections = this.httpServletRequest.getParts();
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
        try (BufferedReader reader = this.getReader()) {
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        }
        return String.join("\r\n", result);
    }
}
