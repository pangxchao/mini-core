package com.mini.digest;

import java.io.InputStream;

/**
 * SHA1Util.java
 * @author xchao
 */
public class SHA1Util {
    private static final DigestUtil instance = new DigestUtil("SHA-1");

    /**
     * SHA1加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA1加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * SHA1加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA1加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * SHA1加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA1加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
