package com.mini.web.argument;

import static com.mini.util.TypeUtil.castToShortVal;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.web.interceptor.ActionInvocation;

@Named
@Singleton
public final class ArgumentResolverArrayShortVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        short[] result = new short[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToShortVal(values[i]);
        }
        return result;
    }
}
