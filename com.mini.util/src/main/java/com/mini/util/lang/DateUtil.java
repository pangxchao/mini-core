package com.mini.util.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * java.util.Date 工具类
 *
 * @author XChao
 */
public final class DateUtil {
    public static final long second = 1000; // 一秒的时间缀
    public static final long minute = second * 60; //  一分钟的时间缀
    public static final long hour = minute * 60; // 一小时的时间缀
    public static final long day = hour * 24; // 一天的时间缀
    public static final long week = day * 7; // 一周的时间缀

    /**
     * 自定义格式化类
     *
     * @author xchao
     */
    private static class MySimpleDateFormat extends SimpleDateFormat {
        public MySimpleDateFormat(String format) {
            super(format, Locale.getDefault());
        }

        @Override
        public Date parse(String source) {
            try {
                return super.parse(source);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 将日期格式化为 时间 (format) 格式
     *
     * @param date   日期
     * @param format 时间格式
     */
    public static String format(Date date, String format) {
        if (date == null || format == null) return null;
        return new MySimpleDateFormat(format).format(date);
    }

    /**
     * 将日期格式化为 时间 (format) 格式
     *
     * @param date   日期
     * @param format 时间格式
     */
    public static String format(long date, String format) {
        return format(new Date(date), format);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     *
     * @param date
     */
    public static String formatDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }


    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     *
     * @param date
     */
    public static String formatDate(long date) {
        return formatDate(new Date(date));
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date
     */
    public static String formatDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     *
     * @param date
     */
    public static String formatDateTime(long date) {
        return formatDateTime(new Date(date));
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     *
     * @param date
     */
    public static String formatTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     *
     * @param date
     */
    public static String formatTime(long date) {
        return formatTime(new Date(date));
    }

    /**
     * 将日期格式的字符串转化成日期
     *
     * @param date
     * @param format
     * @return
     */
    public static Date parse(String date, String format) {
        if (date == null || format == null) return null;
        return new MySimpleDateFormat(format).parse(date);
    }

    /**
     * 将 yyyy-MM-dd 日期格式的字符串转换成日期格式
     *
     * @param date
     */
    public static Date parseDate(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 将(HH:mm:ss)时间格式的字符串转换成日期格式
     *
     * @param date
     */
    public static Date parseTime(String date) {
        return parse(date, "HH:mm:ss");
    }

    /**
     * 将(yyyy-MM-dd HH:mm:ss)时间格式的字符串转换成日期格式
     *
     * @param date
     */
    public static Date parseDateTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }
}
