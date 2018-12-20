package com.mini.util.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    @SuppressWarnings("unchecked")
    public static <T> T[] concat(T[] src, T[] dest) {
        if (src == null) return dest;
        if (dest == null) return src;
        Class<T> clazz = (Class<T>) src.getClass().getComponentType();
        T[] result = (T[]) Array.newInstance(clazz, src.length + dest.length);
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
        if (src == null) return dest;
        if (dest == null) return src;
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
    public static int[] concat(int[] src, int[] dest) {
        if (src == null) return dest;
        if (dest == null) return src;
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
    public static short[] concat(short[] src, short[] dest) {
        if (src == null) return dest;
        if (dest == null) return src;
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
        if (src == null) return dest;
        if (dest == null) return src;
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
        if (src == null) return dest;
        if (dest == null) return src;
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
        if (src == null) return dest;
        if (dest == null) return src;
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
    public static boolean[] concat(boolean[] src, boolean[] dest) {
        if (src == null) return dest;
        if (dest == null) return src;
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
    public static char[] concat(char[] src, char[] dest) {
        char[] result = new char[src.length + dest.length];
        System.arraycopy(src, 0, result, 0, src.length);
        System.arraycopy(dest, 0, result, src.length, dest.length);
        return result;
    }


    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> boolean every(Iterable<T> array, Function.FR1<Boolean, T> func) {
        if (array == null) return false;
        for (T t : array) if (!func.apply(t)) return false;
        return true;
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
        if (array == null) return false;
        for (T t : array) if (!func.apply(t)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(long[] array, Function.FR1<Boolean, Long> func) {
        if (array == null) return false;
        for (long i : array) if (!func.apply(i)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(int[] array, Function.FR1<Boolean, Integer> func) {
        if (array == null) return false;
        for (int i : array) if (!func.apply(i)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(short[] array, Function.FR1<Boolean, Short> func) {
        if (array == null) return false;
        for (short i : array) if (!func.apply(i)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(byte[] array, Function.FR1<Boolean, Byte> func) {
        if (array == null) return false;
        for (byte i : array) if (!func.apply(i)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(double[] array, Function.FR1<Boolean, Double> func) {
        if (array == null) return false;
        for (double i : array) if (!func.apply(i)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(float[] array, Function.FR1<Boolean, Float> func) {
        if (array == null) return false;
        for (float i : array) if (!func.apply(i)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        if (array == null) return false;
        for (boolean i : array) if (!func.apply(i)) return false;
        return true;
    }

    /**
     * 所有的都为true时返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean every(char[] array, Function.FR1<Boolean, Character> func) {
        if (array == null) return false;
        for (char i : array) if (!func.apply(i)) return false;
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
    public static <T> boolean any(Iterable<T> array, Function.FR1<Boolean, T> func) {
        if (array == null) return false;
        for (T t : array) if (func.apply(t)) return true;
        return false;
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
        if (array == null) return false;
        for (T t : array) if (func.apply(t)) return true;
        return false;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(long[] array, Function.FR1<Boolean, Long> func) {
        if (array == null) return false;
        for (long i : array) if (func.apply(i)) return true;
        return false;
    }


    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(int[] array, Function.FR1<Boolean, Integer> func) {
        if (array == null) return false;
        for (int i : array) if (func.apply(i)) return true;
        return false;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(short[] array, Function.FR1<Boolean, Short> func) {
        if (array == null) return false;
        for (short i : array) if (func.apply(i)) return true;
        return false;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(byte[] array, Function.FR1<Boolean, Byte> func) {
        if (array == null) return false;
        for (byte i : array) if (func.apply(i)) return true;
        return false;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(double[] array, Function.FR1<Boolean, Double> func) {
        if (array == null) return false;
        for (double i : array) if (func.apply(i)) return true;
        return false;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(float[] array, Function.FR1<Boolean, Float> func) {
        if (array == null) return false;
        for (float i : array) if (func.apply(i)) return true;
        return false;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        if (array == null) return false;
        for (boolean i : array) if (func.apply(i)) return true;
        return false;
    }

    /**
     * 其中一个为true时都返回true
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean any(char[] array, Function.FR1<Boolean, Character> func) {
        if (array == null) return false;
        for (char i : array) if (func.apply(i)) return true;
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
    public static <R, T> ArrayList<R> map(Iterable<T> array, Function.FR1<R, T> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (T t : array) result.add(func.apply(t));
        return result;
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
    public static <R, T> ArrayList<R> map(T[] array, Function.FR1<R, T> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (T t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(long[] array, Function.FR1<R, Long> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (long t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(int[] array, Function.FR1<R, Integer> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (int t : array) result.add(func.apply(t));
        return result;
    }


    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(short[] array, Function.FR1<R, Short> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (short t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(byte[] array, Function.FR1<R, Byte> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (byte t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(double[] array, Function.FR1<R, Double> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (double t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(float[] array, Function.FR1<R, Float> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (float t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(boolean[] array, Function.FR1<R, Boolean> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (boolean t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 更改集合元素结构
     *
     * @param array
     * @param func
     * @param <R>
     * @return
     */
    public static <R> ArrayList<R> map(char[] array, Function.FR1<R, Character> func) {
        if (array == null) return null;
        ArrayList<R> result = new ArrayList<>();
        for (char t : array) result.add(func.apply(t));
        return result;
    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> filter(Iterable<T> array, Function.FR1<Boolean, T> func) {
        if (array == null) return null;
        ArrayList<T> result = new ArrayList<>();
        for (T t : array) if (func.apply(t)) result.add(t);
        return result;

    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> filter(T[] array, Function.FR1<Boolean, T> func) {
        if (array == null) return null;
        ArrayList<T> result = new ArrayList<>();
        for (T t : array) if (func.apply(t)) result.add(t);
        return result;
    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @return
     */
    public static ArrayList<Long> filter(long[] array, Function.FR1<Boolean, Long> func) {
        if (array == null) return null;
        ArrayList<Long> result = new ArrayList<>();
        for (long t : array) if (func.apply(t)) result.add(t);
        return result;
    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @return
     */
    public static ArrayList<Integer> filter(int[] array, Function.FR1<Boolean, Integer> func) {
        if (array == null) return null;
        ArrayList<Integer> result = new ArrayList<>();
        for (int t : array) if (func.apply(t)) result.add(t);
        return result;
    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @return
     */
    public static ArrayList<Short> filter(short[] array, Function.FR1<Boolean, Short> func) {
        if (array == null) return null;
        ArrayList<Short> result = new ArrayList<>();
        for (short t : array) if (func.apply(t)) result.add(t);
        return result;
    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @return
     */
    public static ArrayList<Byte> filter(byte[] array, Function.FR1<Boolean, Byte> func) {
        if (array == null) return null;
        ArrayList<Byte> result = new ArrayList<>();
        for (byte t : array) if (func.apply(t)) result.add(t);
        return result;
    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @return
     */
    public static ArrayList<Boolean> filter(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        if (array == null) return null;
        ArrayList<Boolean> result = new ArrayList<>();
        for (boolean t : array) if (func.apply(t)) result.add(t);
        return result;
    }

    /**
     * 过虑
     *
     * @param array
     * @param func
     * @return
     */
    public static ArrayList<Character> filter(char[] array, Function.FR1<Boolean, Character> func) {
        if (array == null) return null;
        ArrayList<Character> result = new ArrayList<>();
        for (char t : array) if (func.apply(t)) result.add(t);
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
    public static <T> List<T> sort(List<T> array, Function.FR2<Integer, T, T> func) {
        if (array == null) return null;
        for (int i = 0, l = array.size(); i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                T ti = array.get(i), tj = array.get(j);
                if (func.apply(ti, tj) > 0) {
                    array.set(i, tj);
                    array.set(j, ti);
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> T[] sort(T[] array, Function.FR2<Integer, T, T> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                T ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static long[] sort(long[] array, Function.FR2<Integer, Long, Long> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                long ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static int[] sort(int[] array, Function.FR2<Integer, Integer, Integer> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                int ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static short[] sort(short[] array, Function.FR2<Integer, Short, Short> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                short ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static byte[] sort(byte[] array, Function.FR2<Integer, Byte, Byte> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                byte ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static double[] sort(double[] array, Function.FR2<Integer, Double, Double> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                double ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static float[] sort(float[] array, Function.FR2<Integer, Float, Float> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                float ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean[] sort(boolean[] array, Function.FR2<Integer, Boolean, Boolean> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                boolean ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 排序
     *
     * @param array
     * @param func
     * @return
     */
    public static char[] sort(char[] array, Function.FR2<Integer, Character, Character> func) {
        if (array == null) return null;
        for (int i = 0, l = array.length; i < l; i++) {
            for (int j = i + 1; j < l; j++) {
                char ti = array[i], tj = array[j];
                if (func.apply(ti, tj) > 0) {
                    array[i] = tj;
                    array[j] = ti;
                }
            }
        }
        return array;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> T first(Iterable<T> array, Function.FR1<Boolean, T> func) {
        if (array == null) return null;
        for (T t : array) if (func.apply(t)) return t;
        return null;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> T first(T[] array, Function.FR1<Boolean, T> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return null;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static long first(long[] array, Function.FR1<Boolean, Long> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return 0;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static int first(int[] array, Function.FR1<Boolean, Integer> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return 0;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static short first(short[] array, Function.FR1<Boolean, Short> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return (short) 0;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static byte first(byte[] array, Function.FR1<Boolean, Byte> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return (byte) 0;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static double first(double[] array, Function.FR1<Boolean, Double> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return 0D;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static float first(float[] array, Function.FR1<Boolean, Float> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return 0F;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static boolean first(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return false;
    }

    /**
     * 满足条件的第一个元素
     *
     * @param array
     * @param func
     * @return
     */
    public static char first(char[] array, Function.FR1<Boolean, Character> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return array[i];
        }
        return ' ';
    }


    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> void each(Iterable<T> array, Function.FR1<Boolean, T> func) {
        if (array != null) for (T t : array) if (!func.apply(t)) return;
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @param <T>
     * @return
     */
    public static <T> void each(T[] array, Function.FR1<Boolean, T> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (!func.apply(array[i])) return;
        }
    }


    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(long[] array, Function.FR1<Boolean, Long> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(int[] array, Function.FR1<Boolean, Integer> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(short[] array, Function.FR1<Boolean, Short> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(byte[] array, Function.FR1<Boolean, Byte> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(double[] array, Function.FR1<Boolean, Double> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(float[] array, Function.FR1<Boolean, Float> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(boolean[] array, Function.FR1<Boolean, Boolean> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }

    /**
     * 遍历数据/集合内容
     *
     * @param array
     * @param func
     * @return
     */
    public static void each(char[] array, Function.FR1<Boolean, Character> func) {
        for (int i = 0; array != null && i < array.length; i++) {
            if (func.apply(array[i])) return;
        }
    }
}
