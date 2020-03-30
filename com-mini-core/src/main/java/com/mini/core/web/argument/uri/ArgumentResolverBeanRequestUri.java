package com.mini.core.web.argument.uri;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.argument.ArgumentResolverBean;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

import static com.mini.core.web.annotation.Param.Value.URI;
import static java.util.Objects.isNull;

@Singleton
public final class ArgumentResolverBeanRequestUri extends ArgumentResolverBean {

	@Inject
	public ArgumentResolverBeanRequestUri(Configures configures) {
		super(configures);
	}

	@Override
	public boolean supportParameter(MiniParameter parameter) {
		Param param = parameter.getAnnotation(Param.class);
		if (isNull(param) || param.value() != URI) {
			return false;
		}
		return  super.supportParameter(parameter);
	}

	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return Optional.ofNullable(invocation.getUriParameters().get(name))
				.map(v -> new String[]{v}).orElse(null);
	}
}
