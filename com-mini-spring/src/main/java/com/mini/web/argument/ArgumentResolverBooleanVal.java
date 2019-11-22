package com.mini.web.argument;

import com.mini.util.TypeUtil;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public final class ArgumentResolverBooleanVal extends ArgumentResolverBase {
    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return TypeUtil.castToBoolVal(text);
    }
}
