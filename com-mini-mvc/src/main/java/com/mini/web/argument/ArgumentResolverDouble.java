package com.mini.web.argument;

import com.mini.util.TypeUtil;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@Singleton
public final class ArgumentResolverDouble extends ArgumentResolverBase {

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return TypeUtil.castToDouble(text);
    }
}
