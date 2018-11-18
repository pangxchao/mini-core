package com.mini.util.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * java.util.Date 工具类
 * @author XChao
 */
public final class DateUtil {
    public static final long second = 1000; // 一秒的时间缀
    public static final long minute = second * 60; //  一分钟的时间缀
    public static final long hour = minute * 60; // 一小时的时间缀
    public static final long day = hour * 24; // 一天的时间缀
    public static final long week = day * 7; // 周的时间缀


    /**
     * 将日期格式化为 时间 (format) 格式
     * @param date 日期
     * @param format 时间格式
     */
    public static String format(Date date, String format) {
        if(date == null || format == null)return null;
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将日期格式化为 时间 (format) 格式
     * @param date 日期
     * @param format 时间格式
     */
    public static String format(long date, String format) {
        return format(new Date(date), format);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     * @param date
     */
    public static String formatDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }


    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     * @param date
     */
    public static String formatDate(long date) {
        return formatDate(new Date(date));
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     * @param date
     */
    public static String formatDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     * @param date
     */
    public static String formatDateTime(long date) {
        return formatDateTime(new Date(date));
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     * @param date
     */
    public static String formatTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     * @param date
     */
    public static String formatTime(long date) {
        return formatTime(new Date(date));
    }

    /**
     * 将日期格式的字符串转化成日期
     * @param date
     * @param format
     * @return
     */
    public static Date parse(String date, String format)  {
        try {
            if (date == null || format == null) return null;
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将 yyyy-MM-dd 日期格式的字符串转换成日期格式
     * @param date
     */
    public static Date parseDate(String date) {
        return parse(date, "yyyy-MM-dd");
    }

    /**
     * 将(HH:mm:ss)时间格式的字符串转换成日期格式
     * @param date
     */
    public static Date parseTime(String date) {
        return parse(date, "HH:mm:ss");
    }

    /**
     * 将(yyyy-MM-dd HH:mm:ss)时间格式的字符串转换成日期格式
     * @param date
     */
    public static Date parseDateTime(String date) {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取两时间相关的天数
     * @param start
     * @param end
     * @return
     */
    public static int differDay(long start, long end) {
        return (int) (end / day - start / day);
    }

    /**
     * 获得与当前系统时间的相差天数
     * @param date
     * @return 相差天数 如果传入时间大于当前系统时间为负数
     */
    public static int differDay(long date) {
        return differDay(date, System.currentTimeMillis());

    }

    /**
     * 获得与当前系统时间的相差月数
     * @param date
     * @return 相差月数 如果传入时间大于当前系统时间为负数
     */
    public static long compareMonth(long date) {
        Calendar now = Calendar.getInstance();
        Calendar input = Calendar.getInstance();
        input.setTimeInMillis(date);
        int yearnow = now.get(Calendar.YEAR);
        int monthnow = now.get(Calendar.MONTH);
        int yearinput = input.get(Calendar.YEAR);
        int monthinput = input.get(Calendar.MONTH);
        return yearnow * 12 + monthnow - yearinput * 12 - monthinput;
    }

    /**
     * 计算年龄
     * @param date 出生日期
     * @return
     */
    public static int calcAge(Date date) {
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR);
        now.setTime(date);
        return age - now.get(Calendar.YEAR);
    }

    /**
     * 根据传入时间 获得本周第一天（周一)的时间
     * @param date
     * @return 返回时间 时分秒毫秒为0
     */
    public static long getFristDayForWeek(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int tempday = calendar.get(Calendar.DAY_OF_WEEK);
        switch (tempday) {
            case 1:
                // 星期天
                calendar.add(Calendar.DATE, -6);
                break;
            default:
                // 周一到周六
                calendar.add(Calendar.DATE, -(tempday - 2));
                break;
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * 根据传入时间 获得本周第一天（周一)的时间
     * @return 返回时间 时分秒毫秒为0
     */
    public static long getFristDayForWeek() {
        return getFristDayForWeek(System.currentTimeMillis());
    }

    /**
     * 根据传入时间 获得本周最后一天（周日)的时间
     * @param date
     * @return 返回时间 时分秒毫秒为23:59:59 999
     */
    public static long getLastDayForWeek(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int tempday = calendar.get(Calendar.DAY_OF_WEEK);
        switch (tempday) {
            case 1:
                // 星期天
                break;
            default:
                // 周一到周六
                calendar.add(Calendar.DATE, 8 - tempday);
                break;
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime().getTime();
    }

    /**
     * 根据传入时间 获得本周最后一天（周日)的时间
     * @return 返回时间 时分秒毫秒为23:59:59 999
     */
    public static long getLastDayForWeek() {
        return getLastDayForWeek(System.currentTimeMillis());
    }

    /**
     * 根据传入时间 获得此时间所在月的 第一天的日期
     * @param date
     * @return
     */
    public static Date getMonthFristDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 根据传入时间 获得此时间所在月的 最后一天的日期
     * @param date
     * @return
     */
    public static Date getMonthLastDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 根据传入时间 获得此时间所在上个月的 第一天的日期
     * @param date
     * @return
     */
    public static Date getPervMonthFristDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 根据传入时间 获得此时间所在上个月的 最后一天的日期
     * @param date
     * @return
     */
    public static Date getPervMonthLastDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 根据传入时间 获得此时间所在年的 第一天的日期
     * @param date
     * @return
     */
    public static Date getYearFristDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 根据传入时间 获得此时间所在年的 最后一天的日期
     * @param date
     * @return
     */
    public static Date getYearLastDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }


    /**
     * 根据偏移量返回一天中最早的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2011-02-03 00:00:00
     * </p>
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static Date getEarliestOfDay(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DATE, param);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回一周中最早的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2011-02-07 00:00:00
     * </p>
     * @param date
     * @param param 加的周数，负数为提前的周数
     * @return
     */
    public static Date getEarliestOfWeek(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.WEEK_OF_YEAR, param);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回一月中最早的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2011-03-01 00:00:00
     * </p>
     * @param date
     * @param param 加的月数，负数为提前的月数
     * @return
     */
    public static Date getEarliestOfMonth(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MONTH, param);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回一天中最晚的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2011-02-03 23:59:59
     * </p>
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static Date getLatestOfDay(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.DATE, param);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回一周中最晚的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2011-02-13 23:59:59
     * </p>
     * @param date
     * @param param 加的周数，负数为提前的周数
     * @return
     */
    public static Date getLatestOfWeek(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.WEEK_OF_YEAR, param);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回一月中最晚的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2011-03-31 23:59:59
     * </p>
     * @param date
     * @param param 加的月数，负数为提前的月数
     * @return
     */
    public static Date getLatestOfMonth(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.MONTH, param);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 返回是否星期一
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static boolean isMonday(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }

    /**
     * 返回是否每个月的第一天
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static boolean isFirstDayOfMonth(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        return calendar.get(Calendar.DAY_OF_MONTH) == 1;
    }

    /**
     * 获得当前月的第一天
     * @return
     */
    public static Date getFirstDayOfMonth() {
        return getFirstDayOfMonth(new Date(), 0);
    }

    /**
     * 根据偏移量返回日期
     * @param date
     * @param param
     * @return param 加的天数，负数为提前的天数
     */
    public static Date getDateOfDay(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回日期
     * @param date
     * @param param
     * @return param 加的周数，负数为提前的周数
     */
    public static Date getDateOfWeek(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, param);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回日期
     * @param date
     * @param param
     * @return param 加的月数，负数为提前的月数
     */
    public static Date getDateOfMonth(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, param);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回星期一的日期
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static Date getMonday(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回星期天的日期
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static Date getSunday(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回月份的第一天
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static Date getFirstDayOfMonth(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回月份的最后一天
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static Date getLastDayOfMonth(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回年份
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static int getYear(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 根据偏移量返回月份
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static int getMonth(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据偏移量返回日期
     * @param date
     * @param param 加的天数，负数为提前的天数
     * @return
     */
    public static int getDate(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, param);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 根据偏移量返回一年中最早的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2012-01-01 00:00:00
     * </p>
     * @param date
     * @param param 加的年数，负数为提前的年数
     * @return
     */
    public static Date getEarliestOfYear(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, param);
        return calendar.getTime();
    }

    /**
     * 根据偏移量返回一年中最晚的时刻
     * <p>
     * 比如：传入的时间为2011-02-02 15:20:30，param为1，则返回2012-12-31 23:59:59
     * </p>
     * @param date
     * @param param 加的年数，负数为提前的年数
     * @return
     */
    public static Date getLatestOfYear(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, param);
        return calendar.getTime();
    }
}
