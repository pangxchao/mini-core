/**
 * Created the sn.mini.web.filter.DefaultConfigFilter.java
 * @created 2017年11月6日 下午1:24:29
 * @version 1.0.0
 */
package sn.mini.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import sn.mini.util.lang.StringUtil;
import sn.mini.web.SNInitializer;

/**
 * sn.mini.web.filter.DefaultConfigFilter.java
 * @author XChao
 */
public final class DefaultConfigFilter extends SNAbstractFilter {
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public final void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
		throws java.io.IOException, javax.servlet.ServletException {
		HttpServletResponse response = (HttpServletResponse) res;

		if(StringUtil.isNotBlank(SNInitializer.getEncoding())) {
			request.setCharacterEncoding(SNInitializer.getEncoding());
			response.setCharacterEncoding(SNInitializer.getEncoding());
		}
		if(StringUtil.isNotBlank(SNInitializer.getAccessControlAllowOrigin())) {
			// 为允许哪些Origin发起跨域请求. 这里设置为”*”表示允许所有，通常设置为所有并不安全，最好指定一下。
			response.setHeader("Access-Control-Allow-Origin", SNInitializer.getAccessControlAllowOrigin());
			// Access-Control-Allow-Methods 为允许请求的方法.
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, TRACE, HEAD");
			// Access-Control-Max-Age 表明在多少秒内，不需要再发送预检验请求，可以缓存该结果
			response.setHeader("Access-Control-Max-Age", "3600");
			// Access-Control-Allow-Headers 表明它允许跨域请求包含content-type头，这里设置的x-requested-with ，表示ajax请求
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
