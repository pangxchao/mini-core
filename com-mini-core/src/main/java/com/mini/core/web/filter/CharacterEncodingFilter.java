package com.mini.core.web.filter;

import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.*;
import java.io.IOException;

@Singleton
public final class CharacterEncodingFilter implements Filter {
	
	@Inject
	private Configures configures;
	
	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		response.setCharacterEncoding(configures.getEncoding());
		request.setCharacterEncoding(configures.getEncoding());
		chain.doFilter(request, response);
	}
}
