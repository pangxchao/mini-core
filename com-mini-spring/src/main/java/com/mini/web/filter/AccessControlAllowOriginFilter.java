package com.mini.web.filter;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域请求过虑器
 * @author xchao
 */
@Singleton
public final class AccessControlAllowOriginFilter implements Filter {

    private String accessControlAllowMethods = "POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD";
    // 这里设置的x-requested-with ，表示ajax请求
    private String accessControlAllowHeaders = "x-requested-with, Content-Type";
    // 这里设置为”*”表示允许所有，通常设置为所有并不安全，最好指定一下。
    private String accessControlAllowOrigin = "*";
    // 表明在多少秒内，不需要再发送预检验请求，可以缓存该结果
    private String accessControlMaxAge = "3600";


    @Inject
    public void setAccessControlAllowOrigin(@Named("Access-Control-Allow-Origin") @Nullable String accessControlAllowOrigin) {
        this.accessControlAllowOrigin = accessControlAllowOrigin;
    }

    @Inject
    public void setAccessControlAllowMethods(@Named("Access-Control-Allow-Methods") @Nullable String accessControlAllowMethods) {
        this.accessControlAllowMethods = accessControlAllowMethods;
    }

    @Inject
    public void setAccessControlAllowHeaders(@Named("Access-Control-Allow-Headers") @Nullable String accessControlAllowHeaders) {
        this.accessControlAllowHeaders = accessControlAllowHeaders;
    }

    @Inject
    public void setAccessControlMaxAge(@Named("Access-Control-Max-Age") @Nullable String accessControlMaxAge) {
        this.accessControlMaxAge = accessControlMaxAge;
    }

    public final void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // Access-Control-Allow-Methods 为允许请求的方法.
        response.setHeader("Access-Control-Allow-Methods", accessControlAllowMethods);
        // Access-Control-Allow-Headers 表明它允许跨域请求包含content-type头，
        response.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);
        // 为允许哪些Origin发起跨域请求,”*”表示允许所有，通常设置为所有并不安全，最好指定一下
        response.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
        // Access-Control-Max-Age 表明在多少秒内，不需要再发送预检验请求，可以缓存该结果
        response.setHeader("Access-Control-Max-Age", accessControlMaxAge);
        // 调用下一个拦截器
        chain.doFilter(request, response);
    }
}
