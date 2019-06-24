package com.mini.digest;

import java.io.InputStream;

/**
 * MD5Util.java
 * @author xchao
 */
public class MD5Util {
    private static final DigestUtil instance = new DigestUtil("MD5");

    /**
     * MD5加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(String data) {
        return instance.encodeToByte(data);
    }

    /**
     * MD5加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(String data) {
        return instance.encode(data);
    }

    /**
     * MD5加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(byte[] data) {
        return instance.encodeToByte(data);
    }

    /**
     * MD5加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(byte[] data) {
        return instance.encode(data);
    }

    /**
     * MD5加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static byte[] encodeToByte(InputStream data) {
        return instance.encodeToByte(data);
    }

    /**
     * MD5加密
     * @param data 加密数据
     * @return 加密结果
     */
    public static String encode(InputStream data) {
        return instance.encode(data);
    }

}
