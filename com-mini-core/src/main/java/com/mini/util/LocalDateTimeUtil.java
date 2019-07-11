package com.mini.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;

public class LocalDateTimeUtil {

    /**
     * 格式化LocalDateTime对象
     * @param dateTime LocalDateTime对象
     * @param format   格式化字符串
     * @return 格式化结果
     */
    public static String format(LocalDateTime dateTime, String format) {
        return dateTime.format(ofPattern(format));
    }

    /**
     * 格式化LocalDateTime对象
     * @param dateTime LocalDateTime对象
     * @return 格式化结果
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将与格式化字符串匹配的字符串转换成LocalDateTime对象
     * @param dateTime 与格式化字符串匹配的LocalDateTime字符串
     * @param format   格式化字符串
     * @return 转换结果
     */
    public static LocalDateTime parse(String dateTime, String format) {
        return LocalDateTime.parse(dateTime, ofPattern(format));
    }

    /**
     * 将字符串转换成LocalDateTime对象
     * @param dateTime LocalDateTime字符串
     * @return 转换结果
     */
    public static LocalDateTime parse(String dateTime) {
        return parse(dateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将LocalDateTime对象转换成Instant对象
     * @param dateTime LocalDateTime对象
     * @param offset   ZoneOffset对象
     * @return Instant对象
     */
    public static Instant toInstant(LocalDateTime dateTime, ZoneOffset offset) {
        return dateTime.toInstant(offset);
    }

    /**
     * 将LocalDateTime对象转换成Instant对象
     * @param dateTime LocalDateTime对象
     * @return Instant对象
     */
    public static Instant toUTCInstant(LocalDateTime dateTime) {
        return toInstant(dateTime, ZoneOffset.UTC);
    }

    /**
     * 将LocalDateTime对象转换成时间戳
     * @param dateTime LocalDateTime对象
     * @param offset   ZoneOffset对象
     * @return 时间戳
     */
    public static long toMillis(LocalDateTime dateTime, ZoneOffset offset) {
        return toInstant(dateTime, offset).toEpochMilli();
    }

    /**
     * 将LocalDateTime对象转换成时间戳
     * @param dateTime LocalDateTime对象
     * @return 时间戳
     */
    public static long toUTCMillis(LocalDateTime dateTime) {
        return toMillis(dateTime, ZoneOffset.UTC);
    }

    /**
     * 将LocalDateTime对象转换成Date对象
     * @param dateTime LocalDateTime对象
     * @param offset   ZoneOffset对象
     * @return Date对象
     */
    public static Date toDate(LocalDateTime dateTime, ZoneOffset offset) {
        return Date.from(toInstant(dateTime, offset));
    }

    /**
     * 将LocalDateTime对象转换成Date对象
     * @param dateTime LocalDateTime对象
     * @return Date对象
     */
    public static Date toUTCDate(LocalDateTime dateTime) {
        return toDate(dateTime, ZoneOffset.UTC);
    }

    /**
     * 将时间戳转换成LocalDateTime对象
     * @param millis 时间戳
     * @param offset ZoneOffset对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime valueOf(long millis, ZoneOffset offset) {
        return Instant.ofEpochMilli(millis).atZone(offset).toLocalDateTime();
    }

    /**
     * 将时间戳转换成LocalDateTime对象
     * @param millis 时间戳
     * @return LocalDateTime对象
     */
    public static LocalDateTime valueOfUTC(long millis) {
        return valueOf(millis, ZoneOffset.UTC);
    }

    /**
     * 将Date对象转换成LocalDateTime对象
     * @param date   Date对象
     * @param offset ZoneOffset对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime valueOf(Date date, ZoneOffset offset) {
        return date.toInstant().atZone(offset).toLocalDateTime();
    }

    /**
     * 将Date对象转换成LocalDateTime对象
     * @param date Date对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime valueOfUTC(Date date) {
        return valueOf(date, ZoneOffset.UTC);
    }
}
