package com.mini.web.argument;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.mini.web.interceptor.ActionInvocation;

public abstract class ArgumentResolverArray implements ArgumentResolver {
    @Override
    public final Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        String[] value = invocation.getRequest().getParameterValues(name);
        return Optional.ofNullable(value).map(values -> //
                parse(values, type, invocation)) //
                .orElse(null);
    }

    protected abstract Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation);
}
