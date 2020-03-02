package com.mini.core.web.argument.request.header;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverBean;
import com.mini.core.web.argument.annotation.RequestHeader;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

@Singleton
public final class ArgumentResolverBeanRequestHeader extends ArgumentResolverBean {
	@Inject
	public ArgumentResolverBeanRequestHeader(Configures configures) {
		super(configures);
	}

	@Override
	public boolean supportParameter(MiniParameter parameter) {
		RequestHeader param = parameter.getAnnotation(RequestHeader.class);
		return param != null && super.supportParameter(parameter);
	}

	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return of(invocation.getRequest().getHeaders(name)).flatMap(v -> {
			Stream.Builder<String> builder = Stream.builder();
			v.asIterator().forEachRemaining(builder::add);
			return builder.build();
		}).toArray(String[]::new);
	}
}
