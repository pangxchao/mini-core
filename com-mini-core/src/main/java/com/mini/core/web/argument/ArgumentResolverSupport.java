package com.mini.core.web.argument;

import com.mini.core.web.support.config.Configures;

import javax.annotation.Nullable;
import java.io.Serializable;
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

public final class ArgumentResolverSupport implements Serializable, EventListener {
	private ArgumentResolverSupport() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	private static Configures configures;

	public static void init(Configures configures) {
		ArgumentResolverSupport.configures = configures;
	}

	private static final Map<Class<?>, Function<String, Object>> BASIC_MAP = new HashMap<>() {{
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
		put(int.class, value -> Optional.ofNullable(value)
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
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						LocalDateTime date = LocalDateTime.parse(text, format);
						return java.sql.Timestamp.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}

					try {
						DateTimeFormatter format = ofPattern(configures.getDateFormat());
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
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalDateTime.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.time.LocalDate 类型的参数
		put(java.time.LocalDate.class, value -> Optional.ofNullable(value) //
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalDate.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.time.LocalTime 类型的参数
		put(java.time.LocalTime.class, value -> Optional.ofNullable(value) //
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalTime.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.sql.Timestamp 类型的参数
		put(java.sql.Timestamp.class, value -> Optional.ofNullable(value) //
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
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
						DateTimeFormatter format = ofPattern(configures.getDateFormat());
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
						DateTimeFormatter format = ofPattern(configures.getTimeFormat());
						LocalTime time = LocalTime.parse(text, format);
						return java.sql.Time.valueOf(time);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));
	}};

	private static final Map<Class<?>, Function<String[], Object>> ARRAY_MAP = new HashMap<>() {{
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
		put(Integer[].class, values -> Stream.ofNullable(values)
				.flatMap(Stream::of)
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

		// Byte[].class 处理
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

		// Double[].class 处理
		put(Double[].class, values -> Stream.ofNullable(values)
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

		// java.util.Date[].class 处理
		put(java.util.Date[].class, values -> Stream.ofNullable(values) //
				.flatMap(Stream::of).filter(value -> !value.isBlank())  //
				.map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						LocalDateTime date = LocalDateTime.parse(text, format);
						return java.sql.Timestamp.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}

					try {
						DateTimeFormatter format = ofPattern(configures.getDateFormat());
						LocalDate date = LocalDate.parse(text, format);
						return java.sql.Date.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).toArray(java.util.Date[]::new));

		// java.time.LocalDateTime[] 类型的参数
		put(java.time.LocalDateTime[].class, values -> Stream.ofNullable(values) //
				.flatMap(Stream::of).filter(value -> !value.isBlank())  //
				.map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalDateTime.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).toArray(LocalDateTime[]::new));

		// java.time.LocalDate[] 类型的参数
		put(java.time.LocalDate[].class, values -> Stream.ofNullable(values) //
				.flatMap(Stream::of).filter(value -> !value.isBlank()) //
				.map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalDate.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).toArray(LocalDate[]::new));

		// java.time.LocalTime 类型的参数
		put(java.time.LocalTime[].class, values -> Stream.ofNullable(values) //
				.flatMap(Stream::of).filter(value -> !value.isBlank()) //
				.map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalTime.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).toArray(LocalTime[]::new));

		// java.sql.Timestamp 类型的参数
		put(java.sql.Timestamp[].class, values -> Stream.ofNullable(values) //
				.flatMap(Stream::of).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						LocalDateTime date = LocalDateTime.parse(text, format);
						return java.sql.Timestamp.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).toArray(java.sql.Timestamp[]::new));

		// java.sql.Date 类型的参数
		put(java.sql.Date[].class, values -> Stream.ofNullable(values) //
				.flatMap(Stream::of).filter(value -> !value.isBlank()) //
				.map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateFormat());
						LocalDate date = LocalDate.parse(text, format);
						return java.sql.Date.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).toArray(java.sql.Date[]::new));

		// java.sql.Time 类型的参数
		put(java.sql.Time[].class, values -> Stream.ofNullable(values) //
				.flatMap(Stream::of).filter(value -> !value.isBlank()) //
				.map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getTimeFormat());
						LocalTime time = LocalTime.parse(text, format);
						return java.sql.Time.valueOf(time);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).toArray(java.sql.Time[]::new));
	}};

	private static final Map<Class<?>, Function<String[], Object>> BEAN_MAP = new HashMap<>(ARRAY_MAP) {{
		// String.class 处理
		put(String.class, values -> Optional.ofNullable(values)
				.filter(v -> v.length > 0).map(v -> v[0])
				.orElse(null));

		// Long.class 处理
		put(Long.class, values -> Optional.ofNullable(values)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Long::parseLong)
				.orElse(null));

		// long.class 处理
		put(long.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Long::parseLong)
				.orElse(0L));

		// Integer.class 处理
		put(Integer.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Integer::parseInt)
				.orElse(null));

		// int.class 处理
		put(int.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Integer::parseInt)
				.orElse(0));

		// Short.class 处理
		put(Short.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Short::parseShort)
				.orElse(null));

		// Short.class 处理
		put(short.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Short::parseShort)
				.orElse((short) 0));

		// Byte.class 处理
		put(Byte.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0)
				.map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Byte::parseByte)
				.orElse(null));

		// byte.class 处理
		put(byte.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Byte::parseByte)
				.orElse((byte) 0));

		// Double.class 处理
		put(Double.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Double::parseDouble)
				.orElse(null));

		// double.class 处理
		put(double.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Double::parseDouble)
				.orElse(0D));

		// Float.class 处理
		put(Float.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Float::parseFloat)
				.orElse(null));

		// float.class 处理
		put(float.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.map(Float::parseFloat)
				.orElse(0F));

		// Boolean.class 处理
		put(Boolean.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Boolean::parseBoolean)
				.orElse(null));

		// boolean.class 处理
		put(boolean.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank())
				.map(Boolean::parseBoolean)
				.orElse(false));

		// java.util.Date.class 处理
		put(java.util.Date.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						LocalDateTime date = LocalDateTime.parse(text, format);
						return java.sql.Timestamp.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}

					try {
						DateTimeFormatter format = ofPattern(configures.getDateFormat());
						LocalDate date = LocalDate.parse(text, format);
						return java.sql.Date.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.time.LocalDateTime 类型的参数
		put(java.time.LocalDateTime.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalDateTime.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.time.LocalDate 类型的参数
		put(java.time.LocalDate.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalDate.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.time.LocalTime 类型的参数
		put(java.time.LocalTime.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						return LocalTime.parse(text, format);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.sql.Timestamp 类型的参数
		put(java.sql.Timestamp.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateTimeFormat());
						LocalDateTime date = LocalDateTime.parse(text, format);
						return java.sql.Timestamp.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.sql.Date 类型的参数
		put(java.sql.Date.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getDateFormat());
						LocalDate date = LocalDate.parse(text, format);
						return java.sql.Date.valueOf(date);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));

		// java.sql.Time 类型的参数
		put(java.sql.Time.class, value -> Optional.ofNullable(value)
				.filter(v -> v.length > 0).map(v -> v[0])
				.filter(v -> !v.isBlank()).map(text -> {
					try {
						DateTimeFormatter format = ofPattern(configures.getTimeFormat());
						LocalTime time = LocalTime.parse(text, format);
						return java.sql.Time.valueOf(time);
					} catch (DateTimeParseException ignored) {
					}
					return null;
				}).orElse(null));
	}};

	@Nullable
	public static Function<String, Object> getBasicFunc(Class<?> type) {
		return BASIC_MAP.get(type);
	}

	@Nullable
	public static Function<String[], Object> getArrayFunc(Class<?> type) {
		return ARRAY_MAP.get(type);
	}

	@Nullable
	public static Function<String[], Object> getBeanFunc(Class<?> type) {
		return BEAN_MAP.get(type);
	}
}
