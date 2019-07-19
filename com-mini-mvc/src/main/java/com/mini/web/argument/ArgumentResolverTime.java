package com.mini.web.argument;

import com.mini.util.LocalTimeUtil;
import com.mini.web.config.Configure;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.time.LocalTime;

import static com.mini.util.StringUtil.def;

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
        String format = def(configure.getDateFormat(), "HH:mm:ss");
        LocalTime time = LocalTimeUtil.parse(text, format);

        // java.util.LocalTime 类型的参数
        if (LocalTime.class.isAssignableFrom(type)) {
            return time;
        }

        // java.sql.Time 类型的参数
        if(Time.class.isAssignableFrom(type)){
            return Time.valueOf(time);
        }

        // 其它类型的参数
        return null;
    }
}
