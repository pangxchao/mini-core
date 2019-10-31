package com.mini.util;

import javax.annotation.Nonnull;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import static com.mini.validate.ValidateUtil.MOBILE;
import static java.lang.Character.isUpperCase;

/**
 * java.lang.String 工具类
 * @author XChao
 */
public final class StringUtil {
    private StringUtil() {
    }

    /**
     * 判断字符串是否为空
     * @param self 字符串
     * @return true - 是
     */
    public static boolean isEmpty(String self) {
        return self == null || self.isEmpty();
    }

    /**
     * 判断字符串是否为空
     * @param self 字符串
     * @return true - 是
     */
    public static boolean isBlank(String self) {
        return self == null || self.isBlank();
    }

    /**
     * 获取空白字符串时的默认值
     * @param self 字符串
     * @param def  默认值
     * @return isBlank(value)- def
     */
    public static String def(String self, String def) {
        return !isBlank(self) ? self : def;
    }

    /**
     * 字符串连接
     * @param builder StringBuilder
     * @param join    连接符
     * @param value   连接对象
     * @return 连接结果
     */
    @Nonnull
    @SafeVarargs
    public static <T> StringBuilder join(StringBuilder builder, String join, T... value) {
        for (int i = 0; value != null && i < value.length; i++) {
            if (i > 0) builder.append(join).append(value[i]);
            else builder.append(value[i]);
        }
        return builder;
    }

    /**
     * - 字符串连接
     * @param join  连接符
     * @param value 连接对象
     * @return 连接结果
     */
    @Nonnull
    @SafeVarargs
    public static <T> String join(String join, T... value) {
        StringBuilder result = new StringBuilder();
        StringUtil.join(result, join, value);
        return result.toString();
    }

    /**
     * 字符串连接
     * @param builder StringBuilder
     * @param join    连接符
     * @param value   连接对象
     * @return 连接结果
     */
    @Nonnull
    public static <T> StringBuilder join(StringBuilder builder, String join, Iterator<T> value) {
        for (int i = 0; value != null && value.hasNext(); i++) {
            if (i > 0) builder.append(join).append(value.next());
            else builder.append(value.next());
        }
        return builder;
    }

    /**
     * 字符串连接
     * @param join  连接符
     * @param value 连接对象
     * @return 连接结果
     */
    @Nonnull
    public static <T> String join(String join, Iterator<T> value) {
        StringBuilder result = new StringBuilder();
        StringUtil.join(result, join, value);
        return result.toString();
    }

    /**
     * 字符串连接
     * @param builder StringBuilder
     * @param join    连接符
     * @param value   连接对象
     * @return 连接结果
     */
    @Nonnull
    public static <T> StringBuilder join(StringBuilder builder, String join, Iterable<T> value) {
        return value == null ? builder : join(builder, join, value.iterator());
    }

    /**
     * 字符串连接
     * @param join  连接符
     * @param value 连接对象
     * @return 连接结果
     */
    @Nonnull
    public static <T> String join(String join, Iterable<T> value) {
        StringBuilder result = new StringBuilder();
        StringUtil.join(result, join, value);
        return result.toString();
    }



    /**
     * 返回字符串长度
     * @param self 当前字符串
     * @return self为Null时，返回0
     */
    public static int length(String self) {
        return self == null ? 0 : self.length();
    }

    /**
     * -格式化字符串
     * @param self 字符串内容
     * @param args 参数
     * @return self为Null时，返回“”
     */
    @Nonnull
    public static String format(String self, Object... args) {
        return self == null ? "" : String.format(self, args);
    }

