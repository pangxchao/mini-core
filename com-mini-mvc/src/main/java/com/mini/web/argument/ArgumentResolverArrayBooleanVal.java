package com.mini.web.argument;

import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import static com.mini.util.TypeUtil.castToBoolVal;

@Named
@Singleton
public final class ArgumentResolverArrayBooleanVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        boolean[] result = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToBoolVal(values[i]);
        }
        return result;
    }
}
