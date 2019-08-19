package com.mini.web.test.context;

import com.mini.web.annotation.Action;
import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.servlet.AbstractDispatcherHttpServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义Servlet
 * @author xchao
 */
public final class MiniWebMvcServlet extends AbstractDispatcherHttpServlet {
    private static final long serialVersionUID = -5104342688489225990L;

    @Override
    protected ActionInvocationProxy getInvocationProxy(String uri, Action.Method method, HttpServletRequest request) {
        return getConfigure().getInvocationProxy("/front/user/group.htm", method, (name, value) -> {
            request.setAttribute("fileId", uri); //
        });
    }
}
