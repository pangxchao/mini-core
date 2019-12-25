package com.mini.core.web.filter;

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

    @Inject
    @Named("AccessControlAllowMethods")
    private String accessControlAllowMethods;

    @Inject
    @Named("AccessControlAllowHeaders")
    private String accessControlAllowHeaders;

    @Inject
    @Named("AccessControlAllowOrigin")
    private String accessControlAllowOrigin;

    @Inject
    @Named("AccessControlAllowOrigin")
    private int accessControlMaxAge;

    public final void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        // Access-Control-Max-Age 表明在多少秒内，不需要再发送预检验请求，可以缓存该结果
        response.setHeader("Access-Control-Max-Age", String.valueOf(accessControlMaxAge));
        // Access-Control-Allow-Methods 为允许请求的方法.
        response.setHeader("Access-Control-Allow-Methods", accessControlAllowMethods);
        // Access-Control-Allow-Headers 表明它允许跨域请求包含content-type头，
        response.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);
        // 为允许哪些Origin发起跨域请求,”*”表示允许所有，通常设置为所有并不安全，最好指定一下
        response.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
        // 调用下一个拦截器
        chain.doFilter(request, response);
    }
}
