package com.mini.core.util;


import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;

public class DateFormatUtil extends DateFormatUtils {

	/**
	 * 将日期格式化为 时间 (format) 格式
	 * @param date   日期
	 * @param format 时间格式
	 */
	public static String format(LocalDateTime date, String format) {
		return date.format(ofPattern(format));
	}

	/**
	 * 将日期格式化为 时间 (format) 格式
	 * @param date   日期
	 * @param format 时间格式
	 */
	public static String format(LocalDate date, String format) {
		return date.format(ofPattern(format));
	}

	/**
	 * 将日期格式化为 时间 (format) 格式
	 * @param date   日期
	 * @param format 时间格式
	 */
	public static String format(LocalTime date, String format) {
		return date.format(ofPattern(format));
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatDate(long date) {
		return formatDate(new Date(date));
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatDate(Calendar date) {
		return formatDate(date.getTime());
	}

	/**
	 * 将日期格式化为 时间 (format) 格式
	 * @param date 日期
	 */
	public static String formatDate(LocalDate date) {
		return date.format(ofPattern("yyyy-MM-dd"));
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatDateTime(long date) {
		return formatDateTime(new Date(date));
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatDateTime(Calendar date) {
		return formatDateTime(date.getTime());
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatDateTime(LocalDateTime date) {
		return date.format(ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 将日期格式化成：HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 将日期格式化成：HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatTime(long date) {
		return formatTime(new Date(date));
	}

	/**
	 * 将日期格式化成：HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatTime(Calendar date) {
		return formatTime(date.getTime());
	}

	/**
	 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
	 * @param date 日期
	 * @return 格式化结果
	 */
	public static String formatTime(LocalTime date) {
		return date.format(ofPattern("HH:mm:ss"));
	}

	/**
	 * 将日期格式的字符串转化成日期
	 * @param date   日期字符串
	 * @param format 字符串格式
	 * @return 日期
	 */
	public static Date parse(String date, String format) {
		try {
			if (date == null || format == null) {
				return null;
			}
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 yyyy-MM-dd 日期格式的字符串转换成日期格式
	 * @param date 日期字符串
	 * @return 日期
	 */
	public static Date parseDate(String date) {
		return parse(date, "yyyy-MM-dd");
	}

	/**
	 * 将(HH:mm:ss)时间格式的字符串转换成日期格式
	 * @param date 日期字符串
	 * @return 日期
	 */
	public static Date parseTime(String date) {
		return parse(date, "HH:mm:ss");
	}

	/**
	 * 将(yyyy-MM-dd HH:mm:ss)时间格式的字符串转换成日期格式
	 * @param date 日期字符串
	 * @return 日期
	 */
	public static Date parseDateTime(String date) {
		return parse(date, "yyyy-MM-dd HH:mm:ss");
	}
}
