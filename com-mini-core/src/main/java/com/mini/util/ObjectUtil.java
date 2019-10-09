package com.mini.util;

import java.util.function.Supplier;

/**
 * ObjectUtil.java
 * @author xchao
 */
public final class ObjectUtil {

	public static <T> T defIfNull(T v, T def) {
		return v == null ? def : v;
	}

	public static <T> T defIfNull(T v, Supplier<T> func) {
		return v == null ? func.get() : v;
	}

	public static void require(boolean bool) {
		if (!bool) throw new RuntimeException();
	}

	public static void require(boolean bool, String message) {
		if (!bool) throw new RuntimeException(message);
	}

	public static boolean equals(Object o, Object v) {
		return o != null && o.equals(v);
	}

	public static String toString(Object object) {
		return object == null ? null : object.toString();
	}

	public static void sendError(String message) {
		throw new RuntimeException(message);
	}
}
