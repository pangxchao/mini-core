package com.mini.core.web.argument;

import com.mini.core.util.Assert;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.support.config.Configures;

import javax.annotation.Nonnull;
import java.util.EventListener;
import java.util.function.Function;

import static com.mini.core.web.argument.ArgumentResolverSupport.getBasicFunc;

public abstract class ArgumentResolverBasic implements ArgumentResolver, EventListener {

	protected ArgumentResolverBasic(Configures configures) {
		ArgumentResolverSupport.init(configures);
	}

	@Override
	public boolean supportParameter(MiniParameter parameter) {
		return getBasicFunc(parameter.getType()) != null;
	}

	@Override
	public final Object getValue(MiniParameter parameter, ActionInvocation invocation) {
		Function<String, ?> function = getBasicFunc(parameter.getType());
		Assert.notNull(function, "Unsupported parameter type.");

		// 获取参数名称和参值，并处理
		String value = getValue(getParameterName(parameter), invocation);
		return function.apply(value);
	}

	/**
	 * 获取参数名称
	 * @param parameter 参数对象
	 * @return 参数名称
	 */
	@Nonnull
	protected abstract String getParameterName(MiniParameter parameter);

	/**
	 * 根据参数名称获取参数值
	 * @param name       参数名称
	 * @param invocation Action 调用对象
	 * @return 参数值
	 */
	protected abstract String getValue(String name, ActionInvocation invocation);
}
