package com.mini.core.web.argument.param;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.argument.ArgumentResolver;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static com.mini.core.web.annotation.Param.Value.PARAM;
import static java.util.Objects.isNull;

@Named
@Singleton
public final class ArgumentResolverMapRequestParamArray implements ArgumentResolver {

	@Override
	public boolean supportParameter(MiniParameter parameter) {
		Param param = parameter.getAnnotation(Param.class);
		if (isNull(param) || param.value() != PARAM) {
			return false;
		}
		if (Map.class != parameter.getType()) {
			return false;
		}
		Type type = parameter.getParameterizedType();
		if (type instanceof ParameterizedType) {
			var t = (ParameterizedType) type;
			var arr = t.getActualTypeArguments();
			if (arr == null || arr.length != 2) {
				return false;
			}
			if (!arr[0].getTypeName().equals(String.class.getName())) {
				return false;
			}
			return arr[1].getTypeName().equals(String[].class.getName());
		}
		return false;
	}

	@Override
	public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
		return invocation.getRequest().getParameterMap();
	}
}
