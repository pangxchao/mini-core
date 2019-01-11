package com.mini.util;

import java.util.Random;
import java.util.UUID;

/**
 * 	主键获取,规则：当前时间缀转36进制字符串 + 两位36进制IP码 + 一位36进制随机码
 * 
 * @author XChao
 */
public class PKGenerator {
	private static final long BASE_TIME = 1451606400000L;
	private static final PKGenerator INSTANCE = new PKGenerator();
	private final static char[] DIGITS = "0123456789BCDEFGHJKMNPQRSTUVWXYZ".toCharArray();

	private long workerId;
	private long sequence = 0L;
	private long timestamp = -1L;

	private PKGenerator() {
		workerId = (new Random().nextInt(16) + 1);
	}

	// 生成主键
	private synchronized long generate() {
		if (sequence > 0x3FFF) {
			sequence = 0;
		}
		long timestamp = System.currentTimeMillis();
		while (sequence == 0 && this.timestamp > timestamp) {
			this.timestamp = timestamp;
		}
		long val = ((0x7fffffffffffffffL & (timestamp - BASE_TIME) << 22));
		return val | ((sequence++ & 0x3FFF) << 8) | ((workerId & 0xff));
	}

	// 根据主键获取生成主键时的时间缀
	private long sequence(long generate) {
		return ((generate & 0xFFFFFFFFFFC00000L) >> 22) + BASE_TIME;
	}

	public static void setWorkerId(long workerId) {
		INSTANCE.workerId = workerId;
	}

	/**
	 * 根据主键获取生成主键生成时的时间缀
	 * 
	 * @param key
	 * @return
	 */
	public static long millis(long key) {
		return INSTANCE.sequence(key);
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public static long key() {
		return INSTANCE.generate();
	}

	/**
	 * 生成一个UUID 替换掉"-"
	 * 
	 * @return
	 */
	public static String uuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "").toUpperCase();
	}

	/**
	 * 生成length位长度的纯数字的随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String gennum(int length) {
		StringBuilder result = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			result.append(random.nextInt(10));
		}
		return result.toString();
	}

	/**
	 * 生成length位长度的字母加数据的随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String genseed(int length) {
		StringBuilder result = new StringBuilder();
		Random random = new Random();
		for (int i = 0, len = DIGITS.length; i < length; i++) {
			result.append(DIGITS[random.nextInt(len)]);
		}
		return result.toString();
	}
}
