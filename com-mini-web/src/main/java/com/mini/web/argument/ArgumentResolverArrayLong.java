package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToLong;

@Singleton
public final class ArgumentResolverArrayLong extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        Long[] result = new Long[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToLong(values[i]);
        }
        return result;
    }
}
