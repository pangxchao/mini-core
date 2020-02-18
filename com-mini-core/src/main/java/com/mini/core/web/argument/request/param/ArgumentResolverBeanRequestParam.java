package com.mini.core.web.argument.request.param;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverBean;
import com.mini.core.web.argument.annotation.RequestParam;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ArgumentResolverBeanRequestParam extends ArgumentResolverBean {
	@Inject
	public ArgumentResolverBeanRequestParam(Configures configures) {
		super(configures);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		RequestParam param = parameter.getAnnotation(RequestParam.class);
		return param != null && super.supportParameter(parameter);
	}
	
	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return invocation.getRequest().getParameterValues(name);
	}
}
