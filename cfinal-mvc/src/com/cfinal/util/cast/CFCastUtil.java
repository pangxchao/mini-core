/**
 * Created the com.cfinal.util.cast.CFCast.java
 * @created 2017年2月25日 上午12:58:19
 * @version 1.0.0
 */
package com.cfinal.util.cast;

import java.util.HashMap;
import java.util.Map;

/**
 * com.cfinal.util.cast.CFCast.java
 * @author XChao
 */
public class CFCastUtil {
	private static final Map<Class<?>, CFCast> casts = new HashMap<>();
	private static final CFCast defaultCast = new CFDefaultCast();
	static {
		casts.put(byte.class, new CFByteCast());
		casts.put(Byte.class, new CFByteCast());

		casts.put(short.class, new CFShortCast());
		casts.put(Short.class, new CFShortCast());

		casts.put(int.class, new CFIntCast());
		casts.put(Integer.class, new CFIntCast());

		casts.put(long.class, new CFLongCast());
		casts.put(Long.class, new CFLongCast());

		casts.put(float.class, new CFFloatCast());
		casts.put(Float.class, new CFFloatCast());

		casts.put(double.class, new CFDoubleCast());
		casts.put(Double.class, new CFDoubleCast());

		casts.put(boolean.class, new CFBooleanCast());
		casts.put(Boolean.class, new CFBooleanCast());
	}

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object value, Class<T> clazz) {
		return (T) getCast(clazz).cast(value);
	}

	public static String castString(Object value) {
		return cast(value, String.class);
	}

	public static long castLong(Object value) {
		return cast(value, Long.class);
	}

	public static int castInt(Object value) {
		return cast(value, Integer.class);
	}

	public static short castShort(Object value) {
		return cast(value, Short.class);
	}

	public static byte castByte(Object value) {
		return cast(value, Byte.class);
	}

	public static double castDouble(Object value) {
		return cast(value, Double.class);
	}

	public static float castFloat(Object value) {
		return cast(value, Float.class);
	}

	public static boolean castBoolean(Object value) {
		return cast(value, Boolean.class);
	}

	private static CFCast getCast(Class<?> clazz) {
		CFCast cast = casts.get(clazz);
		if(cast == null) {
			return defaultCast;
		}
		return cast;
	}
}
