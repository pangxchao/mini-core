package com.mini.util;

import java.util.Random;

import static java.util.UUID.randomUUID;

/**
 * 主键获取,规则：当前时间缀转36进制字符串 + 两位36进制IP码 + 一位36进制随机码
 * @author XChao
 */
public final class PKGenerator {
    private static final PKGenerator INSTANCE = new PKGenerator();
    private static final Random RANDOM = new Random();
    private final static char[] DIGITS = new char[]{        //
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',//
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',//
            'k', 'm', 'n', 'p', 'q', 'r', 's', 't',//
            'u', 'v', 'w', 'x', 'y', 'z'};
    private static final long BASE_TIME = 1451606400000L;
    // 时间戳部分长度42位，设置一个基础时间戳，中以保证60年不重复/64 位全是1
    private static final long MAX = 0x7fffffffffffffffL;
    // 44位1，22位0
    private static final long MAX_TIME = 0xFFFFFFFFFFC00000L;
    // 自动增长序列部分，16位长度，每毫秒可以生成65535个不同ID，// 16位全是1
    private static final long MAX_SEQUENCE = 0xffff;
    // 机器码6位长度，可以同时集群63台机器，// 6位全是1
    private static final long MAX_WORK = 0x3f;

    private long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private PKGenerator() {}

    public static void setWorkerId(long workerId) {
        INSTANCE.workerId = workerId;
    }

    /**
     * 根据主键获取ID中的时间戳
     * @param id 主键
     * @return 时间戳
     */
    public static long millis(long id) {
        return INSTANCE.sequence(id);
    }

    /**
     * 生成主键
     * @return 主键
     */
    public static long id() {
        return INSTANCE.generate();
    }

    /**
     * 生成一个UUID 替换掉"-"
     * @return UUID
     */
    public static String uuid() {
        String uuid = randomUUID().toString();
        return uuid.replace("-", "");
    }

    /**
     * 获取一个随机数
     * @param bound 最大限制
     * @return 随机数
     */
    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    /**
     * 获取一个随机字符
     * @return 随机字符
     */
    public static char nextChar() {
        int length = DIGITS.length;
        return DIGITS[nextInt(length)];
    }

    /**
     * 生成length位长度的纯数字的随机字符串
     * @param length 长度
     * @return 随机数字
     */
    public static String number(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(nextInt(10));
        }
        return result.toString();
    }

    /**
     * 生成length位长度的字母加数据的随机字符串
     * @param length 长度
     * @return 随机字符
     */
    public static String random(int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(nextChar());
        }
        return result.toString();
    }

    /**
     * 重新创建用户token
     * @param id 用户ID
     * @return 根据用户ID生成的用户TOKEN
     */
    public static String token(long id) {
        return Long.toHexString(id);
    }

    /**
     * 解码token 获取用户 uid
     * @param token 用户TOKEN
     * @return 从TOKEN中获取的用户ID
     */
    public static long decode(String token) {
        try {
            return Long.valueOf(token, 16);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 生成主键
     * @return 随机主键
     */
    private synchronized long generate() {
        if (sequence > MAX_SEQUENCE) {
            sequence = 0;
        }
        long timestamp = System.currentTimeMillis();
        while (sequence == 0 && lastTimestamp > timestamp) {
            lastTimestamp = timestamp;
        }
        long val = ((MAX & (timestamp - BASE_TIME) << 22));
        val = val | ((sequence++ & MAX_SEQUENCE) << 6);
        return val | ((workerId & MAX_WORK));
    }

    /**
     * 根据主键获取生成主键时的时间缀
     * @param generate 主键
     * @return 时间戳
     */
    private long sequence(long generate) {
        return ((generate & MAX_TIME) >> 22) + BASE_TIME;
    }
}
