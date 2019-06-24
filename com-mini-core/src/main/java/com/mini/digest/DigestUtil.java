package com.mini.digest;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * DigestUtil.java
 * @author xchao
 */
public class DigestUtil {
    // 用来将字节转换成 16 进制表示的字符
    private static final char[] ENCODE_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private final String algorithm;

    protected DigestUtil(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * 获取MD2加密文摘
     * @return MessageDigest
     */
    private MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * MD2加密
     * @param data 加密数据
     * @return 加密结果
     */
    public byte[] encodeToByte(String data) {
        return getDigest().digest(data.getBytes());
    }

    /**
     * MD2加密
     * @param data 加密数据
     * @return 加密结果
     */
    public String encode(String data) {
        return toString(encodeToByte(data));
    }

    /**
     * MD2加密
     * @param data 加密数据
     * @return 加密结果
     */
    public byte[] encodeToByte(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * MD2加密
     * @param data 加密数据
     * @return 加密结果
     */
    public String encode(byte[] data) {
        return toString(encodeToByte(data));
    }

    /**
     * MD2加密
     * @param data 加密数据
     * @return 加密结果
     */
    public byte[] encodeToByte(InputStream data) {
        try {
            MessageDigest digest = getDigest();
            byte[] buffer = new byte[1024];
            for (int read = data.read(buffer); read > -1; ) {
                digest.update(buffer, 0, read);
                read = data.read(buffer);
            }
            return digest.digest();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * MD2加密
     * @param data 加密数据
     * @return 加密结果
     */
    public String encode(InputStream data) {
        return toString(encodeToByte(data));
    }


    /** 将byte数组转换成String */
    private String toString(byte[] bytes) {
        // 每个字节用 16 进制表示的话，使用两个字符
        char[] result = new char[bytes.length * 2];
        // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
        for (int i = 0; i < bytes.length; i++) {
            // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
            result[i * 2] = ENCODE_DIGITS[(bytes[i] >>> 4) & 0xf];
            // 取字节中低 4 位的数字转换
            result[i * 2 + 1] = ENCODE_DIGITS[bytes[i] & 0xf];
        }
        // 换后的结果转换为字符串
        return new String(result);
    }
}
