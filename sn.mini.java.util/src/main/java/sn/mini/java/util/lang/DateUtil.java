/**
 * Created the com.cfinal.util.lang.CFDate.java
 *
 * @created 2016年8月14日 上午10:22:25
 * @version 1.0.0
 */
package sn.mini.java.util.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * java.util.Date 工具类
 * @author XChao
 */
public final class DateUtil {

    /** 日期格式化字符串： yyyy-MM-dd */
    public static final String STRING_DATE = "yyyy-MM-dd";

    /** 日期格式化字符串： yyyy-MM-dd HH:mm:ss */
    public static final String STRING_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /** 日期格式化字符串： HH:mm:ss */
    public static final String STRING_TIME = "HH:mm:ss";

    /** 日期格式化DateFormat对象： yyyy-MM-dd */
    public static final DateFormat FORMAT_DATE = new SimpleDateFormat(STRING_DATE);

    /** 日期格式化DateFormat对象： yyyy-MM-dd HH:mm:ss */
    public static final DateFormat FORMAT_DATETIME = new SimpleDateFormat(STRING_DATETIME);

    /** 日期格式化DateFormat对象： HH:mm:ss */
    public static final DateFormat FORMAT_TIME = new SimpleDateFormat(STRING_TIME);

    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     * @param date
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return FORMAT_DATE.format(date);
    }


    /**
     * 将日期格式化成：yyyy-MM-dd 格式
     * @param date
     */
    public static String formatDate(long date) {
        return FORMAT_DATE.format(new Date(date));
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     * @param date
     */
    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return FORMAT_DATETIME.format(date);
    }

    /**
     * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
     * @param date
     */
    public static String formatDateTime(long date) {
        return FORMAT_DATETIME.format(new Date(date));
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     * @param date
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return FORMAT_TIME.format(date);
    }

    /**
     * 将日期格式化成：HH:mm:ss 格式
     * @param date
     */
    public static String formatTime(long date) {
        return FORMAT_TIME.format(new Date(date));
    }

    /**
     * 将日期格式化为 时间 (format) 格式
     * @param date 日期
     * @param format 时间格式
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);

    }

    /**
     * 将日期格式化为 时间 (format) 格式
     * @param date 日期
     * @param format 时间格式
     */
    public static String format(long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }

    /**
     * 将 yyyy-MM-dd 日期格式的字符串转换成日期格式
     * @param dateString
     */
    public static Date parseDate(String date) {
        try {
            return FORMAT_DATE.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将(HH:mm:ss)时间格式的字符串转换成日期格式
     * @param date
     */
    public static Date parseTime(String date) {
        try {
            return FORMAT_TIME.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将(yyyy-MM-dd HH:mm:ss)时间格式的字符串转换成日期格式
     * @param date
     */
    public static Date parseDateTime(String date) {
        try {
            return FORMAT_DATETIME.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将(format)时间格式的字符串转换成日期格式
     * @param date 日期字符串
     * @param format 格式化字符串
     */
    public static Date parse(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (Exception e) {
            throw new RuntimeException("日期格式错误", e);
        }
    }

    /**
     * 获得与当前系统时间的相差天数
     * @param date
     * @return 相差天数 如果传入时间大于当前系统时间为负数
     */
    public static int compareDate(long date) {
        return compareDate(date, System.currentTimeMillis());

    }

    /**
     * 获得传入两个时间的相差天数
     * @param startdate 传入开始时间
     * @param enddate 传结束时间
     * @return 开始时间到结束时间相关的开数，如果开始时间比结束时间大，返回负数
     */
    public static int compareDate(long startdate, long enddate) {
        long day = 1000 * 3600 * 24;
        return (int) (enddate / day - startdate / day);

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
