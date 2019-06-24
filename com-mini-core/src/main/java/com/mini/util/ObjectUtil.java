package com.mini.util;

/**
 * ObjectUtil.java
 * @author xchao
 */
public final class ObjectUtil {

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
