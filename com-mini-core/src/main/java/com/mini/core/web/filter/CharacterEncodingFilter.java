package com.mini.core.web.filter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Singleton
public final class CharacterEncodingFilter implements Filter {

	@Inject
	@Named("CharsetEncoding")
	private String encoding;


	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, javax.servlet.ServletException {
		response.setCharacterEncoding(encoding);
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}
}
