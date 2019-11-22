package com.mini.core.util;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.MILLISECOND;

public class DateUtil extends DateUtils {
    // 一秒的时间戳
    @SuppressWarnings("WeakerAccess")
    public static final long SECOND = 1000;
    // 一分钟的时间戳
    @SuppressWarnings("WeakerAccess")
    public static final long MINUTE = SECOND * 60;
    // 一小时的时间戳
    @SuppressWarnings("WeakerAccess")
    public static final long HOUR = MINUTE * 60;
    // 一天的时间戳
    public static final long DAY = HOUR * 24;
    // 一周的时间戳
    @SuppressWarnings("unused")
    public static final long WEEK = DAY * 7;

    /**
     * 将日期转换成时间戳
     * @param date 日期
     * @return 时间戳
     */
    public static long getTime(Date date) {
        return date == null ? 0 : date.getTime();
    }

    /**
     * 判断日期d是否在date之后
     * @param date 日期
     * @param when 被比较的日期
     * @return date > when
     */
    public static boolean after(Date date, Date when) {
        if (date == null || when == null) return false;
        return date.getTime() > when.getTime();
    }

    /**
     * 判断日期d是否在date之后
     * @param date 日期
     * @param when 被比较的日期
     * @return date > when
     */
    public static boolean afterOrNull(Date date, Date when) {
        if (date == null || when == null) return true;
        return date.getTime() > when.getTime();
    }

    /**
     * 判断日期d是否在date之前
     * @param date 日期
     * @param when 被比较的日期
     * @return date < when
     */
    public static boolean before(Date date, Date when) {
        if (date == null || when == null) return false;
        return date.getTime() < when.getTime();
    }

    /**
     * 判断日期d是否在date之前
     * @param date 日期
     * @param when 被比较的日期
     * @return date < when
     */
    public static boolean beforeOrNull(Date date, Date when) {
        if (date == null || when == null) return true;
        return date.getTime() < when.getTime();
    }

    /**
     * 判断日期d是否在date之前
     * @param date  日期
     * @param start 开始日期
     * @param end   结束日期
     * @return start < date < end
     */
    public static boolean between(Date date, Date start, Date end) {
        return after(date, start) && before(date, end);
    }

    /**
     * 判断日期d是否在date之前
     * @param date  日期
     * @param start 开始日期
     * @param end   结束日期
     * @return start < date < end
     */
    public static boolean betweenOrNull(Date date, Date start, Date end) {
        return afterOrNull(date, start) && beforeOrNull(date, end);
    }

    /**
     * 根据指定日期创建Calendar对象
     * @param date 指定日期
     * @return 日历
     */
    public static Calendar createCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 根据指定日期创建Calendar对象
     * @param date 指定日期
     * @return 日历
     */
    @SuppressWarnings("WeakerAccess")
    public static Calendar createCalendar(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }

