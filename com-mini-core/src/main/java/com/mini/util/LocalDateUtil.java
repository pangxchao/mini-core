package com.mini.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ofPattern;

public class LocalDateUtil {


    /**
     * 格式化LocalDate对象
     * @param date   LocalDate对象
     * @param format 格式化字符串
     * @return 格式化结果
     */
    public static String format(LocalDate date, String format) {
        return date.format(ofPattern(format));
    }

    /**
     * 格式化LocalDate对象
     * @param date LocalDate对象
     * @return 格式化结果
     */
    public static String format(LocalDate date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 将与格式化字符串匹配的字符串转换成LocalDate对象
     * @param date   与格式化字符串匹配的LocalDate字符串
     * @param format 格式化字符串
     * @return 转换结果
     */
    public static LocalDate parse(String date, String format) {
        return LocalDate.parse(date, ofPattern(format));
    }

    /**
     * 将字符串转换成LocalDate对象
     * @param date LocalDate字符串
     * @return 转换结果
     */
    public static LocalDate parse(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 将LocalDate对象转换成Instant对象
     * @param date   LocalDate对象
     * @param offset ZoneOffset对象
     * @return Instant对象
     */
    public static Instant toInstant(LocalDate date, ZoneOffset offset) {
        return date.atStartOfDay(offset).toInstant();
    }

    /**
     * 将LocalDate对象转换成Instant对象
     * @param date LocalDate对象
     * @return Instant对象
     */
    public static Instant toUTCInstant(LocalDate date) {
        return toInstant(date, ZoneOffset.UTC);
    }

    /**
     * 将LocalDate对象转换成时间戳
     * @param date   LocalDate对象
     * @param offset ZoneOffset对象
     * @return 时间戳
     */
    public static long toMillis(LocalDate date, ZoneOffset offset) {
        return toInstant(date, offset).toEpochMilli();
    }

    /**
     * 将LocalDate对象转换成时间戳
     * @param date LocalDate对象
     * @return 时间戳
     */
    public static long toUTCMillis(LocalDate date) {
        return toMillis(date, ZoneOffset.UTC);
    }

    /**
     * 将LocalDate对象转换成Date对象
     * @param date   LocalDate对象
     * @param offset ZoneOffset对象
     * @return Date对象
     */
    public static Date toDate(LocalDate date, ZoneOffset offset) {
        return Date.from(toInstant(date, offset));
    }

    /**
     * 将LocalDate对象转换成Date对象
     * @param date LocalDate对象
     * @return Date对象
     */
    public static Date toUTCDate(LocalDate date) {
        return toDate(date, ZoneOffset.UTC);
    }

    /**
     * 将时间戳转换成LocalDate对象
     * @param millis 时间戳
     * @param offset ZoneOffset对象
     * @return LocalDate对象
     */
    public static LocalDate valueOf(long millis, ZoneOffset offset) {
        return Instant.ofEpochMilli(millis).atZone(offset).toLocalDate();
    }

    /**
     * 将时间戳转换成LocalDate对象
     * @param millis 时间戳
     * @return LocalDate对象
     */
    public static LocalDate valueOfUTC(long millis) {
        return valueOf(millis, ZoneOffset.UTC);
    }

    /**
     * 将Date对象转换成LocalDate对象
     * @param date   Date对象
     * @param offset ZoneOffset对象
     * @return LocalDate对象
     */
    public static LocalDate valueOf(Date date, ZoneOffset offset) {
        return date.toInstant().atZone(offset).toLocalDate();
    }

    /**
     * 将Date对象转换成LocalDate对象
     * @param date Date对象
     * @return LocalDate对象
     */
    public static LocalDate valueOfUTC(Date date) {
        return valueOf(date, ZoneOffset.UTC);
    }
}
