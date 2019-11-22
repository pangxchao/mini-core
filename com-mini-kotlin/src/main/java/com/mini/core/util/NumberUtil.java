package com.mini.core.util;

public final class NumberUtil {
    /**
     * 判断等于
     * @return a == b
     */
    public static boolean equals(float a, float b) {
        return Float.floatToIntBits(a) == Float.floatToIntBits(b);
    }

    /**
     * 判断大于
     * @return a > b
     */
    public static boolean gt(float a, float b) {
        return Float.floatToIntBits(a) > Float.floatToIntBits(b);
    }

    /**
     * 判断大于等于
     * @return a >= b
     */

    public static boolean gte(float a, float b) {
        return Float.floatToIntBits(a) >= Float.floatToIntBits(b);
    }

    /**
     * 判断大于
     * @return a < b
     */
    public static boolean lt(float a, float b) {
        return Float.floatToIntBits(a) < Float.floatToIntBits(b);
    }

    /**
     * 判断大于等于
     * @return a <= b
     */

    public static boolean lte(float a, float b) {
        return Float.floatToIntBits(a) <= Float.floatToIntBits(b);
    }

    /**
     * 判断等于
     * @return a == b
     */
    public static boolean equals(double a, double b) {
        return Double.doubleToLongBits(a) == Double.doubleToLongBits(b);
    }

    /**
     * 判断大于
     * @return a > b
     */
    public static boolean gt(double a, double b) {
        return Double.doubleToLongBits(a) > Double.doubleToLongBits(b);
    }

    /**
     * 判断大于等于
     * @return a >= b
     */

    public static boolean gte(double a, double b) {
        return Double.doubleToLongBits(a) >= Double.doubleToLongBits(b);
    }

    /**
     * 判断大于
     * @return a < b
     */
    public static boolean lt(double a, double b) {
        return Double.doubleToLongBits(a) < Double.doubleToLongBits(b);
    }

    /**
     * 判断大于等于
     * @return a <= b
     */

    public static boolean lte(double a, double b) {
        return Double.doubleToLongBits(a) <= Double.doubleToLongBits(b);
    }
}