    /**
     * 自定义格式化字符串
     * @param self 字符串内容
     * @param map  格式化参数
     * @return 格式化结果
     */
    public static String format(String self, Map<String, Object> map) {
        if (self == null || self.isBlank()) return self;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = StringUtil.def(entry.getKey(), "");
            String val = String.valueOf(entry.getValue());
            self = self.replaceAll("\\{" + key + "}", val);
        }
        return self;
    }

    /**
     * 比较两个字符串是否相等 都为null时
     * @param self 当前字符串
     * @param obj  比较的对象
     * @return 都为Null时，返回False
     */
    public static boolean equals(String self, Object obj) {
        return self != null && self.equals(obj);
    }

    /**
     * 比较两个字符串是否相等 都为null时
     * @param self 当前字符串
     * @param obj  比较的对象
     * @return 都为Null时，返回False
     */
    public static boolean eq(String self, Object obj) {
        return self != null && self.equals(obj);
    }

    /**
     * 忽略大小写比较两个字符串是否相等 都为null时
     * @param self 当前字符串
     * @param str  比较的字符串
     * @return 都为Null时，返回False
     */
    public static boolean equalsIgnoreCase(String self, String str) {
        return self != null && self.equalsIgnoreCase(str);
    }

    /**
     * 比较字符串的字符内容是否相同
     * @param self 当前字符串
     * @param str  比较的字符串
     * @return 都为Null时，返回False
     */
    public static boolean contentEquals(String self, CharSequence str) {
        return self != null && str != null && self.contentEquals(str);
    }

    /**
     * 比较字符串是否以指定字符串开头
     * @param self   当前字符串
     * @param prefix 比较的字符串
     * @return 都为Null时，返回False
     */
    public static boolean startsWith(String self, String prefix) {
        return self != null && prefix != null && self.startsWith(prefix);
    }

    /**
     * 比较字符串是否以指定字符串开头
     * @param self   当前字符串
     * @param prefix 比较的字符串
     * @param offset 偏移长度
     * @return 都为Null时，返回False
     */
    public static boolean startsWith(String self, String prefix, int offset) {
        return self != null && prefix != null && self.startsWith(prefix, offset);
    }

    /**
     * 比较字符串是否以指定字符串结尾
     * @param self   当前字符串
     * @param suffix 比较的字符串
     * @return 都为Null时，返回False
     */
    public static boolean endsWith(String self, String suffix) {
        return self != null && suffix != null && self.endsWith(suffix);
    }

    /**
     * 截取字符串
     * @param self  当前字符串
     * @param begin 开始位置
     * @return self为Null时，返回“”
     */
    @Nonnull
    public static String substring(String self, int begin) {
        return self == null ? "" : self.substring(begin);
    }

    /**
     * 截取字符串
     * @param self  当前字符串
     * @param begin 开始位置
     * @param end   结束位置
     * @return self为Null时，返回“”
     */
    @Nonnull
    public static String substring(String self, int begin, int end) {
        return self == null ? "" : self.substring(begin, end);
    }

    /**
     * 查找字符位置
     * @param self 当前字符串
     * @param ch   指定字符
     * @return self为Null时，返回-1
     */
    public static int indexOf(String self, int ch) {
        return self == null ? -1 : self.indexOf(ch);
    }

    /**
     * 查找字符串位置
     * @param self 当前字符串
     * @param str  指定字符串
     * @return self为Null时，返回-1
     */
    public static int indexOf(String self, String str) {
        return self == null || str == null ? -1 : self.indexOf(str);
    }

    /**
     * 查找字符位置
     * @param self 当前字符串
     * @param ch   指定字符
     * @param from 指定开始位置
     * @return self为Null时，返回-1
     */
    public static int indexOf(String self, int ch, int from) {
        return self == null ? -1 : self.indexOf(ch, from);
    }

    /**
     * 查找字符串位置
     * @param self 当前字符串
     * @param str  指定字符串
     * @param from 指定开始位置
     * @return self为Null时，返回-1
     */
    public static int indexOf(String self, String str, int from) {
        return self == null || str == null ? -1 : self.indexOf(str, from);
    }

    /**
     * 反向查找字符位置
     * @param self 当前字符串
     * @param ch   指定字符
     * @return self为Null时，返回-1
     */
    public static int lastIndexOf(String self, int ch) {
        return self == null ? -1 : self.lastIndexOf(ch);
    }

    /**
     * 反向查找字符串位置
     * @param self 当前字符串
     * @param str  指定字符串
     * @return self为Null时，返回-1
     */
    public static int lastIndexOf(String self, String str) {
        return self == null || str == null ? -1 : self.lastIndexOf(str);
    }

    /**
     * 反向查找字符位置
     * @param self 当前字符串
     * @param ch   指定字符
     * @param from 指定开始位置
     * @return self为Null时，返回-1
     */
    public static int lastIndexOf(String self, int ch, int from) {
        return self == null ? -1 : self.lastIndexOf(ch, from);
    }

    /**
     * 反向查找字符串位置
     * @param self 当前字符串
     * @param str  指定字符串
     * @param from 指定开始位置
     * @return self为Null时，返回-1
     */
    public static int lastIndexOf(String self, String str, int from) {
        return self == null || str == null ? -1 : self.lastIndexOf(str, from);
    }

    /**
     * 将字符串按指定正则表达式分割成数组
     * @param self  当前字符串
     * @param regex 正则表达式
     * @param limit 跳过的字符长度
     * @return true-匹配
     */
    public static String[] split(String self, String regex, int limit) {
        if (self == null || regex == null) return new String[0];
        return self.split(regex, limit);
    }

    /**
     * 将字符串按指定正则表达式分割成数组
     * @param self  当前字符串
     * @param regex 正则表达式
     * @return true-匹配
     */
    public static String[] split(String self, String regex) {
        return StringUtil.split(self, regex, 0);
    }

    /**
     * 验证字符串是否与正则表达式匹配
     * @param self  当前字符串
     * @param regex 正则表达式
     * @return true-匹配
     */
    public static boolean matches(String self, String regex) {
        return self != null && regex != null && self.matches(regex);
    }

    /**
     * 替换字符串
     * @param self    当前字符串
     * @param oldChar 替换字符
     * @param newChar 替换字符
     * @return 替换后的字符串
     */
    public static String replace(String self, char oldChar, char newChar) {
        return self == null ? null : self.replace(oldChar, newChar);

    }

    /**
     * 替换字符串
     * @param self        当前字符串
     * @param target      替换目标
     * @param replacement 替换字符串
     * @return 替换后的字符串
     */
    public static String replace(String self, CharSequence target, CharSequence replacement) {
        if (self == null || target == null || replacement == null) return self;
        return self.replace(target, replacement);
    }

    /**
     * 替换字符串
     * @param self        当前字符串
     * @param regex       目标正则表达式
     * @param replacement 替换字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String self, String regex, String replacement) {
        if (self == null || regex == null || replacement == null) return self;
        return self.replaceAll(regex, replacement);
    }

    /**
     * 替换字符串
     * @param self        当前字符串
     * @param regex       目标正则表达式
     * @param replacement 替换字符串
     * @return 替换后的字符串
     */
    public static String replaceFirst(String self, String regex, String replacement) {
        if (self == null || regex == null || replacement == null) return self;
        return self.replaceFirst(regex, replacement);
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
        if (!matches(self, MOBILE)) return self;
        return self.substring(0, 3) + "****" //
                + self.substring(7);

    }
}
