package com.mini.core.util;

import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class ArrayUtil extends ArrayUtils {
    private ArrayUtil() {
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static <T> List<T> asList(@Nullable T[] array) {
        if (Objects.isNull(array)) {
            return null;
        }
        return Arrays.asList(array);
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Long> asList(@Nullable long[] array) {
        return asList(toObject(array));
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Integer> asList(@Nullable int[] array) {
        return asList(toObject(array));
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Short> asList(@Nullable short[] array) {
        return asList(toObject(array));
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Byte> asList(@Nullable byte[] array) {
        return asList(toObject(array));
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Double> asList(@Nullable double[] array) {
        return asList(toObject(array));
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Float> asList(@Nullable float[] array) {
        return asList(toObject(array));
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Boolean> asList(@Nullable boolean[] array) {
        return asList(toObject(array));
    }

    /**
     * 数组转换成集合
     *
     * @param array 数组对象
     * @return 转换结果
     */
    @Nullable
    public static List<Character> asList(@Nullable char[] array) {
        return asList(toObject(array));
    }
}
