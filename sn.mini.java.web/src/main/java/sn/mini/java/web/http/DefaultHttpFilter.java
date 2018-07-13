package sn.mini.java.web.http;

import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.web.SNInitializer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "DefaultHttpFilter", value = "/*", asyncSupported = true)
public class DefaultHttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        SNHttpServletRequest request = new SNHttpServletRequest((HttpServletRequest) req);
        HttpServletResponse response = (HttpServletResponse) res;
        if (StringUtil.isNotBlank(SNInitializer.getEncoding())) {
            request.setCharacterEncoding(SNInitializer.getEncoding());
            response.setCharacterEncoding(SNInitializer.getEncoding());
        }
        if (StringUtil.isNotBlank(SNInitializer.getAccessControlAllowOrigin())) {
            // 为允许哪些Origin发起跨域请求. 这里设置为”*”表示允许所有，
            // 通常设置为所有并不安全，最好指定一下。
            response.setHeader("Access-Control-Allow-Origin", //
                    SNInitializer.getAccessControlAllowOrigin());
            // Access-Control-Allow-Methods 为允许请求的方法.
            response.setHeader("Access-Control-Allow-Methods", //
                    "POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD");
            // Access-Control-Max-Age 表明在多少秒内，不需要再发送预检验请求，可以缓存该结果
            response.setHeader("Access-Control-Max-Age", "3600");
            // Access-Control-Allow-Headers 表明它允许跨域请求包含content-type头，
            // 这里设置的x-requested-with ，表示ajax请求
            response.setHeader("Access-Control-Allow-Headers", //
                    "x-requested-with, Content-Type");
        }
        ActionProxy proxy = SNInitializer.getActionProxy(request.getRequestURI());
        if (proxy == null) {
            for (String name : SNInitializer.getActionProxys().keySet()) {
                if (request.getRequestURI().matches(name)) {
                    proxy = SNInitializer.getActionProxy(name);
                    break;
                }
            }
        }
        if (proxy != null) {
            proxy.getController().doDeleteBefore(request, response);
            proxy.getController().doService(proxy, request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
