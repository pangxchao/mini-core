/**
 * Created the com.cfinal.web.core.CFCharacterEncodingFilter.java
 * @created 2017年8月21日 下午10:42:08
 * @version 1.0.0
 */
package com.cfinal.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * com.cfinal.web.core.CFCharacterEncodingFilter.java
 * @author XChao
 */
public class CFCharacterEncodingFilter extends CFFilter {

	public void init(FilterConfig config) throws ServletException {
	}

	protected EnumSet<DispatcherType> getEnumSet() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, //
			DispatcherType.INCLUDE, DispatcherType.ERROR);
	}

	protected List<String> getUrlMapping() {
		return Arrays.asList("/*");
	}

	protected List<String> getServletNames() {
		return null;
	}

	protected String getCharacterEncoding() {
		return this.getContext().getEncoding();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		request.setCharacterEncoding(getCharacterEncoding());
		response.setCharacterEncoding(getCharacterEncoding());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
