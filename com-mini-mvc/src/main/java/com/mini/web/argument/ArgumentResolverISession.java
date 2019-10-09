package com.mini.web.argument;

import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.web.util.ISession.SESSION_KEY;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.web.interceptor.ActionInvocation;

@Named
@Singleton
public final class ArgumentResolverISession implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        Object value = invocation.getRequest().getSession().getAttribute(SESSION_KEY);
        return defIfNull(value, invocation.getRequest().getAttribute(SESSION_KEY));
    }
}
