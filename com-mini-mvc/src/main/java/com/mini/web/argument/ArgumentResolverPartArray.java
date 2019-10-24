package com.mini.web.argument;

import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.Part;
import java.util.Collection;

import static java.util.Optional.ofNullable;

@Named
@Singleton
public final class ArgumentResolverPartArray implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) throws Exception {
        return ofNullable(invocation.getRequest().getParts()).stream().flatMap(Collection::stream) //
                .filter(p -> p != null && name.equals(p.getName())).toArray(Part[]::new);
    }
}
