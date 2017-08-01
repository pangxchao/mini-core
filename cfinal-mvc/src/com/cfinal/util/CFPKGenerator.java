/**
 * Created com.cfinal.util.CFPKGenerator.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package com.cfinal.util;

import java.util.Random;
import java.util.UUID;

import com.cfinal.web.central.CFInitialize;

/**
 * 主键获取,规则：当前时间缀转36进制字符串 + 两位36进制IP码 + 一位36进制随机码
 * @author XChao
 */
public class CFPKGenerator {
	private static final long BASE_TIME = 1451606400000L;
	private static final CFPKGenerator INSTENCE = new CFPKGenerator();
	private final static char[] DIGITS = "0123456789BCDEFGHJKMNPQRSTUVWXYZ".toCharArray();

	private long workerid = 0L;
	private long sequence = 0L;
	private long lasttimestamp = -1L;

	private CFPKGenerator() {
		try {
			this.workerid = CFInitialize.getContext().getWorkerId();
		} catch (Exception e) {
			workerid = (new Random().nextInt(16) + 1);
		}
	}

	// 生成主键
	private synchronized long generate() {
		if(sequence >= 0x40000) {
			sequence = 0;
		}
		long timestamp = System.currentTimeMillis();
		while (sequence == 0 && lasttimestamp > timestamp) {
			lasttimestamp = timestamp;
		}
		long val = ((0x7fffffffffffffffL & (timestamp - BASE_TIME) << 22));
		return val | ((sequence++ & 0x4FFFF) << 4) | ((workerid & 0xf));
	}

	// 根据主键获取生成主键时的时间缀
	private long sequence(long generate) {
		return ((generate & 0xFFFFFFFFFFC00000L) >> 22) + BASE_TIME;
	}

	public static void setWorkerid(long workerid) {
		INSTENCE.workerid = workerid;
	}

	/**
	 * 根据主键获取生成主键生成时的时间缀
	 * @param key
	 * @return
	 */
	public static long millis(long key) {
		return INSTENCE.sequence(key);
	}

	/**
	 * 生成主键
	 * @return
	 */
	public static long key() {
		return INSTENCE.generate();
	}

	/**
	 * 生成一个UUID 替换掉"-"
	 * @return
	 */
	public static String uuid() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "").toUpperCase();
	}

	/**
	 * 生成length位长度的纯数字的随机字符串
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
