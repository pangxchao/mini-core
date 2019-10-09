package com.mini.web.filter;

import java.io.IOException;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

import com.mini.util.TypeUtil;

/**
 * 缓存拦截器
 * @author xchao
 */
@Singleton
public final class CacheControlFilter implements Filter {

    private String cacheControl = "No-Cache";
    private String cachePragma = "No-Cache";
    private String cacheExpires = "0";

    @Inject
    public void setCacheControl(@Named("Cache-Control") @Nullable String cacheControl) {
        this.cacheControl = cacheControl;
    }

    @Inject
    public void setCachePragma(@Named("Cache-Pragma") @Nullable String cachePragma) {
        this.cachePragma = cachePragma;
    }

    @Inject
    public void setCacheExpires(@Named("Cache-Expires") @Nullable String cacheExpires) {
        this.cacheExpires = cacheExpires;
    }

    private long getCacheExpires() {
        long c = TypeUtil.castToLongVal(cacheExpires);
        return System.currentTimeMillis() + c;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        // 缓存到期日期的时间戳
        resp.setDateHeader("Expires", getCacheExpires());
        // 浏览器和缓存服务器都不应该缓存页面信息
        resp.setHeader("Cache-Control", cacheControl);
        // 不允许浏览器端或缓存服务器缓存当前页面信息。
        resp.setHeader("Pragma", cachePragma);
        // 调用下一个拦截器
        chain.doFilter(request, response);
    }

}
