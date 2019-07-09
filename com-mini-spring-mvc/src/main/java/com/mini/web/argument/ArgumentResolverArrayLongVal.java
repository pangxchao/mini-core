package com.mini.web.argument;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToLongVal;

@Component
public final class ArgumentResolverArrayLongVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        long[] result = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToLongVal(values[i]);
        }
        return result;
    }
}
