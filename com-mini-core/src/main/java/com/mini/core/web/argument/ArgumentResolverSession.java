package com.mini.core.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.util.WebSession;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public final class ArgumentResolverSession implements ArgumentResolver {
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		return WebSession.class.isAssignableFrom(parameter.getType());
	}
	
	@Override
	public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
		return invocation.getSession().getAttribute(WebSession.SESSION_KEY);
	}
}
