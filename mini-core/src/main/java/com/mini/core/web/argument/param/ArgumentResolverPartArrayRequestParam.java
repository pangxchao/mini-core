package com.mini.core.web.argument.param;

import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.annotation.Param;
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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Named
@Singleton
public final class ArgumentResolverPartArrayRequestParam implements ArgumentResolver {
	@Override
	public boolean supportParameter(MiniParameter parameter) {
		Param param = parameter.getAnnotation(Param.class);
		if (isNull(param) || param.value() != Param.Value.PARAM) {
			return false;
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
		Param param = parameter.getAnnotation(Param.class);
		if (nonNull(param) && isNotBlank(param.name())) {
			return param.name();
		}
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
					.filter(p -> p.getSize() > 0)
					.toArray(Part[]::new);
		} catch (IOException | ServletException ignored) {
		}
		return null;
	}
	
}
