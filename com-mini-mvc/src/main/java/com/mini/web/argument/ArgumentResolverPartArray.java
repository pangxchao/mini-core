package com.mini.web.argument;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;

import static java.util.Optional.ofNullable;

@Named
@Singleton
public final class ArgumentResolverPartArray implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws Exception {
        return ofNullable(request.getParts()).stream().flatMap(Collection::stream).filter(part -> part != null && name.equals(part.getName())).toArray(Part[]::new);
    }
}
