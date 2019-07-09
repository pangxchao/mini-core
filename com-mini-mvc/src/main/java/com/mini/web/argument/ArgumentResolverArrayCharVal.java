package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToCharVal;

@Named
@Singleton
public final class ArgumentResolverArrayCharVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        char[] result = new char[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToCharVal(values[i]);
        }
        return result;
    }
}
