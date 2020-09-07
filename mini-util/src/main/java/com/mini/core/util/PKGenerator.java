package com.mini.core.util;


import java.util.Random;

import static java.util.UUID.randomUUID;

/**
 * 主键获取,规则：当前时间缀转36进制字符串 + 两位36进制IP码 + 一位36进制随机码
 *
 * @author XChao
 */
public final class PKGenerator {
    @SuppressWarnings("SpellCheckingInspection")
    private final static char[] DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final WorkerId workerId = new WorkerId();
    private static final Random RANDOM = new Random();

    /**
     * 设置主键机器码
     *
     * @param workId 机器码
     */
    public static void setWorkerId(long workId) {
        workerId.setWorkerId(workId);
    }

    /**
     * 生成主键
     *
     * @return 主键
     */
    public static synchronized long nextId() {
        return workerId.nextId();
    }

    /**
     * 生成主键
     *
     * @return 主键
     */
    public static synchronized long id() {
        return PKGenerator.nextId();
    }

    /**
     * 根据主键获取ID中的时间戳
     *
     * @param id 主键
     * @return 时间戳
     */
    public static long millis(long id) {
        return WorkerId.millis(id);
    }

    /**
     * 生成一个UUID 替换掉"-"
     *
     * @return UUID
     */
    public static String uuid() {
        String uuid = randomUUID().toString();
        return uuid.replace("-", "");
    }

    /**
     * 获取一个随机数
     *
     * @param bound 最大限制
     * @return 随机数
     */
    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    /**
     * 获取一个随机字符
     *
     * @return 随机数
     */
    public static char nextNum() {
        return DIGITS[nextInt(10)];
    }

    /**
     * 获取一个随机字符
     *
     * @return 随机字符
     */
    public static char nextChar() {
        int length = DIGITS.length;
        return DIGITS[nextInt(length)];
    }

    /**
     * 生成length位长度的纯数字的随机字符串
     *
     * @param length 长度
     * @return 随机数字
     */
    public static String number(int length) {
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = nextNum();
        }
        return new String(result);
    }

    /**
     * 生成length位长度的字母加数据的随机字符串
     *
     * @param length 长度
     * @return 随机字符
     */
    public static String random(int length) {
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = nextChar();
        }
        return new String(result);
    }
}
