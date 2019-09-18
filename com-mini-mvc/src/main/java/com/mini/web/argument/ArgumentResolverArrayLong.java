package com.mini.web.argument;

import com.mini.util.TypeUtil;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mini.util.TypeUtil.castToLong;
import static java.util.Arrays.stream;

@Named
@Singleton
public final class ArgumentResolverArrayLong extends ArgumentResolverArray {

    protected Object parse(@Nonnull String[] values, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return stream(values).map(TypeUtil::castToLong).toArray(Long[]::new);
    }
}
