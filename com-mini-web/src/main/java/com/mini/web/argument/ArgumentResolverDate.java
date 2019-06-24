package com.mini.web.argument;

import com.mini.util.DateUtil;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public final class ArgumentResolverDate extends ArgumentResolverBase {

    @Inject
    @Named("mini.http.date-format")
    private String format;

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return DateUtil.parse(text, StringUtil.def(format, "yyyy-MM-dd"));
    }
}
