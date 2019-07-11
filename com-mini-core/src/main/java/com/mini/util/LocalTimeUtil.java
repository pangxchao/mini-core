package com.mini.util;

import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ofPattern;

public class LocalTimeUtil {


    /**
     * 格式化LocalTime对象
     * @param time   LocalTime对象
     * @param format 格式化字符串
     * @return 格式化结果
     */
    public static String format(LocalTime time, String format) {
        return time.format(ofPattern(format));
    }

    /**
     * 格式化LocalTime对象
     * @param date LocalTime对象
     * @return 格式化结果
     */
    public static String format(LocalTime date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 将与格式化字符串匹配的字符串转换成LocalTime对象
     * @param time   与格式化字符串匹配的LocalTime字符串
     * @param format 格式化字符串
     * @return 转换结果
     */
    public static LocalTime parse(String time, String format) {
        return LocalTime.parse(time, ofPattern(format));
    }

    /**
     * 将字符串转换成LocalTime对象
     * @param time LocalTime字符串
     * @return 转换结果
     */
    public static LocalTime parse(String time) {
        return parse(time, "HH:mm:ss");
    }
}
