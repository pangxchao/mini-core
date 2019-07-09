package com.mini.web.argument;

import com.mini.util.DateUtil;
import com.mini.util.StringUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class ArgumentResolverTime extends ArgumentResolverBase {

    //@Inject
    //@Named("mini.http.time-format")
    private String format;

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return DateUtil.parse(text, StringUtil.def(format, "HH:mm:ss"));
    }
}
