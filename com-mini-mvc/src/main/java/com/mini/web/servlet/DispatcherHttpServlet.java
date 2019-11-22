package com.mini.web.servlet;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

/**
 * 默认的Servlet
 * @author xchao
 */
@Named
@Singleton
public final class DispatcherHttpServlet extends AbstractDispatcherHttpServlet {
    @Override
    protected String getActionProxyUri(HttpServletRequest request) {
        return request.getServletPath();
    }
}
