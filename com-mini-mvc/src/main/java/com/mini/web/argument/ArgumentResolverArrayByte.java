package com.mini.web.argument;

import static java.util.Arrays.stream;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.util.TypeUtil;
import com.mini.web.interceptor.ActionInvocation;

@Named
@Singleton
public final class ArgumentResolverArrayByte extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return stream(values).map(TypeUtil::castToByte).toArray(Byte[]::new);
    }
}
