package com.mini.core.web.argument.param;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.argument.ArgumentResolverBean;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.mini.core.web.annotation.Param.Value.PARAM;
import static java.util.Objects.isNull;

@Singleton
public final class ArgumentResolverBeanRequestParam extends ArgumentResolverBean {
	@Inject
	public ArgumentResolverBeanRequestParam(Configures configures) {
		super(configures);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		Param param = parameter.getAnnotation(Param.class);
		if (isNull(param) || param.value() != PARAM) {
			return false;
		}
		return super.supportParameter(parameter);
	}
	
	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return invocation.getRequest().getParameterValues(name);
	}
}
