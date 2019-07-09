package com.mini.web.argument;

import com.mini.util.TypeUtil;

import javax.annotation.Nonnull;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class ArgumentResolverByteVal extends ArgumentResolverBase {

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return TypeUtil.castToByteVal(text);
    }
}
