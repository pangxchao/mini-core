package com.mini.core.util;

import com.google.common.base.Strings;
import kotlin.text.StringsKt;

import static com.mini.core.util.StringUtil.isBlank;

/**
 * 类型转换
 * @author XChao
 */
public final class TypeUtil {
    /**
     * 转换成 long 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static long castToLongVal(String value) {
        if (isBlank(value)) return 0L;
        return Long.parseLong(value);
    }

    /**
     * 转换成 Long 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Long castToLong(String value) {
        if (isBlank(value)) return null;
        return Long.valueOf(value);
    }

    /**
     * 转换成 int 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static int castToIntVal(String value) {
        if (isBlank(value)) return 0;
        return Integer.parseInt(value);
    }

    /**
     * 转换成 Integer 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Integer castToInt(String value) {
        if (isBlank(value)) return null;
        return Integer.valueOf(value);
    }

    /**
     * 转换成 short 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static short castToShortVal(String value) {
        if (isBlank(value)) return (short) 0;
        return Short.parseShort(value);
    }

    /**
     * 转换成 Short 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Short castToShort(String value) {
        if (isBlank(value)) return null;
        return Short.valueOf(value);
    }

    /**
     * 转换成 byte 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static byte castToByteVal(String value) {
        if (isBlank(value)) return (byte) 0;
        return Byte.parseByte(value);
    }

    /**
     * 转换成 Byte 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Byte castToByte(String value) {
        if (isBlank(value)) return null;
        return Byte.valueOf(value);
    }

    /**
     * 转换成 double 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static double castToDoubleVal(String value) {
        if (isBlank(value)) return 0.0D;
        return Double.parseDouble(value);
    }

    /**
     * 转换成 Double 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Double castToDouble(String value) {
        if (isBlank(value)) return null;
        return Double.valueOf(value);
    }

    /**
     * 转换成 float 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static float castToFloatVal(String value) {
        if (isBlank(value)) return 0.0F;
        return Float.parseFloat(value);
    }

    /**
     * 转换成 Float 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Float castToFloat(String value) {
        if (isBlank(value)) return null;
        return Float.valueOf(value);
    }

    /**
     * 转换成 boolean 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static boolean castToBoolVal(String value) {
        if (isBlank(value)) return false;
        return Boolean.parseBoolean(value);
    }

    /**
     * 转换成 Boolean 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Boolean castToBool(String value) {
        if (isBlank(value)) return null;
        return Boolean.valueOf(value);
    }

    /**
     * 转换成 boolean 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static char castToCharVal(String value) {
        if (isBlank(value)) return ' ';
        return value.charAt(0);
    }

    /**
     * 转换成 Boolean 类型
     * @param value 转换目标
     * @return 转换结果
     */
    public static Character castToChar(String value) {
        if (isBlank(value)) return null;
        return value.charAt(0);
    }
}
