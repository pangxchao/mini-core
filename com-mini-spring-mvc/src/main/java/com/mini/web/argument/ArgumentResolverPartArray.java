package com.mini.web.argument;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.Optional;

@Component
public final class ArgumentResolverPartArray implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws Exception {
        return Optional.ofNullable(request.getParts()).stream().flatMap(Collection::stream).filter(part -> part != null && name.equals(part.getName())).toArray(Part[]::new);
    }
}
