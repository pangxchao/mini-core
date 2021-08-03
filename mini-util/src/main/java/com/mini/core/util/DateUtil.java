package com.mini.core.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static org.apache.commons.lang3.time.DateUtils.*;

/**
 * Mini Date Utils 工具类
 *
 * @author pangchao
 */
public final class DateUtil extends DateFormatUtils {

    /**
     * 日期-时间默认格式化字符串
     */
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期默认格式化字符串
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间默认格式化字符串
     */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 一秒的时间戳
     */
    public static final long SECOND = MILLIS_PER_SECOND;

    /**
     * 一分钟的时间戳
     */
    public static final long MINUTE = MILLIS_PER_MINUTE;

    /**
     * 一小时的时间戳
     */
    public static final long HOUR = MILLIS_PER_HOUR;

    /**
     * 一天的时间戳
     */
    public static final long DAY = MILLIS_PER_DAY;

    /**
     * 一天的时间戳
     */
    public static final long WEEK = DAY * 7;

    /**
     * 将日期格式化为 时间 (format) 格式
     *
     * @param date   日期
     * @param format 时间格式
     */
    public static String format(LocalDateTime date, String format) {
        return date.format(ofPattern(format));
    }

    /**
     * 将日期格式化为 时间 (format) 格式
     *
     * @param date   日期
     * @param format 时间格式
     */
    public static String format(LocalDate date, String format) {
        return date.format(ofPattern(format));
    }

    /**
     * 将日期格式化为 时间 (format) 格式
     *
     * @param date   日期
     * @param format 时间格式
     */
    public static String format(LocalTime date, String format) {
        return date.format(ofPattern(format));
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDateTime(LocalDateTime date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDateTime(Calendar date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDateTime(Date date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDateTime(Long date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * 将日期格式化为 时间 (format) 格式
     *
     * @param date 日期
     */
    public static String formatDate(LocalDate date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDate(Calendar date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDate(Long date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatTime(LocalTime date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatTime(Date date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatTime(Long date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     *
     * @param date 日期
     * @return 格式化结果
     */
    public static String formatTime(Calendar date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 将给定格式的日期字符串转换成日期对象
     *
     * @param date   日期字符串
     * @param format 格式化字符串
     * @return 转换结果
     */
    @SneakyThrows
    public static Date parse(String date, String format) {
        return DateUtils.parseDate(date, format);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss时间格式的字符串转换成日期格式
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static Date parseDateTime(String date) {
        return parse(date, DATETIME_PATTERN);
    }

    /**
     * 将yyyy-MM-dd日期格式的字符串转换成日期格式
     *
     * @param date 日期字符串
     * @return 转换结果
     */
    public static Date parseDate(String date) {
        return parse(date, DATE_PATTERN);
    }

    /**
     * 将HH:mm:ss时间格式的字符串转换成日期格式
     *
     * @param date 日期字符串
     * @return 转换结果
     */
    public static Date parseTime(String date) {
        return parse(date, TIME_PATTERN);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getStartOfSecond(Calendar date) {
        return truncate(date, Calendar.SECOND);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getStartOfSecond(Date date) {
        return truncate(date, Calendar.SECOND);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getEndOfSecond(Calendar date) {
        return ceiling(date, Calendar.SECOND);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getEndOfSecond(Date date) {
        return ceiling(date, Calendar.SECOND);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getStartOfMinute(Calendar date) {
        return truncate(date, Calendar.MINUTE);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getStartOfMinute(Date date) {
        return truncate(date, Calendar.MINUTE);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getEndOfMinute(Calendar date) {
        return ceiling(date, Calendar.MINUTE);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getEndOfMinute(Date date) {
        return ceiling(date, Calendar.MINUTE);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getStartOfHour(Date date) {
        return truncate(date, HOUR_OF_DAY);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getStartOfHour(Calendar date) {
        return truncate(date, HOUR_OF_DAY);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getEndOfHour(Date date) {
        return ceiling(date, HOUR_OF_DAY);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getStartOfDay(Date date) {
        return truncate(date, DAY_OF_MONTH);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getStartOfDay(Calendar date) {
        return truncate(date, DAY_OF_MONTH);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getEndOfDay(Date date) {
        return ceiling(date, DAY_OF_MONTH);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getStartOfMonth(Calendar date) {
        return truncate(date, Calendar.MONTH);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getStartOfMonth(Date date) {
        return truncate(date, Calendar.MONTH);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getEndOfMonth(Calendar date) {
        return ceiling(date, Calendar.MONTH);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getEndOfMonth(Date date) {
        return ceiling(date, Calendar.MONTH);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getStartOfYear(Calendar date) {
        return truncate(date, Calendar.YEAR);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getStartOfYear(Date date) {
        return truncate(date, Calendar.YEAR);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Calendar getEndOfYear(Calendar date) {
        return ceiling(date, Calendar.YEAR);
    }

    /**
     * 获取一秒中最早的时刻
     *
     * @return 计算结果
     */
    public static Date getEndOfYear(Date date) {
        return ceiling(date, Calendar.YEAR);
    }

    /**
     * 私有构造方法
     */
    private DateUtil() {
    }
}
