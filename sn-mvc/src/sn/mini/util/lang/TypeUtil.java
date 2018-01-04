/**
 * Created the sn.mini.util.converter.TypeUtil.java
 * @created 2017年8月31日 下午4:40:36
 * @version 1.0.0
 */
package sn.mini.util.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.BigIntegerStringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.ByteStringConverter;
import javafx.util.converter.CharacterStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import javafx.util.converter.ShortStringConverter;

/**
 * sn.mini.util.converter.TypeUtil.java
 * @author XChao
 */
public final class TypeUtil {
	private final Map<Class<?>, StringConverter<?>> converter_map = new ConcurrentHashMap<>();
	private static final TypeUtil instance = new TypeUtil();

	private TypeUtil() { // 无法new当前类
		converter_map.put(String.class, new StringConverter<String>() {
			public String toString(String object) {
				return object;
			}

			public String fromString(String string) {
				return string;
			}
		});

		converter_map.put(Long.class, new LongStringConverter());
		converter_map.put(long.class, new LongStringConverter());

		converter_map.put(Integer.class, new IntegerStringConverter());
		converter_map.put(int.class, new IntegerStringConverter());

		converter_map.put(Short.class, new ShortStringConverter());
		converter_map.put(short.class, new ShortStringConverter());

		converter_map.put(Byte.class, new ByteStringConverter());
		converter_map.put(byte.class, new ByteStringConverter());

		converter_map.put(Double.class, new DoubleStringConverter());
		converter_map.put(double.class, new DoubleStringConverter());

		converter_map.put(Float.class, new FloatStringConverter());
		converter_map.put(float.class, new FloatStringConverter());

		converter_map.put(Boolean.class, new BooleanStringConverter());
		converter_map.put(boolean.class, new BooleanStringConverter());

		converter_map.put(Character.class, new CharacterStringConverter());
		converter_map.put(char.class, new CharacterStringConverter());

		converter_map.put(Date.class, new StringConverter<Date>() {
			private DateFormat[] formats = new DateFormat[] { //
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"), //
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), //
				new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"), //
				new SimpleDateFormat("yyyy/dd/MM HH:mm:ss"), //
				new SimpleDateFormat("yy-M-d H:m:s"), //
				new SimpleDateFormat("yy/M/d H:m:s"), //
				new SimpleDateFormat("yy年M月d日H:m:s"), //
				new SimpleDateFormat("yy/d/M H:m:s"), //
				new SimpleDateFormat("yyyy-MM-dd'Y'HH:mm:ss'Z'"), //
				new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒"), //
				new SimpleDateFormat("yyyy-MM-dd"), //
				new SimpleDateFormat("yyyy年MM月dd日"), //
				new SimpleDateFormat("yyyy/MM/dd"), //
				new SimpleDateFormat("yyyy/dd/MM"), //
				new SimpleDateFormat("yy-M-d"), //
				new SimpleDateFormat("yy/M/d"), //
				new SimpleDateFormat("yy年M月d日"), //
				new SimpleDateFormat("HH:mm:ss.SSS"), //
				new SimpleDateFormat("H:m:s"), //
				new SimpleDateFormat("H时m分s秒") //
			};

			public String toString(Date object) {
				return Objects.toString(object);
			}

			public Date fromString(String string) {
				if(StringUtil.isBlank(string)) {
					return null;
				}
				for (DateFormat format : formats) {
					try {
						return format.parse(string);
					} catch (Exception e) {
					}
				}
				throw new RuntimeException("Unparseable date: \"" + string + "\"");
			}
		});
		converter_map.put(java.math.BigDecimal.class, new BigDecimalStringConverter());
		converter_map.put(java.math.BigInteger.class, new BigIntegerStringConverter());

	};

	@SuppressWarnings("unchecked")
	public static <T> T cast(String value, Class<T> clazz) {
		return (T) Optional.ofNullable(instance.converter_map).map(v -> v.get(clazz))//
			.map(v -> v.fromString(value)).orElse(null);
	}

	public static long castToLongValue(String value) {
		return Optional.ofNullable(cast(value, Long.class)).orElse(0L);
	}

	public static Long castToLong(String value) {
		return Optional.ofNullable(cast(value, Long.class)).orElse(null);
	}

	public static int castToIntValue(String value) {
		return Optional.ofNullable(cast(value, Integer.class)).orElse(0);
	}

	public static Integer castToInt(String value) {
		return Optional.ofNullable(cast(value, Integer.class)).orElse(null);
	}

	public static short castToShortValue(String value) {
		return Optional.ofNullable(cast(value, Short.class)).orElse((short) 0);
	}

	public static Short castToShort(String value) {
		return Optional.ofNullable(cast(value, Short.class)).orElse(null);
	}

	public static byte castToByteValue(String value) {
		return Optional.ofNullable(cast(value, Byte.class)).orElse((byte) 0);
	}

	public static Byte castToByte(String value) {
		return Optional.ofNullable(cast(value, Byte.class)).orElse(null);
	}

	public static double castToDoubleValue(String value) {
		return Optional.ofNullable(cast(value, Double.class)).orElse(0.0);
	}

	public static Double castToDouble(String value) {
		return Optional.ofNullable(cast(value, Double.class)).orElse(null);
	}

	public static float castToFloatValue(String value) {
		return Optional.ofNullable(cast(value, Float.class)).orElse(0.0f);
	}

	public static Float castToFloat(String value) {
		return Optional.ofNullable(cast(value, Float.class)).orElse(null);
	}

	public static boolean castToBoolValue(String value) {
		return Optional.ofNullable(cast(value, Boolean.class)).orElse(false);
	}

	public static Boolean castToBool(String value) {
		return Optional.ofNullable(cast(value, Boolean.class)).orElse(null);
	}

	public static Date castToDate(String value) {
		return Optional.ofNullable(cast(value, Date.class)).orElse(null);
	}
}
