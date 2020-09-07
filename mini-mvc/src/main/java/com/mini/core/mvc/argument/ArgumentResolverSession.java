package com.mini.core.mvc.argument;

import com.mini.core.mvc.interceptor.ActionInvocation;
import com.mini.core.mvc.util.WebSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public final class ArgumentResolverSession implements ArgumentResolver {
	
	@Override
	public boolean supportParameter(MethodParameter parameter) {
		return WebSession.class.isAssignableFrom(parameter.getParameterType());
	}
	
	@Override
	public Object getValue(MethodParameter parameter, ActionInvocation invocation) {
		return invocation.getSession().getAttribute(WebSession.SESSION_KEY);
	}
	
	@Override
	public final int compareTo(@Nonnull ArgumentResolver o) {
		return this.hashCode() - o.hashCode();
	}
}
