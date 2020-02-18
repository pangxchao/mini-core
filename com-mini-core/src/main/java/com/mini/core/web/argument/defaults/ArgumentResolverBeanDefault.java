package com.mini.core.web.argument.defaults;

import com.mini.core.inject.annotation.Associated;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverBean;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ArgumentResolverBeanDefault extends ArgumentResolverBean {
	
	@Inject
	public ArgumentResolverBeanDefault(Configures configures) {
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
	
	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return invocation.getRequest().getParameterValues(name);
	}
}
