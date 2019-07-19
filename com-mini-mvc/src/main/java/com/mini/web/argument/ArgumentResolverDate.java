package com.mini.web.argument;

import com.mini.util.LocalDateUtil;
import com.mini.web.config.Configure;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;

import static com.mini.util.StringUtil.def;

@Named
@Singleton
public final class ArgumentResolverDate extends ArgumentResolverBase {

    private Configure configure;

    @Inject
    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
        String format = def(configure.getDateFormat(), "yyyy-MM-dd");
        LocalDate date = LocalDateUtil.parse(text, format);

        // java.time.LocalDateTime 类型的参数
        if (LocalDate.class.isAssignableFrom(type)) {
            return date;
        }

        // java.sql.Date 类型的参数
        if (Date.class.isAssignableFrom(type)) {
            return Date.valueOf(date);
        }
        // 其它类型的参数
        return null;
    }
}
