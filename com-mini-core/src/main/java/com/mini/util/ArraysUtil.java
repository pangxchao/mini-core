package com.mini.util;

import java.util.*;
import java.util.stream.Stream;

/**
 * 集合， 数组 工具类
 * @author XChao
 */
public final class ArraysUtil {


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
    public static <T> Stream<T> stream(Iterator<T> array) {
        ArrayList<T> list = new ArrayList<>();
        array.forEachRemaining(list::add);
        return list.stream();
    }

    /**
     * 将 Iterable 转换成 Stream 对象
     * @param array 数据对象
     * @param <T>   数据类型
     * @return Stream 对象
     */
    public static <T> Stream<T> stream(Iterable<T> array) {
        return stream(array.iterator());
    }

    /**
     * 将 Iterable 转换成 Stream 对象
     * @param array 数据对象
     * @param <T>   数据类型
     * @return Stream 对象
     */
    public static <T> Stream<T> stream(Collection<T> array) {
        return array.stream();
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @param <T>   数组类型
     * @return Stream
     */
    public static <T> Stream<T> stream(T[] array) {
        return Arrays.stream(array);
    }

    /**
     * 将数据转换成 Stream 对象
     * @param array 数组对象
     * @return Stream
     */
    public static Stream<Long> stream(long[] array) {
        List<Long> list = new ArrayList<>();
        for (long t : array) list.add(t);
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

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static <T> T first(Iterator<T> array, Function.FR1<Boolean, T> func) {
        for (; array.hasNext(); ) {
            T t = array.next();
            if (func.apply(t)) {
                return t;
            }
        }
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static <T> T first(Iterable<T> array, Function.FR1<Boolean, T> func) {
        for (T t : array) if (func.apply(t)) return t;
        return null;
    }


    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static <T> T first(T[] array, Function.FR1<Boolean, T> func) {
        for (T t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Long first(long[] array, Function.FR1<Boolean, Long> func) {
        for (long t : array) if (func.apply(t)) return t;
        return null;
    }


    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Integer first(int[] array, Function.FR1<Boolean, Integer> func) {
        for (int t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Short first(short[] array, Function.FR1<Boolean, Short> func) {
        for (short t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Byte first(byte[] array, Function.FR1<Boolean, Byte> func) {
        for (byte t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Double first(double[] array, Function.FR1<Boolean, Double> func) {
        for (double t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Float first(float[] array, Function.FR1<Boolean, Float> func) {
        for (float t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Boolean first(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        for (boolean t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 查询符合条件的第一个对象
     * @param array 数据
     * @param func  回调
     * @return Null-没有符合条件的对象
     */
    public static Character first(char[] array, Function.FR1<Boolean, Character> func) {
        for (char t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static <T> boolean every(Iterator<T> array, Function.FR1<Boolean, T> func) {
        for (; array.hasNext(); ) {
            T t = array.next();
            if (!func.apply(t)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static <T> boolean every(Iterable<T> array, Function.FR1<Boolean, T> func) {
        for (T t : array) if (!func.apply(t)) return false;
        return true;
    }


    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static <T> boolean every(T[] array, Function.FR1<Boolean, T> func) {
        for (T t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(long[] array, Function.FR1<Boolean, Long> func) {
        for (long t : array) if (!func.apply(t)) return false;
        return true;
    }


    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(int[] array, Function.FR1<Boolean, Integer> func) {
        for (int t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(short[] array, Function.FR1<Boolean, Short> func) {
        for (short t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(byte[] array, Function.FR1<Boolean, Byte> func) {
        for (byte t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(double[] array, Function.FR1<Boolean, Double> func) {
        for (double t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(float[] array, Function.FR1<Boolean, Float> func) {
        for (float t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        for (boolean t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean every(char[] array, Function.FR1<Boolean, Character> func) {
        for (char t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static <T> boolean any(Iterator<T> array, Function.FR1<Boolean, T> func) {
        for (; array.hasNext(); ) {
            T t = array.next();
            if (func.apply(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static <T> boolean any(Iterable<T> array, Function.FR1<Boolean, T> func) {
        for (T t : array) if (func.apply(t)) return true;
        return false;
    }


    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static <T> boolean any(T[] array, Function.FR1<Boolean, T> func) {
        for (T t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(long[] array, Function.FR1<Boolean, Long> func) {
        for (long t : array) if (func.apply(t)) return true;
        return false;
    }


    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(int[] array, Function.FR1<Boolean, Integer> func) {
        for (int t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(short[] array, Function.FR1<Boolean, Short> func) {
        for (short t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(byte[] array, Function.FR1<Boolean, Byte> func) {
        for (byte t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(double[] array, Function.FR1<Boolean, Double> func) {
        for (double t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(float[] array, Function.FR1<Boolean, Float> func) {
        for (float t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        for (boolean t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     * @return true-是
     */
    public static boolean any(char[] array, Function.FR1<Boolean, Character> func) {
        for (char t : array) if (func.apply(t)) return true;
        return false;
    }


    /////////////////////////////////////

    /**
     * 是否任意一个符合条件
     * @param array 数据
     * @param func  回调
     */
    public static <T> void forEach(Iterator<T> array, Function.F1<T> func) {
        while (array.hasNext()) func.apply(array.next());
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static <T> void forEach(Iterable<T> array, Function.F1<T> func) {
        for (T t : array) func.apply(t);
    }


    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static <T> void forEach(T[] array, Function.F1<T> func) {
        for (T t : array) func.apply(t);
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(long[] array, Function.F1<Long> func) {
        for (long t : array) func.apply(t);
    }


    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(int[] array, Function.F1<Integer> func) {
        for (int t : array) func.apply(t);
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(short[] array, Function.F1<Short> func) {
        for (short t : array) func.apply(t);
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(byte[] array, Function.F1<Byte> func) {
        for (byte t : array) func.apply(t);
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(double[] array, Function.F1<Double> func) {
        for (double t : array) func.apply(t);
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(float[] array, Function.F1<Float> func) {
        for (float t : array) func.apply(t);
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(boolean[] array, Function.F1<Boolean> func) {
        for (boolean t : array) func.apply(t);
    }

    /**
     * 是否每一个都符合条件
     * @param array 数据
     * @param func  回调
     */
    public static void forEach(char[] array, Function.F1<Character> func) {
        for (char t : array) func.apply(t);
    }
}