    /**
     * 获取一天中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfDay(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一天中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfDay(Date date) {
        return getStartOfDay(createCalendar(date));
    }

    /**
     * 获取一天中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfDay(long date) {
        return getStartOfDay(createCalendar(date));
    }

    /**
     * 获取一天中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("DuplicatedCode")
    public static Date getEndOfDay(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一天中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    public static Date getEndOfDay(Date date) {
        return getEndOfDay(createCalendar(date));
    }

    /**
     * 获取一天中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    public static Date getEndOfDay(long date) {
        return getEndOfDay(createCalendar(date));
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getDateOfDay(Calendar date, int offset) {
        date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) + offset);
        return date.getTime();
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getDateOfDay(Date date, int offset) {
        return getDateOfDay(createCalendar(date), offset);
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getDateOfDay(long date, int offset) {
        return getDateOfDay(createCalendar(date), offset);
    }

    /**
     * 获取一天中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfDay(Calendar date, int offset) {
        date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) + offset);
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一天中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfDay(Date date, int offset) {
        return getStartOfDay(createCalendar(date), offset);
    }

    /**
     * 获取一天中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfDay(long date, int offset) {
        return getStartOfDay(createCalendar(date), offset);
    }

    /**
     * 获取一天中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("DuplicatedCode")
    public static Date getEndOfDay(Calendar date, int offset) {
        date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) + offset);
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一天中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getEndOfDay(Date date, int offset) {
        return getEndOfDay(createCalendar(date), offset);
    }

    /**
     * 获取一天中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getEndOfDay(long date, int offset) {
        return getEndOfDay(createCalendar(date), offset);
    }

    /**
     * 获取一周中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfWeek(Calendar date) {
        date.set(Calendar.DAY_OF_WEEK, date.getActualMinimum(Calendar.DAY_OF_WEEK));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一周中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfWeek(Date date) {
        return getStartOfWeek(createCalendar(date));
    }

    /**
     * 获取一周中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("WeakerAccess")
    public static Date getStartOfWeek(long date) {
        return getStartOfWeek(createCalendar(date));
    }

    /**
     * 获取一周中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getEndOfWeek(Calendar date) {
        date.set(Calendar.DAY_OF_WEEK, date.getActualMaximum(Calendar.DAY_OF_WEEK));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一周中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfWeek(Date date) {
        return getEndOfWeek(createCalendar(date));
    }

    /**
     * 获取一周中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("WeakerAccess")
    public static Date getEndOfWeek(long date) {
        return getEndOfWeek(createCalendar(date));
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("WeakerAccess")
    public static Date getDateOfWeek(Calendar date, int offset) {
        date.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR) + offset);
        return date.getTime();
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getDateOfWeek(Date date, int offset) {
        return getDateOfWeek(createCalendar(date), offset);
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getDateOfWeek(long date, int offset) {
        return getDateOfWeek(createCalendar(date), offset);
    }

    /**
     * 获取一周中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfWeek(Calendar date, int offset) {
        date.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR) + offset);
        date.set(Calendar.DAY_OF_WEEK, date.getActualMinimum(Calendar.DAY_OF_WEEK));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一周中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfWeek(Date date, int offset) {
        return getStartOfWeek(createCalendar(date), offset);
    }

    /**
     * 获取一周中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfWeek(long date, int offset) {
        return getStartOfWeek(createCalendar(date), offset);
    }

    /**
     * 获取一周中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getEndOfWeek(Calendar date, int offset) {
        date.set(Calendar.WEEK_OF_YEAR, date.get(Calendar.WEEK_OF_YEAR) + offset);
        date.set(Calendar.DAY_OF_WEEK, date.getActualMaximum(Calendar.DAY_OF_WEEK));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一周中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfWeek(Date date, int offset) {
        return getEndOfWeek(createCalendar(date), offset);
    }

    /**
     * 获取一周中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfWeek(long date, int offset) {
        return getEndOfWeek(createCalendar(date), offset);
    }

    /**
     * 获取一月中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfMonth(Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一月中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfMonth(Date date) {
        return getStartOfMonth(createCalendar(date));
    }

    /**
     * 获取一月中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfMonth(long date) {
        return getStartOfMonth(createCalendar(date));
    }

    /**
     * 获取一月中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getEndOfMonth(Calendar date) {
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一月中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfMonth(Date date) {
        return getEndOfMonth(createCalendar(date));
    }

    /**
     * 获取一月中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfMonth(long date) {
        return getEndOfMonth(createCalendar(date));
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getDateOfMonth(Calendar date, int offset) {
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) + offset);
        return date.getTime();
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getDateOfMonth(Date date, int offset) {
        return getDateOfMonth(createCalendar(date), offset);
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    public static Date getDateOfMonth(long date, int offset) {
        return getDateOfMonth(createCalendar(date), offset);
    }

    /**
     * 获取一月中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfMonth(Calendar date, int offset) {
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) + offset);
        date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一月中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfMonth(Date date, int offset) {
        return getStartOfMonth(createCalendar(date), offset);
    }

    /**
     * 获取一月中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfMonth(long date, int offset) {
        return getStartOfMonth(createCalendar(date), offset);
    }

    /**
     * 获取一月中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getEndOfMonth(Calendar date, int offset) {
        date.set(Calendar.MONTH, date.get(Calendar.MONTH) + offset);
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一月中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfMonth(Date date, int offset) {
        return getEndOfMonth(createCalendar(date), offset);
    }

    /**
     * 获取一月中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfMonth(long date, int offset) {
        return getEndOfMonth(createCalendar(date), offset);
    }

    /**
     * 获取一年中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfYear(Calendar date) {
        date.set(Calendar.MONTH, date.getActualMinimum(Calendar.MONTH));
        date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一年中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfYear(Date date) {
        return getStartOfYear(createCalendar(date));
    }

    /**
     * 获取一年中最早的时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfYear(long date) {
        return getStartOfYear(createCalendar(date));
    }

    /**
     * 获取一年中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getEndOfYear(Calendar date) {
        date.set(Calendar.MONTH, date.getActualMaximum(Calendar.MONTH));
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一年中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfYear(Date date) {
        return getEndOfYear(createCalendar(date));
    }

    /**
     * 获取一年中最晚时刻
     * @param date 指定日期
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfYear(long date) {
        return getEndOfYear(createCalendar(date));
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("WeakerAccess")
    public static Date getDateOfYear(Calendar date, int offset) {
        date.set(Calendar.YEAR, date.get(Calendar.YEAR) + offset);
        return date.getTime();
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getDateOfYear(Date date, int offset) {
        return getDateOfYear(createCalendar(date), offset);
    }

    /**
     * 根据偏移量获取日期
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getDateOfYear(long date, int offset) {
        return getDateOfYear(createCalendar(date), offset);
    }

    /**
     * 获取一年中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getStartOfYear(Calendar date, int offset) {
        date.set(Calendar.YEAR, date.get(Calendar.YEAR) + offset);
        date.set(Calendar.MONTH, date.getActualMinimum(Calendar.MONTH));
        date.set(Calendar.DAY_OF_MONTH, date.getActualMinimum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMinimum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMinimum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一年中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfYear(Date date, int offset) {
        return getStartOfYear(createCalendar(date), offset);
    }

    /**
     * 获取一年中最早的时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getStartOfYear(long date, int offset) {
        return getStartOfYear(createCalendar(date), offset);
    }

    /**
     * 获取一年中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings({"WeakerAccess", "DuplicatedCode"})
    public static Date getEndOfYear(Calendar date, int offset) {
        date.set(Calendar.YEAR, date.get(Calendar.YEAR) + offset);
        date.set(Calendar.MONTH, date.getActualMaximum(Calendar.MONTH));
        date.set(Calendar.DAY_OF_MONTH, date.getActualMaximum(Calendar.DAY_OF_MONTH));
        date.set(Calendar.HOUR_OF_DAY, date.getActualMaximum(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(MILLISECOND, date.getActualMaximum(MILLISECOND));
        return date.getTime();
    }

    /**
     * 获取一年中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfYear(Date date, int offset) {
        return getEndOfYear(createCalendar(date), offset);
    }

    /**
     * 获取一年中最晚时刻
     * @param date   指定日期
     * @param offset 偏移量
     * @return 计算结果
     */
    @SuppressWarnings("unused")
    public static Date getEndOfYear(long date, int offset) {
        return getEndOfYear(createCalendar(date), offset);
    }
}
