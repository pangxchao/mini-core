package com.mini.web.servlet;

import javax.annotation.Nonnull;
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
    private static final long serialVersionUID = -522779044228588138L;

    @Override
    protected String getInvocationProxyUri(@Nonnull String requestPath, HttpServletRequest request) {
        return request.getServletPath();
    }
}
