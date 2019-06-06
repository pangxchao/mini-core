package com.mini.util.validate;

import com.mini.util.lang.StringUtil;

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
     * @param error   错误码
     * @param message 错误消息
     */
    public static void validate(int error, String message) {
        throw new ValidateException(error, message);
    }

    /**
     * 验证表达式/传入参数是否为true
     * @param validate 表达式/传入参
     * @param error    表达式为 false 的提示错误码
     * @param message  静态式为 false 的提示错误消息
     */
    public static void validate(boolean validate, int error, String message) {
        if (!validate) validate(error, message);
    }

    /**
     * 验证字符串与正则表达式是否匹配
     * @param string  字符串
     * @param regex   正则表达式
     * @param error   错误码
     * @param message 错误消息
     */
    public static void regex(String string, String regex, int error, String message) {
        validate(string != null && string.matches(regex), error, message);
    }

    /**
     * 验证传入字符串是否有值
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void blank(String string, int error, String message) {
        validate(!StringUtil.isBlank(string), error, message);
    }

    /**
     * 验证传入对象是否为空
     * @param object  传入对象
     * @param error   错误码
     * @param message 错误消息
     */
    public static void nil(Object object, int error, String message) {
        validate(object != null, error, message);
    }

    /**
     * 验证传入字符串是否为邮箱格式
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void email(String string, int error, String message) {
        regex(string, EMAIL, error, message);
    }

    /**
     * 验证传入字符串是否为电话号码
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void phone(String string, int error, String message) {
        regex(string, PHONE, error, message);
    }

    /**
     * 验证传入字符串是否为手机号
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void mobile(String string, int error, String message) {
        regex(string, MOBILE, error, message);
    }

    /**
     * 验证传入字符串是否为手机号或者电话号码
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void mobilePhone(String string, int error, String message) {
        validate(string != null && (string.matches(MOBILE) || string.matches(PHONE)), error, message);
    }

    /**
     * 验证传入字符串是否为纯英文字母
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void letter(String string, int error, String message) {
        regex(string, LETTER, error, message);
    }

    /**
     * 验证传入字符串是否为纯数字
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void number(String string, int error, String message) {
        regex(string, NUMBER, error, message);
    }

    /**
     * 验证传入字符串是否为纯中文
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void chinese(String string, int error, String message) {
        regex(string, CHINESE, error, message);
    }

    /**
     * 验证传入字符串是否为身份证号码
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void idCard(String string, int error, String message) {
        regex(string, ID_CARD, error, message);
    }

    /**
     * 验证传入字符串是否为字母、数字和下划线组成,字母不能开头
     * @param string  传入字符串
     * @param error   错误码
     * @param message 错误消息
     */
    public static void require(String string, int error, String message) {
        regex(string, ID_CARD, error, message);
    }
}
