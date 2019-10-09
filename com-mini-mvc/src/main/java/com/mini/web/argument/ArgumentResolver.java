package com.mini.web.argument;

import javax.annotation.Nonnull;

import com.mini.web.interceptor.ActionInvocation;

public interface ArgumentResolver {
    Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invoke) throws Exception;
}
