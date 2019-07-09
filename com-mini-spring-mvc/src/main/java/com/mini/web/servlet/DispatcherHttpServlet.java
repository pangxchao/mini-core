
package com.mini.web.servlet;

import com.mini.web.interceptor.ActionInvocationProxy;

import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


/**
 * 默认的Servlet
 * @author xchao
 */
@Component
public final class DispatcherHttpServlet extends AbstractDispatcherHttpServlet implements Serializable {
    private static final long serialVersionUID = 7077857952428048051L;

    /**
     * 获取 ActionInvocationProxy 对象
     * @param uri     RequestURI
     * @param request HttpServletRequest
     * @return ActionInvocationProxy 对象
     */
    protected ActionInvocationProxy getInvocationProxy(String uri, HttpServletRequest request) {
        return getConfigurer().getInvocationProxy(uri, request::setAttribute);
    }
}
