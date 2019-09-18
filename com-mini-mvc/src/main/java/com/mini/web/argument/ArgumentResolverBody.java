package com.mini.web.argument;

import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import static com.mini.util.StringUtil.join;

@Named
@Singleton
public final class ArgumentResolverBody implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) throws Exception {
        return new StringBuilder(join("\r\n", invocation.getRequest().getReader() //
                .lines().toArray(String[]::new)));
    }

}
