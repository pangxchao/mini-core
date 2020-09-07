package com.mini.core.mvc.argument;

import com.mini.core.mvc.interceptor.ActionInvocation;
import com.mini.core.mvc.support.config.Configures;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;

import java.util.Objects;
import java.util.function.Function;

public abstract class ArgumentResolverBasic extends ArgumentResolverAbstract {

    protected ArgumentResolverBasic(@NotNull Configures configures) {
        ArgumentResolverSupport.init(configures);
    }

    @Override
    public boolean supportParameter(MethodParameter parameter) {
        return ArgumentResolverSupport.getBasicFunc(parameter.getParameterType()) != null;
    }

    @Override
    public final Object getValue(MethodParameter parameter, ActionInvocation invocation) {
        Function<String, ?> function = ArgumentResolverSupport.getBasicFunc(parameter.getParameterType());
        Objects.requireNonNull(function, "Unsupported parameter type.");
        // 获取参数名称和参值，并处理
        String value = getValue(getParameterName(parameter), invocation);
        return function.apply(value);
    }

    /**
     * 根据参数名称获取参数值
     *
     * @param name       参数名称
     * @param invocation Action 调用对象
     * @return 参数值
     */
    protected abstract String getValue(String name, ActionInvocation invocation);

    @Override
    public final int compareTo(@NotNull ArgumentResolver o) {
        return this.hashCode() - o.hashCode();
    }
}
