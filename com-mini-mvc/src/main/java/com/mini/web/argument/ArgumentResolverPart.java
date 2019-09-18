package com.mini.web.argument;

import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

@Named
@Singleton
public final class ArgumentResolverPart implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) throws Exception {
        return Optional.ofNullable(invocation.getRequest().getPart(name)) //
                .filter(p -> p.getSize() > 0).orElse(null);
    }
}
