package com.mini.core.web.argument;

import com.mini.core.util.Assert;
import com.mini.core.util.reflect.MiniParameter;
import com.mini.core.web.interceptor.ActionInvocation;

import javax.annotation.Nonnull;
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

import static java.time.format.DateTimeFormatter.ofPattern;

public abstract class ArgumentResolverBasic implements ArgumentResolver, EventListener {
	private final Map<Class<?>, Function<String, Object>> map = new HashMap<>() {{
		// String.class 处理
		put(String.class, value -> value);

		// Long.class 处理
		put(Long.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Long::parseLong)
			.orElse(null));

		// long.class 处理
		put(long.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Long::parseLong)
			.orElse(0L));

		// Integer.class 处理
		put(Integer.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Integer::parseInt)
			.orElse(null));

		// int.class 处理
		put(int.class, value -> (int) Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Integer::parseInt)
			.orElse(0));

		// Short.class 处理
		put(Short.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Short::parseShort)
			.orElse(null));

		// Short.class 处理
		put(short.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Short::parseShort)
			.orElse((short) 0));

		// Byte.class 处理
		put(Byte.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Byte::parseByte)
			.orElse(null));

		// byte.class 处理
		put(byte.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Byte::parseByte)
			.orElse((byte) 0));

		// Double.class 处理
		put(Double.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Double::parseDouble)
			.orElse(null));

		// double.class 处理
		put(double.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Double::parseDouble)
			.orElse(0D));

		// Float.class 处理
		put(Float.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Float::parseFloat)
			.orElse(null));

		// float.class 处理
		put(float.class, value -> Optional.ofNullable(value)
			.map(Float::parseFloat)
			.orElse(0F));

		// Boolean.class 处理
		put(Boolean.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Boolean::parseBoolean)
			.orElse(null));

		// boolean.class 处理
		put(boolean.class, value -> Optional.ofNullable(value)
			.filter(v -> !v.isBlank())
			.map(Boolean::parseBoolean)
			.orElse(false));

		// java.util.Date.class 处理
		put(java.util.Date.class, value -> Optional.ofNullable(value) //
			.filter(v -> !v.isBlank()).map(text -> {
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
			}).orElse(null));

		// java.time.LocalDateTime 类型的参数
		put(java.time.LocalDateTime.class, value -> Optional.ofNullable(value) //
			.filter(v -> !v.isBlank()).map(text -> {
				try {
					DateTimeFormatter format = ofPattern(dateTimeFormat);
					return LocalDateTime.parse(text, format);
				} catch (DateTimeParseException ignored) {
				}
				return null;
			}).orElse(null));

		// java.time.LocalDate 类型的参数
		put(java.time.LocalDate.class, value -> Optional.ofNullable(value) //
			.filter(v -> !v.isBlank()).map(text -> {
				try {
					DateTimeFormatter format = ofPattern(dateTimeFormat);
					return LocalDate.parse(text, format);
				} catch (DateTimeParseException ignored) {
				}
				return null;
			}).orElse(null));

		// java.time.LocalTime 类型的参数
		put(java.time.LocalTime.class, value -> Optional.ofNullable(value) //
			.filter(v -> !v.isBlank()).map(text -> {
				try {
					DateTimeFormatter format = ofPattern(dateTimeFormat);
					return LocalTime.parse(text, format);
				} catch (DateTimeParseException ignored) {
				}
				return null;
			}).orElse(null));

		// java.sql.Timestamp 类型的参数
		put(java.sql.Timestamp.class, value -> Optional.ofNullable(value) //
			.filter(v -> !v.isBlank()).map(text -> {
				try {
					DateTimeFormatter format = ofPattern(dateTimeFormat);
					LocalDateTime date = LocalDateTime.parse(text, format);
					return java.sql.Timestamp.valueOf(date);
				} catch (DateTimeParseException ignored) {
				}
				return null;
			}).orElse(null));

		// java.sql.Date 类型的参数
		put(java.sql.Date.class, value -> Optional.ofNullable(value) //
			.filter(v -> !v.isBlank()).map(text -> {
				try {
					DateTimeFormatter format = ofPattern(dateFormat);
					LocalDate date = LocalDate.parse(text, format);
					return java.sql.Date.valueOf(date);
				} catch (DateTimeParseException ignored) {
				}
				return null;
			}).orElse(null));

		// java.sql.Time 类型的参数
		put(java.sql.Time.class, value -> Optional.ofNullable(value) //
			.filter(v -> !v.isBlank()).map(text -> {
				try {
					DateTimeFormatter format = ofPattern(timeFormat);
					LocalTime time = LocalTime.parse(text, format);
					return java.sql.Time.valueOf(time);
				} catch (DateTimeParseException ignored) {
				}
				return null;
			}).orElse(null));
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
		Function<String, ?> function = map.get(parameter.getType());
		Assert.notNull(function, "Unsupported parameter type.");

		// 获取参数名称和参值，并处理
		String value = getValue(getParameterName(parameter), invocation);
		return function.apply(value);
	}

	/**
	 * 获取参数名称
	 * @param parameter 参数对象
	 * @return 参数名称
	 */
	@Nonnull
	protected abstract String getParameterName(MiniParameter parameter);

	/**
	 * 根据参数名称获取参数值
	 * @param name       参数名称
	 * @param invocation Action 调用对象
	 * @return 参数值
	 */
	protected abstract String getValue(String name, ActionInvocation invocation);
}
