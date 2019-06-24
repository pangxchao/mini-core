package com.mini.digest;

import java.io.InputStream;

/**
 * SHA512Util.java
 * @author xchao
 */
public class SHA512Util {
    private static final DigestUtil instance = new DigestUtil("SHA-512");

    /**
     * SHA512加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA512加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * SHA512加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA512加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * SHA512加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * SHA512加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
