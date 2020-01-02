package com.mini.core.web.argument.request.param;

import com.mini.core.util.StringUtil;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolverArray;
import com.mini.core.web.argument.annotation.RequestParam;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Singleton
public final class ArgumentResolverArrayRequestParam extends ArgumentResolverArray {

	@Override
	public boolean supportParameter(MiniParameter parameter) {
		RequestParam param = parameter.getAnnotation(RequestParam.class);
		return param != null && super.supportParameter(parameter);
	}

	@Nonnull
	@Override
	protected String getParameterName(MiniParameter parameter) {
		RequestParam param = parameter.getAnnotation(RequestParam.class);
		if (param == null || StringUtil.isBlank(param.value())) {
			return parameter.getName();
		}
		return param.value();
	}

	@Override
	protected String[] getValue(String name, ActionInvocation invocation) {
		return invocation.getRequest().getParameterValues(name);
	}
}
