package com.mini.util.lang;

/**
 * 类型转换
 * @author XChao
 */
public final class TypeUtil {
    public static long castToLongValue(String value) {
        return value == null ? 0 : Long.valueOf(value);
    }

    public static Long castToLong(String value) {
        return value == null ? null : Long.valueOf(value);
    }

    public static int castToIntValue(String value) {
        return value == null ? 0 : Integer.valueOf(value);
    }

    public static Integer castToInt(String value) {
        return value == null ? null : Integer.valueOf(value);
    }

    public static short castToShortValue(String value) {
        return value == null ? 0 : Short.valueOf(value);
    }

    public static Short castToShort(String value) {
        return value == null ? null : Short.valueOf(value);
    }

    public static byte castToByteValue(String value) {
        return value == null ? 0 : Byte.valueOf(value);
    }

    public static Byte castToByte(String value) {
        return value == null ? null : Byte.valueOf(value);
    }

    public static double castToDoubleValue(String value) {
        return value == null ? 0 : Double.valueOf(value);
    }

    public static Double castToDouble(String value) {
        return value == null ? null : Double.valueOf(value);
    }

    public static float castToFloatValue(String value) {
        return value == null ? 0f : Float.valueOf(value);
    }

    public static Float castToFloat(String value) {
        return value == null ? null : Float.valueOf(value);
    }

    public static boolean castToBoolValue(String value) {
        return value == null ? false : Boolean.valueOf(value);
    }

    public static Boolean castToBool(String value) {
        return value == null ? null : Boolean.valueOf(value);
    }
}
