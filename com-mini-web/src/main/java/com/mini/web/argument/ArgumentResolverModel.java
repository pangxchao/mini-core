package com.mini.web.argument;

import com.mini.web.model.IModel;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public final class ArgumentResolverModel implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return request.getAttribute(IModel.MODEL_KEY);
    }
}
