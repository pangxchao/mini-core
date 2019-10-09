package com.mini.web.argument;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.util.StringUtil;
import com.mini.web.config.Configure;
import com.mini.web.interceptor.ActionInvocation;

@Named
@Singleton
public final class ArgumentResolverDate extends ArgumentResolverBase {

    private Configure configure;

    @Inject
    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return Optional.ofNullable(text).map(t -> {
            String format = StringUtil.def(configure.getDateFormat(), "yyyy-MM-dd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate date = LocalDate.parse(t, formatter);

            // java.time.LocalDate 类型的参数
            if (LocalDate.class.isAssignableFrom(type)) {
                return date;
            }

            // java.sql.Date 类型的参数
            if (Date.class.isAssignableFrom(type)) {
                return Date.valueOf(date);
            }
            // 其它类型的参数
            return null;
        }).orElse(null);
    }
}
