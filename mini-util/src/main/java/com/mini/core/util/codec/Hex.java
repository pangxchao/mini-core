package com.mini.core.util.codec;

import javax.annotation.Nonnull;

import static java.lang.Character.digit;

public final class Hex {
    /**
     * 16进制(hex)字符列表
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static final char[] DIGITS = "0123456789ABCDEF".toCharArray();

    /**
     * 将16进制字符数组转换为byte数组
     *
     * @param chars 16进制字符数组
     * @return 如果chars长度为奇数，返回NULL
     */
    public static byte[] decode(@Nonnull char[] chars) {
        final int length = chars.length;
        if ((length & 1) != 0) {
            return null;
        }

        final byte[] result = new byte[length >> 1];
        for (int i = 0, j = 0; j < length; ++i) {
            int r = digit(chars[j++], 16) << 4;
            r |= digit(chars[j++], 16);
            result[i] = (byte) (r & 255);
        }
        return result;
    }

    /**
     * 将16进制的字符串转换成byte数组
     *
     * @param data 16进制字符串
     * @return 如果字符串长度为奇数，返回NULL
     */
    public static byte[] decode(String data) {
        return decode(data.toCharArray());
    }

    /**
     * 将byte数组转换成16进制的字符数组
     *
     * @param bytes byte数组
     * @return 转换结果
     */
    public static char[] encode(@Nonnull byte[] bytes) {
        final char[] result = new char[bytes.length * 2];
        for (int i = 0, j = 0; i < bytes.length; i++) {
            int ix1 = (bytes[i] >>> 4) & 0xf;
            result[j++] = DIGITS[ix1];
            // 不足两位补0
            int ix2 = bytes[i] & 0xf;
            result[j++] = DIGITS[ix2];
        }
        return result;
    }

    /**
     * 将byte数组转换成16进制的字符串
     *
     * @param bytes byte数组
     * @return 转换结果
     */
    public static String encodeToString(@Nonnull byte[] bytes) {
        return new String(encode(bytes));
    }
}
