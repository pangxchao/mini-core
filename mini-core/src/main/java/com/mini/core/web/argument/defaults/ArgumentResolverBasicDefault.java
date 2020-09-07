package com.mini.core.web.argument.defaults;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.argument.ArgumentResolverBasic;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@Singleton
public final class ArgumentResolverBasicDefault extends ArgumentResolverBasic {
	
	@Inject
	public ArgumentResolverBasicDefault(Configures configures) {
		super(configures);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		if (nonNull(parameter.getAnnotation(Param.class))) {
			return false;
		}
		return super.supportParameter(parameter);
	}
	
	@Nonnull
	@Override
	protected String getParameterName(MiniParameter parameter) {
		return parameter.getName();
	}
	
	@Override
	protected String getValue(String name, ActionInvocation invocation) {
		return defaultIfBlank(invocation.getRequest().getParameter(name), //
				invocation.getUriParameters().get(name));
	}
}
