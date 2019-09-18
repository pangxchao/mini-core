package com.mini.web.argument;

import com.mini.web.interceptor.ActionInvocation;
import com.mini.web.model.IModel;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@Singleton
public final class ArgumentResolverModel implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return invocation.getModel();
    }
}
