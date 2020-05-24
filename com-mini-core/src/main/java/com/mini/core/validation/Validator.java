package com.mini.core.validation;

import com.mini.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BiConsumer;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

public final class Validator implements EventListener {
	private List<Object> args;
	private final int status;
	private String message;
	
	private Validator(int status) {
		this.status = status;
	}
	
	private ValidationException getValidationException() throws Error {
		return new ValidationException(status, message, args);
	}
	
	public static Validator status(int status) {
		return new Validator(status);
	}
	
	public final Validator message(String message) {
		this.message = message;
		return this;
	}
	
	public final Validator args(Object... args) {
		if (nonNull(args) && args.length > 0) {
			this.args = Arrays.asList(args);
		}
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
	 * 发送错误码和错误消息到回调方法，支持传入同际化处理
	 * @param bundle   国际化 ResourceBundle 对象
	 * @param consumer 回调方法
	 */
	public final void send(ResourceBundle bundle, @Nonnull BiConsumer<Integer, String> consumer) {
		var m = Optional.ofNullable(bundle).filter(b -> b.containsKey(getKey())) //
				.map(b -> b.getString(getKey())).orElse(this.getMessage());
		consumer.accept(status, format(m, Optional.ofNullable(args) //
				.map(List::toArray).orElse(new Object[0])));
	}
	
	/**
	 * 直接发送错误消息
	 */
	public final ValidationException send() {
		throw getValidationException();
	}
	
	
	/**
	 * 当表达式不为 True 是发送错误消息
	 * @param expression 条件表达式
	 */
	public final void is(boolean expression) {
		if (!expression) {
			send();
		}
	}
	
	/**
	 * 验证字符串与正则表达式是否匹配
	 * @param string 字符串
	 * @param regex  正则表达式
	 */
	public final void isPattern(String string, String regex) {
		is(StringUtil.isPattern(string, regex));
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
		is(nonNull(object));
	}
	
	/**
	 * 验证传入对象是否为空
	 * @param object 传入对象
	 */
	public final void isNull(Object object) {
		is(Objects.isNull(object));
	}
	
	/**
	 * 验证传入字符串是否为邮箱格式
	 * @param string 传入字符串
	 */
	public final void isEmail(String string) {
		is(StringUtil.isEmpty(string));
	}
	
	/**
	 * 验证传入字符串是否为电话号码
	 * @param string 传入字符串
	 */
	public final void isPhone(String string) {
		is(StringUtil.isPhone(string));
	}
	
	/**
	 * 验证传入字符串是否为手机号
	 * @param string 传入字符串
	 */
	public final void isMobile(String string) {
		is(StringUtil.isMobile(string));
	}
	
	/**
	 * 验证传入字符串是否为手机号或者电话号码
	 * @param string 传入字符串
	 */
	public final void isMobilePhone(String string) {
		is(StringUtil.isMobilePhone(string));
	}
	
	/**
	 * 验证传入字符串是否为纯英文字母
	 * @param string 传入字符串
	 */
	public final void isLetter(String string) {
		is(StringUtil.isLetter(string));
	}
	
	/**
	 * 验证传入字符串是否为纯数字
	 * @param string 传入字符串
	 */
	public final void isNumber(String string) {
		is(StringUtil.isNumber(string));
	}
	
	/**
	 * 验证传入字符串是否为纯中文
	 * @param string 传入字符串
	 */
	public final void isChinese(String string) {
		is(StringUtil.isChinese(string));
	}
	
	/**
	 * 验证传入字符串是否为身份证号码
	 * @param string 传入字符串
	 */
	public final void isIdCard(String string) {
		is(StringUtil.isIdCard(string));
	}
	
	/**
	 * 验证传入字符串是否为字母、数字和下划线组成，数字不能开头
	 * @param string 传入字符串
	 */
	public final void isRequire(String string) {
		is(StringUtil.isRequire(string));
	}
	
}
