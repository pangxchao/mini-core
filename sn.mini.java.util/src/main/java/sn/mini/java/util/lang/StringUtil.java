/**
 * Created the com.cfinal.util.lang.CFString.java
 *
 * @created 2016年10月18日 上午11:19:03
 * @version 1.0.0
 */
package sn.mini.java.util.lang;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * java.lang.String 工具类
 *
 * @author XChao
 */
public class StringUtil {

    /**
     * 字符串替换的回调接口
     *
     * @author XChao
     */
    @FunctionalInterface
    public interface SNReplace {
        /**
         * 将text转化为特定的字串返回
         *
         * @param matcher Matcher对象
         * @return
         */
        String replace(Matcher matcher);
    }

    /**
     * 将String中的所有regex匹配的字串全部替换掉
     *
     * @param string      代替换的字符串
     * @param regex       替换查找的正则表达式
     * @param replacement 替换函数
     * @return
     */
    public static String replaceAll(String string, String regex, SNReplace replacement) {
        return replaceAll(string, Pattern.compile(regex), replacement);
    }

    /**
     * 将String中的所有pattern匹配的字串替换掉
     *
     * @param string      代替换的字符串
     * @param pattern     替换查找的正则表达式对象
     * @param replacement 替换函数
     * @return
     */
    public static String replaceAll(String string, Pattern pattern, SNReplace replacement) {
        if (string == null) {
            return null;
        }
        Matcher matcher = pattern.matcher(string);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, replacement.replace(matcher));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 将String中的regex第一次匹配的字串替换掉
     *
     * @param string      代替换的字符串
     * @param regex       替换查找的正则表达式
     * @param replacement 替换函数
     * @return
     */
    public static String replaceFirst(String string, String regex, SNReplace replacement) {
        return replaceFirst(string, Pattern.compile(regex), replacement);
    }

    /**
     * 将String中的pattern第一次匹配的字串替换掉
     *
     * @param string      代替换的字符串
     * @param pattern     替换查找的正则表达式对象
     * @param replacement 替换函数
     * @return
     */
    public static String replaceFirst(String string, Pattern pattern, SNReplace replacement) {
        if (string == null) {
            return null;
        }
        Matcher matcher = pattern.matcher(string);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, replacement.replace(matcher));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null;
    }

    /**
     * 判断字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 判断字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isBlank(String value) {
        return isEmpty(value) || value.trim().length() == 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param value
     * @return
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static String defaultIfEmpty(String value, String defaultValue) {
        return isNotBlank(value) ? value : defaultValue;
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static <T> String join(T[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
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
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(long[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(long... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(int[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(int... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(short[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(short... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(byte[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(byte... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(double[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(double... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(float[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(float... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(boolean[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(boolean... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(char[] value, String join) {
        StringBuilder result = new StringBuilder();
        if (value != null && value.length > 0) {
            int length = value.length - 1;
            for (int i = 0; i < length; i++) {
                result.append(value[i]).append(join);
            }
            result.append(value[length]);
        }
        return result.toString();
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(char... value) {
        return StringUtil.join(value, "");
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(Collection<?> value, String join) {
        return StringUtil.join(value.toArray(), join);
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(Collection<?> value) {
        return StringUtil.join(value.toArray());
    }

    /**
     * 字符串连接
     *
     * @param value
     * @param join
     * @return
     */
    public static String join(Stream<?> stream, String join) {
        return StringUtil.join(stream.toArray(), join);
    }

    /**
     * 字符串连接
     *
     * @param value
     * @return
     */
    public static String join(Stream<?> stream) {
        return StringUtil.join(stream.toArray());
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
     * @param stringContent
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
     * @param stringContent
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
        return Character.toUpperCase(string.charAt(0)) + string.substring(1, string.length());
    }

    /**
     * 把传入字符串首字母小写
     *
     * @param string
     * @return
     */
    public static String firstLowerCase(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1, string.length());
    }

    /**
     * 把字符串改写成java的驼峰命名法
     *
     * @param name           字符串内容， 一般是将单词用"_"连接的字符串
     * @param firstUpperCase 变成java命名后，首字母是否大写
     * @return
     */
    public static String toJavaName(String name, boolean firstUpperCase) {
        String[] strArr = name.split("_");
        StringBuilder newName = new StringBuilder();
        for (String aStrArr : strArr) {
            if (aStrArr.length() > 0) {
                newName.append(firstUpperCase(aStrArr));
            }
        }
        if (firstUpperCase) return newName.toString();
        return firstLowerCase(newName.toString());
    }

    /**
     * 把用java驼峰命名法的字符串字符串转换成 用"_"连接的数据库命名
     *
     * @param fieldName
     * @return
     */
    public static String toDBName(String fieldName) {
        StringBuilder result = new StringBuilder();
        char[] fileNameChars = fieldName.toCharArray();
        for (int i = 0, len = fileNameChars.length; i < len; i++) {
            if (Character.isUpperCase(fileNameChars[i]) && i > 0) {
                result.append("_");
            }
            result.append(Character.toLowerCase(fileNameChars[i]));
        }
        return result.toString();
    }

}
