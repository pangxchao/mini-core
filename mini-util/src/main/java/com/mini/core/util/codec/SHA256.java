package com.mini.core.util.codec;

import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.Nonnull;

import static org.apache.commons.codec.binary.Hex.encodeHexString;

public final class SHA256 {

    /**
     * SHA256加密
     *
     * @param bytes 加密内容
     * @return hex加密结果
     */
    public static String encode(@Nonnull byte[] bytes) {
        var encode = DigestUtils.sha256(bytes);
        return encodeHexString(encode, true);
    }

    /**
     * SHA256加密
     *
     * @param data 加密字符串
     * @return hex加密结果
     */
    public static String encode(String data) {
        var encode = DigestUtils.sha256(data);
        return encodeHexString(encode, true);
    }
}