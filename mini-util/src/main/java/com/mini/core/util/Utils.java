package com.mini.core.util;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static org.apache.commons.lang3.time.DateFormatUtils.format;
import static org.apache.commons.lang3.time.DateUtils.*;

/**
 * Mini Utils 工具类
 * <p>
 * 正则表达式匹配规则
 * </p>
 * <ul>
 *     <li>*: 0 次或者多次</li>
 *     <li>+: 一次或者多次</li>
 *     <li>+: 一次或者多次</li>
 *     <li>?: 0次或者1次</li>
 *     <li>{n}:刚刚n次</li>
 *     <li>{n,m}: n到m次</li>
 * </ul>
 * <p>正则表达式快速匹配</p>
 * <ul>
 *     <li>\d: [0-9]</li>
 *     <li>\D: [^0-9]</li>
 *     <li>\w:[a-zA-Z_0-9]</li>
 *     <li>\W:[^a-zA-Z_0-9]</li>
 *     <li>\s: [\t\n\r\f]</li>
 *     <li>\S: [^\t\n\r\f]</li>
 * </ul>
 *
 * @author pangchao
 */
public final class Utils {


    /**
     * 中国大陆身份证号正则表达式
     */
    public static final String ID_CARD_REGEX = "^\\d{15}(\\d{2}[A-Za-z0-9])?$";

    /**
     * 能用邮件地址正则表达式
     */
    public static final String EMAIL_REGEX = "^\\S+[@]\\S+[.]\\S+$";

    /**
     * 中文正则表达式
     */
    public static final String CHINESE_REGEX = "^[\u4E00-\u9FA5]+$";

    /**
     * 一般用户名正则表达式
     */
    public static final String REQUIRE_REGEX = "^[a-z_][a-z0-9_]*$";

    /**
     * 纯数字正则表达式
     */
    public static final String NUMBER_REGEX = "^\\d+$";

    /**
     * 纯英文正则表达式
     */
    public static final String LETTER_REGEX = "^\\w+$";

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
     * 日期-时间格式化
     *
     * @param date 日期-时间对象
     * @return 结果化结果
     */
    public static String formatDatetime(Calendar date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * 日期-时间格式化
     *
     * @param date 日期-时间对象
     * @return 结果化结果
     */
    public static String formatDatetime(Date date) {
        return format(date, DATETIME_PATTERN);
    }

    /**
     * 日期-时间格式化
     *
     * @param date 日期-时间对象
     * @return 结果化结果
     */
    public static String formatDatetime(long date) {
        return format(date, DATETIME_PATTERN);
    }


    /**
     * 日期格式化
     *
     * @param date 日期对象
     * @return 结果化结果
     */
    public static String formatData(Calendar date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化
     *
     * @param date 日期对象
     * @return 结果化结果
     */
    public static String formatData(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化
     *
     * @param date 日期对象
     * @return 结果化结果
     */
    public static String formatData(long date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 时间格式化
     *
     * @param date 时间对象
     * @return 结果化结果
     */
    public static String formatTime(Calendar date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 时间格式化
     *
     * @param date 时间对象
     * @return 结果化结果
     */
    public static String formatTime(Date date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 时间格式化
     *
     * @param date 时间对象
     * @return 结果化结果
     */
    public static String formatTime(long date) {
        return format(date, TIME_PATTERN);
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
     * 将byte数组转换成base64的字符串编码
     *
     * @param bytes byte数组
     * @return 转换结果
     */
    public static String base64Encoder(byte[] bytes) {
        Base64.Encoder code = Base64.getEncoder();
        return code.encodeToString(bytes);
    }


}
