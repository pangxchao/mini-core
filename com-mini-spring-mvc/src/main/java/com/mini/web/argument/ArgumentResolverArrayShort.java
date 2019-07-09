package com.mini.web.argument;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToShort;

@Component
public final class ArgumentResolverArrayShort extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        Short[] result = new Short[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToShort(values[i]);
        }
        return result;
    }
}
