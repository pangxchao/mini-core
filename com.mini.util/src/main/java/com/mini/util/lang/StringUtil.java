package com.mini.util.lang;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Iterator;

import static java.lang.Character.isUpperCase;

/**
 * java.lang.String 工具类
 *
 * @author XChao
 */
public class StringUtil {

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(String join, Object... value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; value != null && i < value.length; i++) {
            if (i > 0) result.append(join).append(value[i]);
            else result.append(value[i]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(Object... value) {
        return StringUtil.join("", value);
    }

    /**
     * 字符串连接
     *
     * @param join
     * @param value
     * @return
     */
    public static <T> String join(String join, Iterator<T> value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; value != null && value.hasNext(); i++) {
            if (i > 0) result.append(join).append(value.next());
            else result.append(value.next());
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static <T> String join(Iterator<T> value) {
        return StringUtil.join("", value);
    }


    /**
     * 字符串连接
     *
     * @param join
     * @param value
     * @return
     */
    public static <T> String join(String join, Iterable<T> value) {
        return StringUtil.join(join, value == null ? null : value.iterator());
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static <T> String join(Iterable<T> value) {
        return StringUtil.join("", value);
    }

    /**
     * 将字符串转换成URL
     *
     * @param name
     * @return
     */
    public static URL toURL(String name) {
        try {
            return new URL(name);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转换成URI
     *
     * @param name
     * @return
     */
    public static URI toURI(String name) {
        try {
            return new URI(name);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串转成URL编码
     *
     * @param value
     * @param encode
     * @throws UnsupportedEncodingException
     */
    public static String urlEncode(String value, String encode) {
        try {
            return URLEncoder.encode(value, encode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将URL编码还原成原来的字符串
     *
     * @param value
     * @param encode
     * @throws UnsupportedEncodingException
     */
    public static String urlDecode(String value, String encode) {
        try {
            return URLDecoder.decode(value, encode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把传入字符串首字母大写
     *
     * @param string
     * @return
     */
    public static String firstUpperCase(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * 把传入字符串首字母小写
     *
     * @param string
     * @return
     */
    public static String firstLowerCase(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * 把字符串改写成java的驼峰命名法
     *
     * @param name           字符串内容， 一般是将单词用"_"连接的字符串变成java命名
     * @param firstUpperCase 命名后，首字母是否大写
     * @return
     */
    public static String toJavaName(String name, boolean firstUpperCase) {
        String[] strArr = name.split("_");
        StringBuilder result = new StringBuilder();
        for (String word : name.split("_")) {
            result.append(firstUpperCase(word));
        }
        if (firstUpperCase) return result.toString();
        return firstLowerCase(result.toString());
    }

    /**
     * 把用java驼峰命名法的字符串字符串转换成 用"_"连接的数据库命名
     *
     * @param name
     * @return 返回字符串都是小写字母
     */
    public static String toDBName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i); // 获取当前的字符
            if (i > 0 && isUpperCase(name.charAt(i))) {
                result.append("_");
            }
            result.append(Character.toLowerCase(ch));
        }
        return result.toString();
    }

}
