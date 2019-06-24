package com.mini.web.argument;

import com.mini.util.TypeUtil;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public final class ArgumentResolverShortVal extends ArgumentResolverBase {

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return TypeUtil.castToShortVal(text);
    }
}
