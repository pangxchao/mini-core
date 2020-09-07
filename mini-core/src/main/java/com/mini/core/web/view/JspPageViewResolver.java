package com.mini.core.web.view;

import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Singleton
public class JspPageViewResolver implements PageViewResolver, Serializable {
	private static final long serialVersionUID = -1L;
	
	@Inject
	private Configures configures;
	
	@Override
	public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String view = configures.getViewPrefix() + viewPath + configures.getViewSuffix();
		data.forEach(request::setAttribute);
		forward(view, request, response);
	}
	
	private void forward(String viewPath, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(viewPath).forward(request, response);
	}
}
