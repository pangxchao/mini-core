package com.mini.core.web.argument.defaults;

import com.mini.core.inject.annotation.Associated;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverArray;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ArgumentResolverArrayDefault extends ArgumentResolverArray {
	@Inject
	public ArgumentResolverArrayDefault(Configures configures) {
		super(configures);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		for (var annotation : parameter.getAnnotations()) {
			if (annotation.getClass().getAnnotation( //
				Associated.class) != null) {
				return false;
			}
		}
		return super.supportParameter(parameter);
	}
	
	@Nonnull
	@Override
	protected String getParameterName(MiniParameter parameter) {
		return parameter.getName();
	}
	
	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return invocation.getRequest().getParameterValues(name);
	}
}
