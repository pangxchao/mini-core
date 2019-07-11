package com.mini.web.argument;

import com.mini.util.LocalTimeUtil;
import com.mini.util.StringUtil;
import com.mini.web.config.Configure;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@Singleton
public final class ArgumentResolverTime extends ArgumentResolverBase {
    private Configure configure;

    @Inject
    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        return LocalTimeUtil.parse(text, StringUtil.def(configure.getTimeFormat(), "HH:mm:ss"));
    }


}
