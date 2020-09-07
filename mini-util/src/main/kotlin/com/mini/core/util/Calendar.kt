@file:JvmName("CalendarKt")
@file:Suppress("unused")

package com.mini.core.util

import java.util.*
import java.util.Calendar.*


/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Calendar.format(format: String = DATE_TIME): String {
    return time.format(format)
}

/**
 * 获取毫秒偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addMillisecond(value: Int): Calendar {
    this.add(MILLISECOND, value)
    return this
}

/**
 * 获取秒偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addSecond(value: Int): Calendar {
    this.add(SECOND, value)
    return this
}

/**
 * 获取分钟偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addMinute(value: Int): Calendar {
    this.add(MINUTE, value)
    return this
}

/**
 * 获取小时偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addHourOfDay(value: Int): Calendar {
    this.add(HOUR_OF_DAY, value)
    return this
}

/**
 * 获取日偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addDayOfMonth(value: Int): Calendar {
    this.add(DAY_OF_MONTH, value)
    return this
}

/**
 * 获取星期偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addDayOfWeek(value: Int): Calendar {
    this.add(DAY_OF_WEEK, value)
    return this
}

/**
 * 获取月偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addMonth(value: Int): Calendar {
    this.add(MONTH, value)
    return this
}

/**
 * 获取年偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addYear(value: Int): Calendar {
    this.add(YEAR, value)
    return this
}

/**
 * 获取一秒中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfSecond(): Calendar {
    set(MILLISECOND, getActualMinimum(MILLISECOND))
    return this
}

/**
 * 获取一秒中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfSecond(): Calendar {
    set(MILLISECOND, getActualMaximum(MILLISECOND))
    return this
}

/**
 * 获取一分钟中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfMinute(): Calendar {
    set(SECOND, getActualMinimum(SECOND))
    return getStartOfSecond()
}

/**
 * 获取一分钟中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfMinute(): Calendar {
    set(SECOND, getActualMaximum(SECOND))
    return getEndOfSecond()
}

/**
 * 获取一天中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfHour(): Calendar {
    set(MINUTE, getActualMinimum(MINUTE))
    return getStartOfMinute()
}

/**
 * 获取一小时中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfHour(): Calendar {
    set(MINUTE, getActualMaximum(MINUTE))
    return getEndOfMinute()
}

/**
 * 获取一小时中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfDay(): Calendar {
    set(HOUR_OF_DAY, getActualMinimum(HOUR_OF_DAY))
    return getStartOfHour()
}

/**
 * 获取一天中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfDay(): Calendar {
    set(HOUR_OF_DAY, getActualMaximum(HOUR_OF_DAY))
    return getEndOfHour()
}

/**
 * 获取一周中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfWeek(): Calendar {
    set(DAY_OF_WEEK, getActualMinimum(DAY_OF_WEEK))
    return getStartOfDay()
}

/**
 * 获取一周中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfWeek(): Calendar {
    set(DAY_OF_WEEK, getActualMaximum(DAY_OF_WEEK))
    return getEndOfDay()
}

/**
 * 获取一月中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfMonth(): Calendar {
    set(DAY_OF_MONTH, getActualMinimum(DAY_OF_MONTH))
    return getStartOfDay()
}

/**
 * 获取一月中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfMonth(): Calendar {
    set(DAY_OF_MONTH, getActualMaximum(DAY_OF_MONTH))
    return getEndOfDay()
}

/**
 * 获取一年中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfYear(): Calendar {
    set(MONTH, getActualMinimum(MONTH))
    return getStartOfMonth()
}

/**
 * 获取一年中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfYear(): Calendar {
    set(MONTH, getActualMaximum(MONTH))
    return getEndOfMonth()
}
