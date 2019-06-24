package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToChar;

@Singleton
public final class ArgumentResolverArrayChar extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        Character[] result = new Character[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToChar(values[i]);
        }
        return result;
    }
}
