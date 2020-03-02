package com.mini.core.web.argument.request.uri;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverBean;
import com.mini.core.web.argument.annotation.RequestUri;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public final class ArgumentResolverBeanRequestUri extends ArgumentResolverBean {

	@Inject
	public ArgumentResolverBeanRequestUri(Configures configures) {
		super(configures);
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
