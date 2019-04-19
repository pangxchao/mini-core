package com.mini.util.lang;

import java.net.*;
import java.util.Iterator;

import static java.lang.Character.isUpperCase;

/**
 * java.lang.String 工具类
 * @author XChao
 */
public final class StringUtil {
    private StringUtil() {
    }

    /**
     * -判断字符串是否为空
     * @param value 字符串
     * @return true - 是
     */
    public static boolean isEmpty(String value) {
        return value == null;
    }

    /**
     * -判断字符串是否为空
     * @param value 字符串
     * @return true - 是
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * -判断字符串是否为空
     * @param value 字符串
     * @return true - 是
     */
    public static boolean isBlank(String value) {
        return isEmpty(value) || value.trim().length() == 0;
    }

    /**
     * -判断字符串是否为空
     * @param value 字符串
     * @return true - 是
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static String defaultIfEmpty(String value, String defaultValue) {
        return isNotBlank(value) ? value : defaultValue;
    }

    /**
     * - 字符串连接
     * @param join  连接符
     * @param value 连接对象
     * @return 连接结果
     */
    @SafeVarargs
    public static <T> String join(String join, T... value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; value != null && i < value.length; i++) {
            if (i > 0) result.append(join).append(value[i]);
            else result.append(value[i]);
        }
        return result.toString();
    }

    /**
     * -字符串连接
     * @param join  连接符
     * @param value 连接对象
     * @return 连接结果
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
     * -字符串连接
     * @param join  连接符
     * @param value 连接对象
     * @return 连接结果
     */
    public static <T> String join(String join, Iterable<T> value) {
        return StringUtil.join(join, value == null ? null : value.iterator());
    }

    /**
     * -格式化字符串
     * @param str  字符串内容
     * @param args 参数
     * @return 格式化结果
     */
    public static String format(String str, Object... args) {
        return str == null ? "" : String.format(str, args);
    }

    /**
     * -将字符串转换成URL
     * @param name 字符串内容
     * @return 转换结果
     */
    public static URL toURL(String name) {
        try {
            return new URL(name);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * -将字符串转换成URI
     * @param name 字符串内容
     * @return URI
     */
    public static URI toURI(String name) {
        try {
            return new URI(name);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * - 将字符串转成URL编码
     * @param value  字符串内容
     * @param encode 编码
     */
    public static String urlEncode(String value, String encode) {
        try {
            return URLEncoder.encode(value, encode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * - 将URL编码还原成原来的字符串
     * @param value  字符串内容
     * @param encode 编码
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
     * @param string 字符串如：first
     * @return 如：First
     */
    public static String firstUpperCase(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * 把传入字符串首字母小写
     * @param string 字符串如：First
     * @return 如: first
     */
    public static String firstLowerCase(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * 把字符串改写成java的驼峰命名法
     * @param name           字符串内容， 一般是将单词用"_"连接的字符串变成java命名
     * @param firstUpperCase 命名后，首字母是否大写
     * @return 驼峰命名字符串
     */
    public static String toJavaName(String name, boolean firstUpperCase) {
        StringBuilder result = new StringBuilder();
        for (String word : name.split("_")) {
            result.append(firstUpperCase(word));
        }
        if (firstUpperCase) return result.toString();
        return firstLowerCase(result.toString());
    }

    /**
     * 把用java驼峰命名法的字符串字符串转换成 用"_"连接的数据库命名
     * @param name 字符串内容
     * @return 返回字符串都是小写字母
     */
    public static String toDBName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (i > 0 && isUpperCase(name.charAt(i))) {
                result.append("_");
            }
            result.append(Character.toLowerCase(ch));
        }
        return result.toString();
    }

    /**
     * 手机号脱敏
     * @param phone 标准手机号
     * @return 脱敏后的手机号
     */
    public static String phoneEncode(String phone) {
        return phone == null || phone.length() <= 7 ? phone : phone.substring(0, 3) + "****" + phone.substring(7);
    }
}
