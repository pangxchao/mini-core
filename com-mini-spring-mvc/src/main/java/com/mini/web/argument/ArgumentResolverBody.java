package com.mini.web.argument;

import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class ArgumentResolverBody implements ArgumentResolver {
    @Override
    public Object value(@Nonnull String name, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws Exception {
        return new StringBuilder(StringUtil.join("\r\n", request.getReader().lines().toArray(String[]::new)));
    }

}
