package com.mini.web.argument;

import com.mini.util.ObjectUtil;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ArgumentResolverBase implements ArgumentResolver {

    @Override
    public final Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return parse(StringUtil.def(request.getParameter(name), ObjectUtil.toString(request.getAttribute(name))), type, request, response);
    }

    protected abstract Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response);
}
