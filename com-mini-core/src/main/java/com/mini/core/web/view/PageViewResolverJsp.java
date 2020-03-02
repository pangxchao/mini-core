package com.mini.core.web.view;

import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

@Singleton
public class PageViewResolverJsp implements PageViewResolver, Serializable {
	private static final long serialVersionUID = -1L;

	@Inject
	private Configures configures;

	@Override
	public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		String view = configures.getViewPrefix() + viewPath //
				+ configures.getViewSuffix();
		data.forEach(request::setAttribute);
		forward(view, request, response);
	}

	private void forward(String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher(viewPath).forward(request, response);
	}
}
