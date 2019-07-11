package com.mini.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域请求过虑器
 * @author xchao
 */
public class AccessControlAllowOriginFilter implements Filter {

    protected String getAccessControlAllowOrigin() {
        return "*";
    }

    public final void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // 为允许哪些Origin发起跨域请求. 这里设置为”*”表示允许所有，通常设置为所有并不安全，最好指定一下。
        response.setHeader("Access-Control-Allow-Origin", getAccessControlAllowOrigin());
        // Access-Control-Allow-Methods 为允许请求的方法.
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD");
        // Access-Control-Max-Age 表明在多少秒内，不需要再发送预检验请求，可以缓存该结果
        response.setHeader("Access-Control-Max-Age", "3600");
        // Access-Control-Allow-Headers 表明它允许跨域请求包含content-type头，这里设置的x-requested-with ，表示ajax请求
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
        chain.doFilter(request, response);
    }
}
