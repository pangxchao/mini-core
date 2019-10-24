package com.mini.web.argument;

import com.mini.util.TypeUtil;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import static java.util.Arrays.stream;

@Named
@Singleton
public final class ArgumentResolverArrayLongVal extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return stream(values).mapToLong(TypeUtil::castToLongVal).toArray();
    }
}
