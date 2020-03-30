package com.mini.core.web.argument.param;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.argument.ArgumentResolverArray;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.mini.core.web.annotation.Param.Value.PARAM;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Singleton
public final class ArgumentResolverArrayRequestParam extends ArgumentResolverArray {

	@Inject
	public ArgumentResolverArrayRequestParam(Configures configures) {
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
		return invocation.getRequest().getParameterValues(name);
	}
}
