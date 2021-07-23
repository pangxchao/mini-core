package com.mini.core.util;

import java.util.Collection;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.System.arraycopy;

public final class Utils {

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T hidden(Throwable throwable) throws T {
        throw (T) throwable;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Integer[] toObject(int[] array) {
        final Integer[] result = new Integer[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Long[] toObject(long[] array) {
        final Long[] result = new Long[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Byte[] toObject(byte[] array) {
        final Byte[] result = new Byte[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Short[] toObject(short[] array) {
        final Short[] result = new Short[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Float[] toObject(float[] array) {
        final Float[] result = new Float[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Double[] toObject(double[] array) {
        final Double[] result = new Double[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Character[] toObject(char[] array) {
        final Character[] result = new Character[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Boolean[] toObject(boolean[] array) {
        final Boolean[] result = new Boolean[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static int[] toPrimitive(Integer[] array) {
        final int[] result = new int[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static long[] toPrimitive(Long[] array) {
        final long[] result = new long[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static byte[] toPrimitive(Byte[] array) {
        final byte[] result = new byte[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static short[] toPrimitive(Short[] array) {
        final short[] result = new short[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static float[] toPrimitive(Float[] array) {
        final float[] result = new float[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static double[] toPrimitive(Double[] array) {
        final double[] result = new double[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static char[] toPrimitive(Character[] array) {
        final char[] result = new char[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static boolean[] toPrimitive(Boolean[] array) {
        final boolean[] result = new boolean[array.length];
        arraycopy(array, 0, result, 0, array.length);
        return result;
    }

}
