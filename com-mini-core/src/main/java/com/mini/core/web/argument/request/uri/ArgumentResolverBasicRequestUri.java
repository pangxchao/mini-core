package com.mini.core.web.argument.request.uri;

import com.mini.core.util.StringUtil;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverBasic;
import com.mini.core.web.argument.annotation.RequestUri;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public final class ArgumentResolverBasicRequestUri extends ArgumentResolverBasic {
	@Inject
	public ArgumentResolverBasicRequestUri(
		@Named("DateTimeFormat") String dateTimeFormat,
		@Named("DateFormat") String dateFormat,
		@Named("TimeFormat") String timeFormat) {
		super(dateTimeFormat, dateFormat, timeFormat);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		RequestUri param = parameter.getAnnotation(RequestUri.class);
		return param != null && super.supportParameter(parameter);
	}

	@Nonnull
	@Override
	protected String getParameterName(MiniParameter parameter) {
		RequestUri param = parameter.getAnnotation(RequestUri.class);
		if (param == null || StringUtil.isBlank(param.value())) {
			return parameter.getName();
		}
		return param.value();
	}

	@Override
	protected String getValue(String name, ActionInvocation invocation) {
		return invocation.getRequest().getParameter(name);
	}
}
