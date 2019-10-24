package com.mini.web.argument;

import com.mini.util.StringUtil;
import com.mini.web.config.Configure;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Named
@Singleton
public final class ArgumentResolverTime extends ArgumentResolverBase {
    private Configure configure;

    @Inject
    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        return Optional.ofNullable(text).map(t -> {
            String format = StringUtil.def(configure.getDateFormat(), "HH:mm:ss");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

            LocalTime time = LocalTime.parse(t, formatter);

            // java.util.LocalTime 类型的参数
            if (LocalTime.class.isAssignableFrom(type)) {
                return time;
            }

            // java.sql.Time 类型的参数
            if (Time.class.isAssignableFrom(type)) {
                return Time.valueOf(time);
            }

            // 其它类型的参数
            return null;
        }).orElse(null);
    }
}
