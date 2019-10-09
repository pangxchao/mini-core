package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.web.interceptor.ActionInvocation;

@Named
@Singleton
public final class ArgumentResolverArrayString extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return values;
    }
}
