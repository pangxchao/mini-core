package com.mini.web.argument;

import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;

public interface ArgumentResolver {
    Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invoke) throws Exception;
}
