package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToDoubleVal;

@Named
@Singleton
public final class ArgumentResolverArrayDoubleVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToDoubleVal(values[i]);
        }
        return result;
    }
}
