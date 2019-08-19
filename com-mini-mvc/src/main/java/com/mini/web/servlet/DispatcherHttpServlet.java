
package com.mini.web.servlet;

import com.mini.web.annotation.Action;
import com.mini.web.interceptor.ActionInvocationProxy;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


/**
 * 默认的Servlet
 * @author xchao
 */
@Named
@Singleton
public final class DispatcherHttpServlet extends AbstractDispatcherHttpServlet implements Serializable {
    private static final long serialVersionUID = -522779044228588138L;

    @Override
    protected ActionInvocationProxy getInvocationProxy(String uri, Action.Method method, HttpServletRequest request) {
        return getConfigure().getInvocationProxy(uri, method, request::setAttribute);
    }
}
