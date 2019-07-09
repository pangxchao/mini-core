package com.mini.web.argument;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToFloat;

@Component
public final class ArgumentResolverArrayFloat extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        Float[] result = new Float[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToFloat(values[i]);
        }
        return result;
    }
}
