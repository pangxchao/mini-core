package com.mini.web.test.context;

import com.mini.core.web.servlet.AbstractDispatcherHttpServlet;
import com.mini.core.web.servlet.AbstractDispatcherHttpServlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义Servlet
 * @author xchao
 */
public final class MiniWebMvcServlet extends AbstractDispatcherHttpServlet {
	private static final long serialVersionUID = -5104342688489225990L;

	@Override
	protected String getActionProxyUri(HttpServletRequest request) {
		return "/front/{userId}/group.htm";
	}
}
