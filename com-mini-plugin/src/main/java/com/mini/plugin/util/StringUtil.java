package com.mini.plugin.util;

import java.net.URI;
import java.net.URISyntaxException;

public final class StringUtil extends com.intellij.openapi.util.text.StringUtil {
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
			if (i > 0 && Character.isUpperCase(self.charAt(i))) {
				result.append("_");
			}
			result.append(Character.toLowerCase(ch));
		}
		return result.toString();
	}
	
	public static String toFieldName(String name, String prefix) {
		return name.replaceFirst(getRegex(prefix), "");
	}
	
	/**
	 * Gets the value of REGEX.
	 * @return the value of REGEX
	 */
	private static String getRegex(String prefix) {
		return "((" + prefix + ")(_)*)";
	}
}
