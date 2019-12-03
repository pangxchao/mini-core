package com.mini.core.validate;

import org.apache.commons.lang3.StringUtils;

public final class ValidateUtil {
    // *: 0 次或者多次, +: 一次或者多次, ?: 0次或者1次, {n}:刚刚n次, {n,m}: n到m次
    // \d: [0-9], \D: [^0-9], \w:[a-zA-Z_0-9], \W:[^a-zA-Z_0-9], \s: [\t\n\r\f], \S: [^\t\n\r\f]
    public static final String PHONE = "((010|02[0-9]|0[3-9]{2,3})[-])?[0-9]{6,8}";
    public static final String ID_CARD = "\\d{15}(\\d{2}[A-Za-z0-9])?";
    public static final String EMAIL = "\\S+[@]\\S+[.]\\S+";
    public static final String CHINESE = "[\u4E00-\u9FA5]+";

    public static final String REQUIRE = "[a-z_][a-z0-9_]*";
    public static final String MOBILE = "1\\d{10}";
    public static final String NUMBER = "\\d+";
    public static final String LETTER = "\\w+";

    /**
     * 设置错误码和错误消息
     * @param error 错误码
     */
    public static void sendError(int error) {
        sendError(error, null);
    }

    /**
     * 设置错误码和错误消息
     * @param error   错误码
     * @param message 错误消息
     */
    public static void sendError(int error, String message) {
        throw new ValidateException(error, message);
    }

    /**
     * 验证表达式/传入参数是否为true
     * @param validate 表达式/传入参
     * @param error    表达式为 false 的提示错误码
     * @param message  静态式为 false 的提示错误消息
     */
    public static void is(boolean validate, int error, String message) {
        if (!validate) sendError(error, message);
    }

    /**
     * 验证字符串与正则表达式是否匹配
     * @param string  字符串
     * @param regex   正则表达式
     * @param error   错误码
     * @param message 错误消息
     */
    public static void isRegex(String string, String regex, int error, String message) {
        is(string != null && string.matches(regex), error, message);
    }

    /**
     * 验证传入字符串是否有值
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void isNotBlank(String string, int error, String message) {
        is(StringUtils.isNotBlank(string), error, message);
    }

    /**
     * 验证传入对象是否为空
     * @param object  传入对象
     * @param error   错误码
     * @param message 错误消息
     */
    public static void isNotNull(Object object, int error, String message) {
        is(object != null, error, message);
    }

    /**
     * 验证传入对象是否为空
     * @param object  传入对象
     * @param error   错误码
     * @param message 错误消息
     */
    public static void isNull(Object object, int error, String message) {
        is(object == null, error, message);
    }

    /**
     * 验证传入字符串是否为邮箱格式
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isEmail(String string, int error, String message) {
        isRegex(string, EMAIL, error, message);
    }

    /**
     * 验证传入字符串是否为电话号码
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isPhone(String string, int error, String message) {
        isRegex(string, PHONE, error, message);
    }

    /**
     * 验证传入字符串是否为手机号
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isMobile(String string, int error, String message) {
        isRegex(string, MOBILE, error, message);
    }

    /**
     * 验证传入字符串是否为手机号或者电话号码
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isMobilePhone(String string, int error, String message) {
        is(string != null && (string.matches(MOBILE) || string.matches(PHONE)), error, message);
    }

    /**
     * 验证传入字符串是否为纯英文字母
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isLetter(String string, int error, String message) {
        isRegex(string, LETTER, error, message);
    }

    /**
     * 验证传入字符串是否为纯数字
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isNumber(String string, int error, String message) {
        isRegex(string, NUMBER, error, message);
    }

    /**
     * 验证传入字符串是否为纯中文
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isChinese(String string, int error, String message) {
        isRegex(string, CHINESE, error, message);
    }

    /**
     * 验证传入字符串是否为身份证号码
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isIdCard(String string, int error, String message) {
        isRegex(string, ID_CARD, error, message);
    }

    /**
     * 验证传入字符串是否为字母、数字和下划线组成,数字不能开头
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */

    public static void isRequire(String string, int error, String message) {
        isRegex(string, ID_CARD, error, message);
    }
}
