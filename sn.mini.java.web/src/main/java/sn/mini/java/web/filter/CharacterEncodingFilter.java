/**
 * Created the sn.mini.java.web.filter.CharacterEncodingFilter.java
 * @created 2017年11月6日 下午1:07:45
 * @version 1.0.0
 */
package sn.mini.java.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * sn.mini.java.web.filter.CharacterEncodingFilter.java
 * @author XChao
 */
public abstract class CharacterEncodingFilter extends SNAbstractFilter {

	public void init(FilterConfig config) throws ServletException {
	}

	protected abstract String getCharacterEncoding();

	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws java.io.IOException, javax.servlet.ServletException {
		request.setCharacterEncoding(getCharacterEncoding());
		response.setCharacterEncoding(getCharacterEncoding());
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
