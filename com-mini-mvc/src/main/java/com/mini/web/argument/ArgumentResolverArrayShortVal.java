package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToShortVal;

@Named
@Singleton
public final class ArgumentResolverArrayShortVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        short[] result = new short[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToShortVal(values[i]);
        }
        return result;
    }
}
