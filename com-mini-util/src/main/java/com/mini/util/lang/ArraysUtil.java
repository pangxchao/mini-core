package com.mini.util.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 集合， 数组 工具类
 * @author XChao
 */
public class ArraysUtil {


    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static <T> T[] concat(T[] src, T[] dest) {
        if (src == null || dest == null) return null;
        List<T> list = new ArrayList<>();
        Collections.addAll(list, src);
        Collections.addAll(list, dest);
        return list.toArray(src);
    }

    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static long[] concat(long[] src, long[] dest) {
        if (src == null || dest == null) return null;
        long[] result = new long[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static int[] concat(int[] src, int[] dest) {
        if (src == null || dest == null) return null;
        int[] result = new int[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }


    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static short[] concat(short[] src, short[] dest) {
        if (src == null || dest == null) return null;
        short[] result = new short[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static byte[] concat(byte[] src, byte[] dest) {
        if (src == null || dest == null) return null;
        byte[] result = new byte[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static double[] concat(double[] src, double[] dest) {
        if (src == null || dest == null) return null;
        double[] result = new double[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static float[] concat(float[] src, float[] dest) {
        if (src == null || dest == null) return null;
        float[] result = new float[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static boolean[] concat(boolean[] src, boolean[] dest) {
        if (src == null || dest == null) return null;
        boolean[] result = new boolean[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     * @param src  源数组
     * @param dest 目标数组
     * @return 连接后的数组
     */
    public static char[] concat(char[] src, char[] dest) {
        if (src == null || dest == null) return null;
        char[] result = new char[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 将 Iterable 转换成 Stream 对象
     * @param array 数据对象
     * @param <T>   数据类型
     * @return Stream 对象
     */
    public static <T> Stream<T> stream(Iterable<T> array) {
        List<T> list = new ArrayList<>();
        for (T t : array) list.add(t);
        return list.stream();
    }

    /**
     * 将 Iterable 转换成 Stream 对象
     * @param array 数据对象
     * @param <T>   数据类型
     * @return Stream 对象
     */
    public static <T> Stream<T> stream(Iterator<T> array) {
        List<T> list = new ArrayList<>();
        while (array.hasNext()) {
            list.add(array.next());
        }
        return list.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @param <T>   数组类型
     * @return Stream
     */
    public static <T> Stream<T> stream(T[] array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list.stream();
    }


    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Integer> stream(int[] array) {
        List<Integer> list = new ArrayList<>();
        for (int t : array) list.add(t);
        return list.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Short> stream(short[] array) {
        List<Short> list = new ArrayList<>();
        for (short t : array) list.add(t);
        return list.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Byte> stream(byte[] array) {
        List<Byte> list = new ArrayList<>();
        for (byte t : array) list.add(t);
        return list.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Double> stream(double[] array) {
        List<Double> list = new ArrayList<>();
        for (double t : array) list.add(t);
        return list.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Float> stream(float[] array) {
        List<Float> list = new ArrayList<>();
        for (float t : array) list.add(t);
        return list.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Boolean> stream(boolean[] array) {
        List<Boolean> list = new ArrayList<>();
        for (boolean t : array) list.add(t);
        return list.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Character> stream(char[] array) {
        List<Character> list = new ArrayList<>();
        for (char t : array) list.add(t);
        return list.stream();
    }
}
