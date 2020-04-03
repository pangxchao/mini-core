package com.mini.core.web.interceptor;

/**
 * Web Action 拦截器
 * @author xchao
 */
public interface ActionInterceptor {
	
	Object invoke(ActionInvocation invocation) throws Throwable;
}
