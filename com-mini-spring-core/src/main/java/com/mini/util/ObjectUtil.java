package com.mini.util;

/**
 * ObjectUtil.java
 * @author xchao
 */
public final class ObjectUtil {

    public static <T> T defIfNull(T v, T def) {
        return v == null ? def : v;
    }

    public static <T> T defIfNull(T v, Function.FR0<T> func)  {
        return v == null ? func.apply() : v;
    }

    public static void require(boolean bool) {
        if (!bool) throw new RuntimeException();
    }

    public static void require(boolean bool, String message) {
        if (!bool) throw new RuntimeException(message);
    }

    public static String toString(Object object) {
        return object == null ? null : object.toString();
    }
}
