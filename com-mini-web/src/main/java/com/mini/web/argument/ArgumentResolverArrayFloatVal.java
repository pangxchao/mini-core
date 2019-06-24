package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToFloatVal;

@Singleton
public final class ArgumentResolverArrayFloatVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        float[] result = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToFloatVal(values[i]);
        }
        return result;
    }
}
