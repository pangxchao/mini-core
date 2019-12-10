package com.mini.web.filter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 缓存拦截器
 * @author xchao
 */
@Singleton
public final class CacheControlFilter implements Filter {

    @Inject
    @Named("CacheControl")
    private String cacheControl;

    @Inject
    @Named("CachePragma")
    private String cachePragma;

    @Inject
    @Named("CacheExpires")
    private int cacheExpires;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        // 浏览器和缓存服务器都不应该缓存页面信息
        resp.setHeader("Cache-Control", cacheControl);
        // 缓存到期日期的时间戳
        resp.setDateHeader("Expires", cacheExpires);
        // 不允许浏览器端或缓存服务器缓存当前页面信息。
        resp.setHeader("Pragma", cachePragma);
        // 调用下一个拦截器
        chain.doFilter(request, response);
    }

}
