package com.mini.web.filter;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public final class TimeCacheFilter implements Filter {
    private final long cacheTime;

    public TimeCacheFilter(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(response instanceof HttpServletResponse)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletResponse resp = (HttpServletResponse) response;
        long now = System.currentTimeMillis();
        long expires = now + cacheTime;
        // Last-Modified: 页面的最后生成时间
        resp.setDateHeader("Last-Modified", now);
        // Expires: 过时期限值，单位毫秒
        resp.setDateHeader("Expires", expires);
        // Cache-Control来控制页面的缓存与否,
        // public:浏览器和缓存服务器都可以缓存页面信息；
        resp.setHeader("Cache-Control", "public");
        // Pragma:设置页面是否缓存，
        // 为Pragma则缓存，
        // no-cache则不缓存
        resp.setHeader("Pragma", "Pragma");
        // 调用下一个拦截器
        chain.doFilter(request, response);
        for (String name : resp.getHeaderNames()) {
            System.out.println(resp.getHeader(name));
        }

    }

}
