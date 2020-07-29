package com.mini.core.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Map;

import static java.lang.Character.isUpperCase;

public final class StringUtil extends StringUtils {
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
	 * 验证字符串与正则表达式是否匹配
	 * @param string 字符串
	 * @param regex  正则表达式
	 * @return true-是
	 */
	public static boolean isPattern(String string, String regex) {
		return (string != null && string.matches(regex));
	}
	
	
	/**
	 * 验证传入字符串是否为邮箱格式
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isEmail(String string) {
		return isPattern(string, EMAIL);
	}
	
	/**
	 * 验证传入字符串是否为电话号码
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isPhone(String string) {
		return isPattern(string, PHONE);
	}
	
	/**
	 * 验证传入字符串是否为手机号
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isMobile(String string) {
		return isPattern(string, MOBILE);
	}
	
	/**
	 * 验证传入字符串是否为手机号或者电话号码
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isMobilePhone(String string) {
		return isPattern(string, MOBILE) || isPattern(string, PHONE);
	}
	
	/**
	 * 验证传入字符串是否为纯英文字母
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isLetter(String string) {
		return isPattern(string, LETTER);
	}
	
	/**
	 * 验证传入字符串是否为纯数字
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isNumber(String string) {
		return isPattern(string, NUMBER);
	}
	
	/**
	 * 验证传入字符串是否为纯中文
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isChinese(String string) {
		return isPattern(string, CHINESE);
	}
	
	/**
	 * 验证传入字符串是否为身份证号码
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isIdCard(String string) {
		return isPattern(string, ID_CARD);
	}
	
	/**
	 * 验证传入字符串是否为字母、数字和下划线组成，数字不能开头
	 * @param string 传入字符串
	 * @return true-是
	 */
	public static boolean isRequire(String string) {
		return isPattern(string, REQUIRE);
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
			String key = defaultIfBlank(entry.getKey(), "");
			String val = String.valueOf(entry.getValue());
			self = self.replaceAll("\\{" + key + "}", val);
		}
		return self;
	}
	
	/**
	 * 将字符串转换成URL
	 * @param self 字符串内容
	 * @return 转换结果
	 */
	public static URL toURL(String self) {
		try {
			return new URL(self);
		} catch (MalformedURLException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	/**
	 * 将字符串转换成URI
	 * @param self 字符串内容
	 * @return URI
	 */
	public static URI toURI(String self) {
		try {
			return new URI(self);
		} catch (URISyntaxException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	/**
	 * 将字符串转成URL编码
	 * @param self    字符串内容
	 * @param charset 编码方式
	 */
	public static String urlEncode(String self, String charset) {
		try {
			return URLEncoder.encode(self, charset);
		} catch (UnsupportedEncodingException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	/**
	 * - 将字符串转成URL编码
	 * @param self    字符串内容
	 * @param charset 编码方式
	 */
	public static String urlEncode(String self, Charset charset) {
		return URLEncoder.encode(self, charset);
	}
	
	/**
	 * - 将URL编码还原成原来的字符串
	 * @param self    字符串
	 * @param charset 编码
	 */
	public static String urlDecode(String self, String charset) {
		try {
			return URLDecoder.decode(self, charset);
		} catch (UnsupportedEncodingException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	/**
	 * - 将URL编码还原成原来的字符串
	 * @param self    字符串
	 * @param charset 编码
	 */
	public static String urlDecode(String self, Charset charset) {
		return URLDecoder.decode(self, charset);
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
		return self.substring(0, 3) + "****" + self.substring(7);
		
	}
}
