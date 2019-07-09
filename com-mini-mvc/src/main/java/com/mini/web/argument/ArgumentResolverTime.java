package com.mini.web.argument;

import com.mini.util.DateUtil;
import com.mini.util.StringUtil;
import com.mini.web.config.WebMvcConfigureDefault;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@Singleton
public final class ArgumentResolverTime extends ArgumentResolverBase {
    private WebMvcConfigureDefault configureDefault;

    @Inject
    public void setConfigureDefault(WebMvcConfigureDefault configureDefault) {
        this.configureDefault = configureDefault;
    }

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return DateUtil.parse(text, StringUtil.def(configureDefault.getTimeFormat(), "HH:mm:ss"));
    }


}
