package com.mini.core.util.codec;

import lombok.SneakyThrows;

import javax.annotation.Nonnull;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static java.security.MessageDigest.getInstance;

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
    @SneakyThrows
    public static String encode(@Nonnull byte[] bytes) {
        final var digest = getInstance("MD5");
        return Hex.encodeToString(bytes);
    }

    /**
     * MD5加密
     *
     * @param data    加密字符串
     * @param charset 编码
     * @return hex加密结果
     */
    @SneakyThrows
    public static String encode(String data, Charset charset) {
        return encode(encode(data.getBytes(charset)));
    }

    /**
     * MD5加密-默认UTF-8编码
     *
     * @param data 加密字符串
     * @return hex加密结果
     */
    public static String encode(@Nonnull String data) {
        return encode(data, StandardCharsets.UTF_8);
    }
}
