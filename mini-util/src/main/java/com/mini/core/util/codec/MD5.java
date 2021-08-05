package com.mini.core.util.codec;

import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Nonnull;

import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 * MD5 加密
 *
 * @author pangchao
 */
public final class MD5 {
    /**
     * MD5加密
     *
     * @param bytes 加密内容
     * @return hex加密结果
     */
    public static String encode(@Nonnull byte[] bytes) {
        byte[] encode = DigestUtils.md5(bytes);
        return encodeHexString(encode, true);
    }

    /**
     * MD5加密
     *
     * @param data 加密字符串
     * @return hex加密结果
     */
    public static String encode(String data) {
        byte[] encode = DigestUtils.md5(data);
        return encodeHexString(encode, true);
    }
}
