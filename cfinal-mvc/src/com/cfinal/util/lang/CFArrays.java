/**
 * Created the com.cfinal.util.lang.CFArrays.java
 * @created 2017年5月24日 下午6:16:43
 * @version 1.0.0
 */
package com.cfinal.util.lang;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 集合， 数组 工具类
 * @author XChao
 */
public class CFArrays {

	/**
	 * 数组连接
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
	 * @param array
	 * @param params
	 * @return
	 */
	public static List<Object> array2list(Object[] array, Object... params) {
		List<Object> result = new ArrayList<Object>();
		result.addAll(Arrays.asList(array));
		result.addAll(Arrays.asList(params));
		return result;
	}
}
