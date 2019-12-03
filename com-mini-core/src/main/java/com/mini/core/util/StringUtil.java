package com.mini.core.util;

import org.apache.commons.lang3.StringUtils;

import java.net.*;
import java.nio.charset.Charset;
import java.util.Map;

import static com.mini.core.validate.ValidateUtil.MOBILE;
import static java.lang.Character.isUpperCase;

public class StringUtil extends StringUtils {
    /**
     * 自定义格式化字符串
     * @param self 字符串内容
     * @param map  格式化参数
     * @return 格式化结果
     */
    public static String format(String self, Map<String, Object> map) {
        if (self == null || self.isBlank()) return self;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = defaultIfBlank(entry.getKey(), "");
            String val = String.valueOf(entry.getValue());
            self = self.replaceAll("\\{" + key + "}", val);
        }
        return self;
    }

    /**
     * -将字符串转换成URL
     * @param self 字符串内容
     * @return 转换结果
     */
    public static URL toURL(String self) {
        try {
            return new URL(self);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * -将字符串转换成URI
     * @param self 字符串内容
     * @return URI
     */
    public static URI toURI(String self) {
        try {
            return new URI(self);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * - 将字符串转成URL编码
     * @param self    字符串内容
     * @param charset 编码方式
     */
    public static String urlEncode(String self, String charset) {
        try {
            return URLEncoder.encode(self, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * - 将字符串转成URL编码
     * @param self    字符串内容
     * @param charset 编码方式
     */
    public static String urlEncode(String self, Charset charset) {
        try {
            return URLEncoder.encode(self, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * - 将URL编码还原成原来的字符串
     * @param self    字符串
     * @param charset 编码
     */
    public static String urlDecode(String self, String charset) {
        try {
            return URLDecoder.decode(self, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * - 将URL编码还原成原来的字符串
     * @param self    字符串
     * @param charset 编码
     */
    public static String urlDecode(String self, Charset charset) {
        try {
            return URLDecoder.decode(self, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把传入字符串首字母大写
     * @param self 字符串如：first
     * @return 如：First
     */
    public static String firstUpperCase(String self) {
        return Character.toUpperCase(self.charAt(0)) + self.substring(1);
    }

    /**
     * 把传入字符串首字母小写
     * @param self 字符串如：First
     * @return 如: first
     */
    public static String firstLowerCase(String self) {
        return Character.toLowerCase(self.charAt(0)) + self.substring(1);
    }

    /**
     * 把字符串改写成java的驼峰命名法
     * @param self           字符串内容， 一般是将单词用"_"连接的字符串变成java命名
     * @param firstUpperCase 命名后，首字母是否大写
     * @return 驼峰命名字符串
     */
    public static String toJavaName(String self, boolean firstUpperCase) {
        StringBuilder result = new StringBuilder();
        for (String word : self.split("_")) {
            result.append(firstUpperCase(word));
        }
        if (firstUpperCase) return result.toString();
        return firstLowerCase(result.toString());
    }

    /**
     * 把用java驼峰命名法的字符串字符串转换成 用"_"连接的数据库命名
     * @param self 字符串内容
     * @return 返回字符串都是小写字母
     */
    public static String toDBName(String self) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < self.length(); i++) {
            char ch = self.charAt(i);
            if (i > 0 && isUpperCase(self.charAt(i))) {
                result.append("_");
            }
            result.append(Character.toLowerCase(ch));
        }
        return result.toString();
    }

    /**
     * 手机号脱敏
     * @param self 标准手机号
     * @return 脱敏后的手机号
     */
    public static String phoneEncode(String self) {
        if (self == null) {
            return null;
        }
        if (self.matches(MOBILE)) {
            return self;
        }
        if (self.length() < 11) {
            return self;
        }
        return self.substring(0, 3) + "****" //
                + self.substring(7);

    }
}
