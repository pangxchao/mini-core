/**
 * Created the sn.mini.java.web.filter.SNAbstractFilter.java
 * @created 2017年11月6日 下午12:00:05
 * @version 1.0.0
 */
package sn.mini.java.web.filter;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * sn.mini.java.web.filter.SNAbstractFilter.java
 * @author XChao
 */
public abstract class SNAbstractFilter implements Filter {

	public abstract void init(FilterConfig config) throws ServletException;

	public EnumSet<DispatcherType> getDispatcherType() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR);
	}

	public String[] getUrlPatterns() {
		return new String[] { "/*", "/" };
	}

	public abstract void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, javax.servlet.ServletException;

	public abstract void destroy();
}
