package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.util.TypeUtil;
import com.mini.web.interceptor.ActionInvocation;

@Named
@Singleton
public final class ArgumentResolverIntVal extends ArgumentResolverBase {

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return TypeUtil.castToIntVal(text);
    }
}
