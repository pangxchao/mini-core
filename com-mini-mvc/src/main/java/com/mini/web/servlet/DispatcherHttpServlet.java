
package com.mini.web.servlet;

import com.mini.web.interceptor.ActionInvocationProxy;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


/**
 * 默认的Servlet
 * @author xchao
 */
@Singleton
public final class DispatcherHttpServlet extends AbstractDispatcherHttpServlet implements Serializable {
    private static final long serialVersionUID = -522779044228588138L;

    @Override
    protected ActionInvocationProxy getInvocationProxy(String uri, HttpServletRequest request) {
        return getActionInvocationProxyConfigure().getInvocationProxy(uri, request::setAttribute);
    }
}
