package com.mini.web.argument;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToDouble;

@Component
public final class ArgumentResolverArrayDouble extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        Double[] result = new Double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToDouble(values[i]);
        }
        return result;
    }
}
