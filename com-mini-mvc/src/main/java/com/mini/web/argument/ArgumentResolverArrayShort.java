package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToShort;

@Named
@Singleton
public final class ArgumentResolverArrayShort extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        Short[] result = new Short[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = castToShort(values[i]);
        }
        return result;
    }
}
