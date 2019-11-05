package com.mini.web.argument;

import com.mini.util.StringUtil;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.format.DateTimeFormatter.ofPattern;

@Named
@Singleton
public final class ArgumentResolverDate extends ArgumentResolverBase {
    private String dateTimeFormat = "yyyy-MM-dd HH[:mm[:ss]]";
    private String dateFormat = "yyyy[-MM[-dd]]";
    private String timeFormat = "HH[:mm[:ss]]";

    /**
     * 日期时间默认格式化
     * @param dateTimeFormat 格式化
     */
    @Inject
    public final void setDateTimeFormat(
            @Named("mini.http.datetime-format")
            @Nullable String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     * 日期默认格式化
     * @param dateFormat 格式化
     */
    @Inject
    public final void setDateFormat(
            @Named("mini.http.date-format")
            @Nullable String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 时间默认格式化
     * @param timeFormat 格式化
     */
    @Inject
    public final void setTimeFormat(
            @Named("mini.http.time-format")
            @Nullable String timeFormat) {
        this.timeFormat = timeFormat;
    }

    @Override
    protected Object parse(String text, @Nonnull Class<?> type, @Nonnull ActionInvocation invocation) {
        // 如果参数为空，则返回 Null
        if (StringUtil.isBlank(text)) {
            return null;
        }

        // java.time.LocalDateTime 类型的参数
        if (java.time.LocalDateTime.class.isAssignableFrom(type)) {
            DateTimeFormatter format = ofPattern(dateTimeFormat);
            return LocalDateTime.parse(text, format);
        }

        // java.time.LocalDate 类型的参数
        if (java.time.LocalDate.class.isAssignableFrom(type)) {
            DateTimeFormatter format = ofPattern(dateFormat);
            return LocalDate.parse(text, format);
        }

        // java.time.LocalTime 类型的参数
        if (java.time.LocalTime.class.isAssignableFrom(type)) {
            DateTimeFormatter format = ofPattern(timeFormat);
            return LocalTime.parse(text, format);
        }

        // java.sql.Timestamp 类型的参数
        if (java.sql.Timestamp.class.isAssignableFrom(type)) {
            DateTimeFormatter format = ofPattern(dateTimeFormat);
            LocalDateTime date = LocalDateTime.parse(text, format);
            return java.sql.Timestamp.valueOf(date);
        }

        // java.sql.Date 类型的参数
        if (java.sql.Date.class.isAssignableFrom(type)) {
            DateTimeFormatter format = ofPattern(dateFormat);
            LocalDate date = LocalDate.parse(text, format);
            return java.sql.Date.valueOf(date);
        }

        // java.sql.Time 类型的参数
        if (java.sql.Time.class.isAssignableFrom(type)) {
            DateTimeFormatter format = ofPattern(timeFormat);
            LocalTime time = LocalTime.parse(text, format);
            return java.sql.Time.valueOf(time);
        }

        // java.util.Date 类型的参数
        if (java.util.Date.class.isAssignableFrom(type)) {
            try {
                DateTimeFormatter format = ofPattern(dateTimeFormat);
                LocalDateTime date = LocalDateTime.parse(text, format);
                return java.sql.Timestamp.valueOf(date);
            } catch (DateTimeParseException ignored) {}

            try {
                DateTimeFormatter format = ofPattern(dateFormat);
                LocalDate date = LocalDate.parse(text, format);
                return java.sql.Date.valueOf(date);
            } catch (DateTimeParseException ignored) {}
        }

        return null;
    }
}
