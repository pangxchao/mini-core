package com.mini.web.argument;

import static com.mini.util.TypeUtil.castToCharVal;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.web.interceptor.ActionInvocation;

@Named
@Singleton
public final class ArgumentResolverArrayCharVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        char[] result = new char[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToCharVal(values[i]);
        }
        return result;
    }
}
