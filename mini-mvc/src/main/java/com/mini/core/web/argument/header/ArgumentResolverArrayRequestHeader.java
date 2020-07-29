package com.mini.core.web.argument.header;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.argument.ArgumentResolverArray;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Stream;

import static com.mini.core.web.annotation.Param.Value.HEADER;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Singleton
public final class ArgumentResolverArrayRequestHeader extends ArgumentResolverArray {
	@Inject
	public ArgumentResolverArrayRequestHeader(Configures configures) {
		super(configures);
	}
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		Param param = parameter.getAnnotation(Param.class);
		if (isNull(param) || param.value() != HEADER) {
			return false;
		}
		return super.supportParameter(parameter);
	}
	
	@Nonnull
	@Override
	protected String getParameterName(MiniParameter parameter) {
		Param param = parameter.getAnnotation(Param.class);
		if (nonNull(param) && isNotBlank(param.name())) {
			return param.name();
		}
		return parameter.getName();
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
