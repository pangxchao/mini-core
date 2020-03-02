package com.mini.core.web.argument;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.model.IModel;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public final class ArgumentResolverModel implements ArgumentResolver {
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		return IModel.class.isAssignableFrom(parameter.getType());
	}

	@Override
	public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
		return invocation.getModel();
	}
}
