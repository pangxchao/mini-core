package com.mini.core.web.argument.defaults;

import com.mini.core.inject.annotation.Associated;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.argument.ArgumentResolver;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static java.util.Optional.ofNullable;

@Named
@Singleton
public final class ArgumentResolverPartDefaultArray implements ArgumentResolver {
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		for (var annotation : parameter.getAnnotations()) {
			if (annotation.getClass().getAnnotation( //
					Associated.class) != null) {
				return false;
			}
		}
		return Part[].class == parameter.getType();
	}

	/**
	 * 获取参数名称
	 * @param parameter 参数对象
	 * @return 参数名称
	 */
	@Nonnull
	private String getParameterName(MiniParameter parameter) {
		return parameter.getName();
	}

	@Override
	public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
		try {
			String name = this.getParameterName(parameter);
			return ofNullable(invocation.getRequest().getParts())
					.stream()
					.flatMap(Collection::stream)
					.filter(Objects::nonNull)
					.filter(p -> name.equals(p.getName()))
					.toArray(Part[]::new);
		} catch (IOException | ServletException ignored) {
		}
		return null;
	}

}
