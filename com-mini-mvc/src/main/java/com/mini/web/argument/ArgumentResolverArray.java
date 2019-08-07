package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Optional.ofNullable;

public abstract class ArgumentResolverArray implements ArgumentResolver {
    @Override
    public final Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return ofNullable(request.getParameterValues(name)).map(values -> parse(values, type, request, response)).orElse(null);
    }

    protected abstract Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response);
}
