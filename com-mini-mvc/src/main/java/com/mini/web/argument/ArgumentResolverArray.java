package com.mini.web.argument;

import com.mini.core.util.Assert;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.web.interceptor.ActionInvocation;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ofPattern;

public abstract class ArgumentResolverArray implements ArgumentResolver, EventListener {
    private final Map<Class<?>, Function<String[], ?>> map = new HashMap<>() {{
        // String[].class 处理
        put(String[].class, (Function<String[], String[]>) values -> {
            return values; //
        });

        // Long[].class 处理
        put(Long[].class, (Function<String[], Long[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(Long::parseLong)
                        .toArray(Long[]::new));

        // long[].class 处理
        put(long[].class, (Function<String[], long[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .mapToLong(Long::parseLong)
                        .toArray());

        // Integer[].class 处理
        put(Integer[].class, (Function<String[], Integer[]>) values ->//
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new));

        // int[].class 处理
        put(int[].class, (Function<String[], int[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .mapToInt(Integer::parseInt)
                        .toArray());

        // Short[].class 处理
        put(Short[].class, (Function<String[], Short[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .map(Short::parseShort)
                        .toArray(Short[]::new));

        // Byte.class 处理
        put(Byte[].class, (Function<String[], Byte[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(Byte::parseByte)
                        .toArray(Byte[]::new));

        // Double.class 处理
        put(Double.class, (Function<String[], Double[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(Double::parseDouble)
                        .toArray(Double[]::new));

        // double[].class 处理
        put(double[].class, (Function<String[], double[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .mapToDouble(Double::parseDouble)
                        .toArray());
        // Float[].class 处理
        put(Float[].class, (Function<String[], Float[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(Float::parseFloat)
                        .toArray(Float[]::new));

        // Boolean[].class 处理
        put(Boolean[].class, (Function<String[], Boolean[]>) values -> //
                Stream.ofNullable(values)
                        .flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(Boolean::parseBoolean)
                        .toArray(Boolean[]::new));

        // java.util.Date.class 处理
        put(java.util.Date.class, (Function<String[], java.util.Date[]>) values -> //
                Stream.ofNullable(values).flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(text -> {
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
                            return null;
                        }).toArray(java.util.Date[]::new));

        // java.time.LocalDateTime 类型的参数
        put(java.time.LocalDateTime.class, (Function<String[], java.time.LocalDateTime[]>) values -> //
                Stream.ofNullable(values).flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(text -> {
                            try {
                                DateTimeFormatter format = ofPattern(dateTimeFormat);
                                return LocalDateTime.parse(text, format);
                            } catch (DateTimeParseException ignored) {}
                            return null;
                        }).toArray(LocalDateTime[]::new));

        // java.time.LocalDate 类型的参数
        put(java.time.LocalDate.class, (Function<String[], java.time.LocalDate[]>) values -> //
                Stream.ofNullable(values).flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(text -> {
                            try {
                                DateTimeFormatter format = ofPattern(dateTimeFormat);
                                return LocalDate.parse(text, format);
                            } catch (DateTimeParseException ignored) {}
                            return null;
                        }).toArray(LocalDate[]::new));

        // java.time.LocalTime 类型的参数
        put(java.time.LocalTime.class, (Function<String[], java.time.LocalTime[]>) values -> //
                Stream.ofNullable(values).flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(text -> {
                            try {
                                DateTimeFormatter format = ofPattern(dateTimeFormat);
                                return LocalTime.parse(text, format);
                            } catch (DateTimeParseException ignored) {}
                            return null;
                        }).toArray(LocalTime[]::new));

        // java.sql.Timestamp 类型的参数
        put(java.sql.Timestamp.class, (Function<String[], java.sql.Timestamp[]>) values -> //
                Stream.ofNullable(values).flatMap(Stream::of).map(text -> {
                    try {
                        DateTimeFormatter format = ofPattern(dateTimeFormat);
                        LocalDateTime date = LocalDateTime.parse(text, format);
                        return java.sql.Timestamp.valueOf(date);
                    } catch (DateTimeParseException ignored) {}
                    return null;
                }).toArray(java.sql.Timestamp[]::new));

        // java.sql.Date 类型的参数
        put(java.sql.Date.class, (Function<String[], java.sql.Date[]>) values ->  //
                Stream.ofNullable(values).flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(text -> {
                            try {
                                DateTimeFormatter format = ofPattern(dateFormat);
                                LocalDate date = LocalDate.parse(text, format);
                                return java.sql.Date.valueOf(date);
                            } catch (DateTimeParseException ignored) {}
                            return null;
                        }).toArray(java.sql.Date[]::new));

        // java.sql.Time 类型的参数
        put(java.sql.Time.class, (Function<String[], java.sql.Time[]>) values -> //
                Stream.ofNullable(values).flatMap(Stream::of)
                        .filter(value -> !value.isBlank())
                        .map(text -> {
                            try {
                                DateTimeFormatter format = ofPattern(timeFormat);
                                LocalTime time = LocalTime.parse(text, format);
                                return java.sql.Time.valueOf(time);
                            } catch (DateTimeParseException ignored) {}
                            return null;
                        }).toArray(java.sql.Time[]::new));
    }};

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
    public boolean supportParameter(MiniParameter parameter) {
        return map.get(parameter.getType()) != null;
    }

    @Override
    public Object getValue(MiniParameter parameter, ActionInvocation invocation) {
        Function<String[], ?> function = map.get(parameter.getType());
        Assert.notNull(function, "Unsupported parameter type.");

        // 获取参数名称和参值，并处理
        String[] value = getValue(getParameterName(parameter), invocation);
        return Optional.of(function).map(func -> {
            return func.apply(value); //
        }).orElse(null);
    }

    /**
     * 获取参数名称
     * @param parameter 参数对象
     * @return 参数名称
     */
    protected abstract String getParameterName(MiniParameter parameter);

    /**
     * 根据参数名称获取参数值
     * @param name       参数名称
     * @param invocation Action 调用对象
     * @return 参数值
     */
    protected abstract String[] getValue(String name, ActionInvocation invocation);
}
