package com.mini.core.web.argument.header;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
import com.mini.core.web.argument.ArgumentResolver;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.mini.core.web.annotation.Param.Value.HEADER;
import static java.util.Objects.isNull;

@Named
@Singleton
public final class ArgumentResolverMapRequestHeaderArray implements ArgumentResolver {
	
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		Param param = parameter.getAnnotation(Param.class);
		if (isNull(param) || param.value() != HEADER) {
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
		HashMap<String, String[]> result = new HashMap<>();
		invocation.getRequest().getHeaderNames().asIterator().forEachRemaining(name -> {
			result.put(name, Stream.of(invocation.getRequest().getHeaders(name)).flatMap(v -> {
				Stream.Builder<String> builder = Stream.builder();
				v.asIterator().forEachRemaining(builder::add);
				return builder.build();
			}).toArray(String[]::new)); //
		});
		return result;
	}
}
