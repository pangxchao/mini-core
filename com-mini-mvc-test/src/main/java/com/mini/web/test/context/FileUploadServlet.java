package com.mini.web.test.context;

import com.mini.web.interceptor.ActionInvocationProxy;
import com.mini.web.servlet.AbstractDispatcherHttpServlet;

import javax.servlet.http.HttpServletRequest;

public final class FileUploadServlet extends AbstractDispatcherHttpServlet {
    private static final long serialVersionUID = -5104342688489225990L;

    @Override
    protected ActionInvocationProxy getInvocationProxy(String uri, HttpServletRequest request) {
        return getActionInvocationProxyConfigure().getInvocationProxy("/front/user/group.htm", (name, value) -> {
            request.setAttribute("fileId", uri); //
        });
    }
}
