package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.ObjectUtil.defIfNull;
import static com.mini.web.util.ISession.SESSION_KEY;

@Named
@Singleton
public final class ArgumentResolverISession implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return defIfNull(request.getSession().getAttribute(SESSION_KEY), request.getAttribute(SESSION_KEY));
    }
}
