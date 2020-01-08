package com.mini.core.web.argument.request.uri;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverBean;
import com.mini.core.web.argument.annotation.RequestUri;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class ArgumentResolverBeanRequestUri extends ArgumentResolverBean {
	
	@Inject
	public ArgumentResolverBeanRequestUri(
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
	
	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return Optional.ofNullable(invocation.getUriParameters().get(name))
			.map(v -> new String[]{v}).orElse(null);
	}
}
