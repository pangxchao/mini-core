/**
 * Created the com.cfinal.util.lang.CFString.java
 * @created 2016年10月18日 上午11:19:03
 * @version 1.0.0
 */
package com.cfinal.util.lang;

import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.cfinal.util.CFHashGenerator;
import com.cfinal.web.central.CFInitialize;

/**
 * java.lang.String 工具类
 * @author XChao
 */
public class CFString {

	/**
	 * 字符串替换的回调接口
	 * @author XChao
	 */
	public static interface XReplace {
		/**
		 * 将text转化为特定的字串返回
		 * @param text 指定的字符串
		 * @param index 替换的次序
		 * @param matcher Matcher对象
		 * @return
		 */
		public String replace(String text, int index, Matcher matcher);
	}

	/**
	 * 将String中的所有regex匹配的字串全部替换掉
	 * @param string 代替换的字符串
	 * @param regex 替换查找的正则表达式
	 * @param replacement 替换函数
	 * @return
	 */
	public static String replaceAll(String string, String regex, XReplace replacement) {
		return replaceAll(string, Pattern.compile(regex), replacement);
	}

	/**
	 * 将String中的所有pattern匹配的字串替换掉
	 * @param string 代替换的字符串
	 * @param pattern 替换查找的正则表达式对象
	 * @param replacement 替换函数
	 * @return
	 */
	public static String replaceAll(String string, Pattern pattern, XReplace replacement) {
		if(string == null) {
			return null;
		}
		Matcher matcher = pattern.matcher(string);
		StringBuffer buffer = new StringBuffer();
		int index = 0;
		while (matcher.find()) {
			matcher.appendReplacement(buffer, replacement.replace(matcher.group(0), index++, matcher));
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	/**
	 * 将String中的regex第一次匹配的字串替换掉
	 * @param string 代替换的字符串
	 * @param regex 替换查找的正则表达式
	 * @param replacement 替换函数
	 * @return
	 */
	public static String replaceFirst(String string, String regex, XReplace replacement) {
		return replaceFirst(string, Pattern.compile(regex), replacement);
	}

	/**
	 * 将String中的pattern第一次匹配的字串替换掉
	 * @param string 代替换的字符串
	 * @param pattern 替换查找的正则表达式对象
	 * @param replacement 替换函数
	 * @return
	 */
	public static String replaceFirst(String string, Pattern pattern, XReplace replacement) {
		if(string == null) {
			return null;
		}
		Matcher matcher = pattern.matcher(string);
		StringBuffer buffer = new StringBuffer();
		if(matcher.find()) {
			matcher.appendReplacement(buffer, replacement.replace(matcher.group(0), 0, matcher));
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}

	/**
	 * 获取指定长度的字符串（包括汉字长度的处理）
	 * @param value 源字符串
	 * @param length 自定长度
	 * @return 返回一个指定长度的字符串
	 */
	public static String subLength(String value, int length) {
		return subLength(value, length, "...");
	}

	/**
	 * 获取指定长度的字符串（包括汉字长度的处理）
	 * @param value
	 * @param length
	 * @param suff
	 * @return
	 */
	public static String subLength(String value, int length, String suff) {
		if(StringUtils.isEmpty(value)) {
			return value;
		}
		int valueLen = value.length();
		if(StringUtils.isNotEmpty(value) && valueLen > length) {
			int size = length * 2;
			for (int i = 0, count = 0; i < valueLen; i++) {
				char ch = value.charAt(i);
				if(ch >= 0 && ch < 256) {
					count++;
				} else {
					count += 2;
				}
				if(count > size) {
					value = value.substring(0, i);
					if(StringUtils.isNotEmpty(suff)) {
						value = value + suff;
					}
					break;
				}
			}
		}
		return value;
	}

	/**
	 * 格式化距离显示
	 * @param dist 距离 单位米
	 */
	public static String formatdist(double dist) {
		if(dist >= 1000) {
			return String.format("%1$.2f", dist / 1000) + "km";
		}
		return String.format("%1$.1f", dist) + "m";
	}

	/**
	 * 获取两经纬度距离
	 * @param lng1 经度1
	 * @param lat1 纬度1
	 * @param lng2 经度2
	 * @param lat2 纬度2
	 * @return
	 */
	public static String distance(double lng1, double lat1, double lng2, double lat2) {
		return formatdist(CFHashGenerator.getInstence().distance(lng1, lat1, lng2, lat2));
	}

	/**
	 * 将字符串转成URL编码
	 * @param stringContent
	 */
	public static String encode(String stringContent) {
		return encode(stringContent, CFInitialize.getContext().getEncoding());
	}

	/**
	 * 将字符串转成URL编码
	 * @param stringContent
	 * @param encode 字符串编码格式
	 */
	public static String encode(String stringContent, String encode) {
		try {
			if(StringUtils.isBlank(stringContent)) {
				return stringContent;
			}
			return URLEncoder.encode(stringContent, encode);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 把传入字符串首字母大写
	 * @param string
	 * @return
	 */
	public static String fristUpperCase(String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1, string.length());
	}

	/**
	 * 把传入字符串首字母小写
	 * @param string
	 * @return
	 */
	public static String fristLowerCase(String string) {
		return Character.toLowerCase(string.charAt(0)) + string.substring(1, string.length());
	}

	/**
	 * 把字符串改写成java的驼峰命名法
	 * @param name 字符串内容， 一般是将单词用"_"连接的字符串
	 * @param fristUpperCase 变成java命名后，首字母是否大写
	 * @return
	 */
	public static String toJavaName(String name, boolean fristUpperCase) {
		String[] strArr = name.split("_");
		StringBuilder newName = new StringBuilder();
		for (int i = 0, len = strArr.length; i < len; i++) {
			if(strArr[i].length() > 0) {
				newName.append(fristUpperCase(strArr[i]));
			}
		}
		if(fristUpperCase == true) {
			return newName.toString();
		}
		return fristLowerCase(newName.toString());
	}

	/**
	 * 把用java驼峰命名法的字符串字符串转换成 用"_"连接的数据库命名
	 * @param fieldName
	 * @return
	 */
	public static String toDBName(String fieldName) {
		StringBuilder result = new StringBuilder();
		char[] fileNameChars = fieldName.toCharArray();
		for (int i = 0, len = fileNameChars.length; i < len; i++) {
			if(Character.isUpperCase(fileNameChars[i]) && i > 0) {
				result.append("_");
			}
			result.append(Character.toLowerCase(fileNameChars[i]));
		}
		return result.toString();
	}

}
