package com.mini.core.validate;

import com.mini.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

public final class Validator implements EventListener {
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
	
	private List<Object> args;
	private final int status;
	private String message;
	
	private Validator(int status) {
		this.status = status;
	}
	
	public static Validator status(int status) {
		return new Validator(status);
	}
	
	public Validator message(String message) {
		this.message = message;
		return this;
	}
	
	public Validator args(Object... args) {
		this.args = Arrays.asList(args);
		return this;
	}
	
	public final List<Object> getArgs() {
		return args;
	}
	
	public final String getMessage() {
		return message;
	}
	
	public final int getStatus() {
		return status;
	}
	
	public final String getKey() {
		return "" + status;
	}
	
	/**
	 * 直接发送错误消息
	 */
	public final void send() {
		throw new ValidateException(status, message, args);
	}
	
	/**
	 * 当表达式不为 True 是发送错误消息
	 * @param expression 条件表达式
	 */
	public final void is(boolean expression) {
		if (!expression) send();
	}
	
	/**
	 * 验证字符串与正则表达式是否匹配
	 * @param string 字符串
	 * @param regex  正则表达式
	 */
	public final void isRegex(String string, String regex) {
		is(string != null && string.matches(regex));
	}
	
	/**
	 * 验证传入字符串是否有值
	 * @param string 传入字符串
	 */
	public final void isNotBlank(String string) {
		is(StringUtils.isNotBlank(string));
	}
	
	/**
	 * 验证传入对象是否为空
	 * @param object 传入对象
	 */
	public final void isNotNull(Object object) {
		is(object != null);
	}
	
	/**
	 * 验证传入对象是否为空
	 * @param object 传入对象
	 */
	public final void isNull(Object object) {
		is(object == null);
	}
	
	/**
	 * 验证传入字符串是否为邮箱格式
	 * @param string 传入字符串
	 */
	public final void isEmail(String string) {
		isRegex(string, EMAIL);
	}
	
	/**
	 * 验证传入字符串是否为电话号码
	 * @param string 传入字符串
	 */
	public final void isPhone(String string) {
		isRegex(string, PHONE);
	}
	
	/**
	 * 验证传入字符串是否为手机号
	 * @param string 传入字符串
	 */
	public final void isMobile(String string) {
		isRegex(string, MOBILE);
	}
	
	/**
	 * 验证传入字符串是否为手机号或者电话号码
	 * @param string 传入字符串
	 */
	public final void isMobilePhone(String string) {
		if (StringUtil.isBlank(string)) {
			Validator.this.send();
			return;
		}
		if (string.matches(MOBILE)) {
			return;
		}
		is(string.matches(PHONE));
	}
	
	/**
	 * 验证传入字符串是否为纯英文字母
	 * @param string 传入字符串
	 */
	public final void isLetter(String string) {
		isRegex(string, LETTER);
	}
	
	/**
	 * 验证传入字符串是否为纯数字
	 * @param string 传入字符串
	 */
	public final void isNumber(String string) {
		isRegex(string, NUMBER);
	}
	
	/**
	 * 验证传入字符串是否为纯中文
	 * @param string 传入字符串
	 */
	public final void isChinese(String string) {
		isRegex(string, CHINESE);
	}
	
	/**
	 * 验证传入字符串是否为身份证号码
	 * @param string 传入字符串
	 */
	public final void isIdCard(String string) {
		isRegex(string, ID_CARD);
	}
	
	/**
	 * 验证传入字符串是否为字母、数字和下划线组成，数字不能开头
	 * @param string 传入字符串
	 */
	public final void isRequire(String string) {
		isRegex(string, ID_CARD);
	}
	
}
