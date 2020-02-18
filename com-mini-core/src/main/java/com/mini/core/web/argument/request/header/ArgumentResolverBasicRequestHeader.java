package com.mini.core.web.argument.request.header;

import com.mini.core.util.StringUtil;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverBasic;
import com.mini.core.web.argument.annotation.RequestHeader;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ArgumentResolverBasicRequestHeader extends ArgumentResolverBasic {
	@Inject
	public ArgumentResolverBasicRequestHeader(Configures configures) {
		super(configures);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		RequestHeader param = parameter.getAnnotation(RequestHeader.class);
		return param != null && super.supportParameter(parameter);
	}
	
	@Nonnull
	@Override
	protected String getParameterName(MiniParameter parameter) {
		RequestHeader param = parameter.getAnnotation(RequestHeader.class);
		if (param == null || StringUtil.isBlank(param.value())) {
			return parameter.getName();
		}
		return param.value();
	}
	
	@Override
	protected String getValue(String name, ActionInvocation invocation) {
		return invocation.getRequest().getHeader(name);
	}
}
