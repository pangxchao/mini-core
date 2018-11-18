package com.mini.util.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合， 数组 工具类
 *
 * @author XChao
 */
public class ArraysUtil {

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static int[] concat(int[] src, int[] dest) {
        int[] result = new int[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static long[] concat(long[] src, long[] dest) {
        long[] result = new long[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static short[] concat(short[] src, short[] dest) {
        short[] result = new short[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static byte[] concat(byte[] src, byte[] dest) {
        byte[] result = new byte[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static double[] concat(double[] src, double[] dest) {
        double[] result = new double[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static float[] concat(float[] src, float[] dest) {
        float[] result = new float[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static char[] concat(char[] src, char[] dest) {
        char[] result = new char[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    public static boolean[] concat(boolean[] src, boolean[] dest) {
        boolean[] result = new boolean[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 数组连接
     *
     * @param src
     * @param dest
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] concat(T[] src, T[] dest) {
        Class<T> clazz = (Class<T>) src.getClass().getComponentType();
        T[] result = (T[]) Array.newInstance(clazz, src.length + dest.length);
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> arrayToList(T[] array, T... params) {
        List<T> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Long> arrayToList(long[] array, long... params) {
        List<Long> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Integer> arrayToList(int[] array, int... params) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Short> arrayToList(short[] array, short... params) {
        List<Short> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Byte> arrayToList(byte[] array, byte... params) {
        List<Byte> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Double> arrayToList(double[] array, double... params) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Float> arrayToList(float[] array, float... params) {
        List<Float> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Boolean> arrayToList(boolean[] array, boolean... params) {
        List<Boolean> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 将数组类型转换成为List类型
     *
     * @param array
     * @param params
     * @return
     */
    public static List<Character> arrayToList(char[] array, char... params) {
        List<Character> result = new ArrayList<>();
        for (int i = 0; array != null && i < array.length; i++) {
            result.add(array[i]);
        }
        for (int i = 0; params != null && i < params.length; i++) {
            result.add(params[i]);
        }
        return result;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param collectionList
     * @param func
     * @param <T>
     * @return
     */
    public static <T> boolean every(Collection<T> collectionList, Function.FR1<Boolean, T> func) {
        for (T collection : collectionList) {
            if (!func.apply(collection)) return false;
        }
        return true;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param collectionList
     * @param func
     * @param <T>
     * @return
     */
    public static <T> boolean any(Collection<T> collectionList, Function.FR1<Boolean, T> func) {
        for (T collection : collectionList) {
            if (func.apply(collection)) return true;
        }
        return false;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> boolean every(T[] array, Function.FR1<Boolean, T> func) {
        for (T collection : array) {
            if (!func.apply(collection)) return false;
        }
        return true;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> boolean any(T[] array, Function.FR1<Boolean, T> func) {
        for (T collection : array) {
            if (func.apply(collection)) return true;
        }
        return false;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(int[] array, Function.FR1<Boolean, Integer> func) {
        for (int collection : array) {
            if (!func.apply(collection)) return false;
        }
        return true;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(int[] array, Function.FR1<Boolean, Integer> func) {
        for (int collection : array) {
            if (func.apply(collection)) return true;
        }
        return false;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T> List<R> map(Collection<T> array, Function.FR1<R, T> func) {
        List<R> result = new ArrayList<>();
        for (T collection : array) {
            result.add(func.apply(collection));
        }
        return result;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> List<T> sort(Collection<T> array, Function.FR2<Integer, T, T> func) {
        List<T> result = map(array, value -> value);
        for (int i = 0, l = result.size(); i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                T temp_i = result.get(i), temp_j = result.get(j);
                if (func.apply(temp_i, temp_j) > 0) {
                    result.set(j, temp_i);
                    result.set(i, temp_j);
                }
            }
        }
        return result;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @param <T>
     */
    public static <T> void sortList(List<T> array, Function.FR2<Integer, T, T> func) {
        for (int i = 0, l = array.size(); i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                T temp_i = array.get(i), temp_j = array.get(j);
                if (func.apply(temp_i, temp_j) > 0) {
                    array.set(j, temp_i);
                    array.set(i, temp_j);
                }
            }
        }
    }

}
