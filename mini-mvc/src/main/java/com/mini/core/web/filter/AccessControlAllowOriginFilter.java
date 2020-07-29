package com.mini.core.web.filter;

import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

/**
 * 跨域请求过虑器
 * @author xchao
 */
@Singleton
public final class AccessControlAllowOriginFilter implements Filter {
	
	@Inject
	private Configures configures;
	
	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ofNullable(response).filter(res -> res instanceof HttpServletResponse).map(res -> (HttpServletResponse) res).ifPresent(res -> {
			// 是否允许设置自定义请求头
			res.setHeader("Access-Control-Allow-Credentials", valueOf(configures.isAccessControlAllowCredentials()));
			// Access-Control-Allow-Methods 为允许请求的方法.
			res.setHeader("Access-Control-Allow-Methods", configures.getAccessControlAllowMethods());
			// Access-Control-Allow-Headers 表明它允许跨域请求包含content-type头，
			res.setHeader("Access-Control-Allow-Headers", configures.getAccessControlAllowHeaders());
			// 为允许哪些Origin发起跨域请求,”*”表示允许所有，通常设置为所有并不安全，最好指定一下
			res.setHeader("Access-Control-Allow-Origin", configures.getAccessControlAllowOrigin());
			// Access-Control-Max-Age 表明在多少秒内，不需要再发送预检验请求，可以缓存该结果
			res.setHeader("Access-Control-Max-Age", valueOf(configures.getAccessControlMaxAge()));
		});
		// 调用下一个拦截器
		chain.doFilter(request, response);
	}
}
