package com.mini.web.argument;

import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;

import static java.util.Optional.ofNullable;

public abstract class ArgumentResolverBase implements ArgumentResolver {

    @Override
    public final Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return parse(ofNullable(invocation.getRequest().getParameter(name)).orElseGet(() -> //
                ofNullable(invocation.getRequest().getAttribute(name)).map(Object::toString) //
                        .orElse(null)), type, invocation);
    }

    protected abstract Object parse(String text, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation);
}
