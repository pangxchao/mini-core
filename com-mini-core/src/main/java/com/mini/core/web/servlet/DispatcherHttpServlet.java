package com.mini.core.web.servlet;

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
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String getActionProxyUri(HttpServletRequest request) {
		return request.getServletPath();
	}
}
