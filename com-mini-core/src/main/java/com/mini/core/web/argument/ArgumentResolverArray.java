package com.mini.core.web.argument;

import com.mini.core.util.Assert;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;
import com.mini.core.web.interceptor.ActionInvocation;

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
import java.util.function.Function;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ofPattern;

public abstract class ArgumentResolverArray implements ArgumentResolver, EventListener {
    private final Map<Class<?>, Function<String[], Object>> map = new HashMap<>() {{
        // String[].class 处理
        put(String[].class, values -> {
            return values; //
        });

        // Long[].class 处理
        put(Long[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .map(Long::parseLong)
            .toArray(Long[]::new));

        // long[].class 处理
        put(long[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .mapToLong(Long::parseLong)
            .toArray());

        // Integer[].class 处理
        put(Integer[].class, values -> Stream.ofNullable(values).flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .map(Integer::parseInt)
            .toArray(Integer[]::new));

        // int[].class 处理
        put(int[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .mapToInt(Integer::parseInt)
            .toArray());

        // Short[].class 处理
        put(Short[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .map(Short::parseShort)
            .toArray(Short[]::new));

        // short[].class 处理
        put(short[].class, values -> {
            Short[] arrays = Stream.ofNullable(values)
                .flatMap(Stream::of)
                .map(Short::parseShort)
                .toArray(Short[]::new);
            short[] copy = new short[arrays.length];
            System.arraycopy(arrays, 0, copy, 0, arrays.length);
            return copy;
        });

        // Byte.class 处理
        put(Byte[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .map(Byte::parseByte)
            .toArray(Byte[]::new));

        // byte[].class 处理
        put(byte[].class, values -> {
            Byte[] arrays = Stream.ofNullable(values)
                .flatMap(Stream::of)
                .map(Byte::parseByte)
                .toArray(Byte[]::new);
            byte[] copy = new byte[arrays.length];
            System.arraycopy(arrays, 0, copy, 0, arrays.length);
            return copy;
        });

        // Double.class 处理
        put(Double.class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .map(Double::parseDouble)
            .toArray(Double[]::new));

        // double[].class 处理
        put(double[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .mapToDouble(Double::parseDouble)
            .toArray());

        // Float[].class 处理
        put(Float[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .map(Float::parseFloat)
            .toArray(Float[]::new));

        // float[].class 处理
        put(float[].class, values -> {
            Float[] arrays = Stream.ofNullable(values)
                .flatMap(Stream::of)
                .map(Float::parseFloat)
                .toArray(Float[]::new);
            float[] copy = new float[arrays.length];
            System.arraycopy(arrays, 0, copy, 0, arrays.length);
            return copy;
        });

        // Boolean[].class 处理
        put(Boolean[].class, values -> Stream.ofNullable(values)
            .flatMap(Stream::of)
            .filter(value -> !value.isBlank())
            .map(Boolean::parseBoolean)
            .toArray(Boolean[]::new));

        // boolean[].class 处理
        put(boolean[].class, values -> {
            Boolean[] arrays = Stream.ofNullable(values)
                .flatMap(Stream::of)
                .map(Boolean::parseBoolean)
                .toArray(Boolean[]::new);
            boolean[] copy = new boolean[arrays.length];
            System.arraycopy(arrays, 0, copy, 0, arrays.length);
            return copy;
        });

        // java.util.Date.class 处理
        put(java.util.Date.class, values -> Stream.ofNullable(values) //
            .flatMap(Stream::of).filter(value -> !value.isBlank())  //
            .map(text -> {
                try {
                    DateTimeFormatter format = ofPattern(dateTimeFormat);
                    LocalDateTime date = LocalDateTime.parse(text, format);
                    return java.sql.Timestamp.valueOf(date);
                } catch (DateTimeParseException ignored) {
                }

                try {
                    DateTimeFormatter format = ofPattern(dateFormat);
                    LocalDate date = LocalDate.parse(text, format);
                    return java.sql.Date.valueOf(date);
                } catch (DateTimeParseException ignored) {
                }
                return null;
            }).toArray(java.util.Date[]::new));

        // java.time.LocalDateTime 类型的参数
        put(java.time.LocalDateTime.class, values -> Stream.ofNullable(values) //
            .flatMap(Stream::of).filter(value -> !value.isBlank())  //
            .map(text -> {
                try {
                    DateTimeFormatter format = ofPattern(dateTimeFormat);
                    return LocalDateTime.parse(text, format);
                } catch (DateTimeParseException ignored) {
                }
                return null;
            }).toArray(LocalDateTime[]::new));

        // java.time.LocalDate 类型的参数
        put(java.time.LocalDate.class, values -> Stream.ofNullable(values) //
            .flatMap(Stream::of).filter(value -> !value.isBlank()) //
            .map(text -> {
                try {
                    DateTimeFormatter format = ofPattern(dateTimeFormat);
                    return LocalDate.parse(text, format);
                } catch (DateTimeParseException ignored) {
                }
                return null;
            }).toArray(LocalDate[]::new));

        // java.time.LocalTime 类型的参数
        put(java.time.LocalTime.class, values -> Stream.ofNullable(values) //
            .flatMap(Stream::of).filter(value -> !value.isBlank()) //
            .map(text -> {
                try {
                    DateTimeFormatter format = ofPattern(dateTimeFormat);
                    return LocalTime.parse(text, format);
                } catch (DateTimeParseException ignored) {
                }
                return null;
            }).toArray(LocalTime[]::new));

        // java.sql.Timestamp 类型的参数
        put(java.sql.Timestamp.class, values -> Stream.ofNullable(values) //
            .flatMap(Stream::of).map(text -> {
                try {
                    DateTimeFormatter format = ofPattern(dateTimeFormat);
                    LocalDateTime date = LocalDateTime.parse(text, format);
                    return java.sql.Timestamp.valueOf(date);
                } catch (DateTimeParseException ignored) {
                }
                return null;
            }).toArray(java.sql.Timestamp[]::new));

        // java.sql.Date 类型的参数
        put(java.sql.Date.class, values -> Stream.ofNullable(values) //
            .flatMap(Stream::of).filter(value -> !value.isBlank()) //
            .map(text -> {
                try {
                    DateTimeFormatter format = ofPattern(dateFormat);
                    LocalDate date = LocalDate.parse(text, format);
                    return java.sql.Date.valueOf(date);
                } catch (DateTimeParseException ignored) {
                }
                return null;
            }).toArray(java.sql.Date[]::new));

        // java.sql.Time 类型的参数
        put(java.sql.Time.class, values -> Stream.ofNullable(values) //
            .flatMap(Stream::of).filter(value -> !value.isBlank()) //
            .map(text -> {
                try {
                    DateTimeFormatter format = ofPattern(timeFormat);
                    LocalTime time = LocalTime.parse(text, format);
                    return java.sql.Time.valueOf(time);
                } catch (DateTimeParseException ignored) {
                }
                return null;
            }).toArray(java.sql.Time[]::new));
    }};

    @Inject
    @Named("DateTimeFormat")
    private String dateTimeFormat;

    @Inject
    @Named("DateFormat")
    private String dateFormat;

    @Inject
    @Named("TimeFormat")
    private String timeFormat;

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
        return function.apply(value);
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
