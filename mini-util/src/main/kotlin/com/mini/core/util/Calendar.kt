@file:JvmName("CalendarKt")
@file:Suppress("unused")

package com.mini.core.util

import java.util.*


/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
@JvmOverloads
fun Calendar.format(format: String = DATE_TIME): String {
    return time.format(format)
}

/**
 * 获取毫秒偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addMillisecond(value: Int): Calendar {
    this.add(Calendar.MILLISECOND, value)
    return this
}

/**
 * 获取秒偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addSecond(value: Int): Calendar {
    this.add(Calendar.SECOND, value)
    return this
}

/**
 * 获取分钟偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addMinute(value: Int): Calendar {
    this.add(Calendar.MINUTE, value)
    return this
}

/**
 * 获取小时偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addHourOfDay(value: Int): Calendar {
    this.add(Calendar.HOUR_OF_DAY, value)
    return this
}

/**
 * 获取日偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addDayOfMonth(value: Int): Calendar {
    this.add(Calendar.DAY_OF_MONTH, value)
    return this
}

/**
 * 获取星期偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addDayOfWeek(value: Int): Calendar {
    this.add(Calendar.DAY_OF_WEEK, value)
    return this
}

/**
 * 获取月偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addMonth(value: Int): Calendar {
    this.add(Calendar.MONTH, value)
    return this
}

/**
 * 获取年偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Calendar.addYear(value: Int): Calendar {
    this.add(Calendar.YEAR, value)
    return this
}

/**
 * 获取一秒中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfSecond(): Calendar {
    set(Calendar.MILLISECOND, getActualMinimum(Calendar.MILLISECOND))
    return this
}

/**
 * 获取一秒中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfSecond(): Calendar {
    set(Calendar.MILLISECOND, getActualMaximum(Calendar.MILLISECOND))
    return this
}

/**
 * 获取一分钟中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfMinute(): Calendar {
    set(Calendar.SECOND, getActualMinimum(Calendar.SECOND))
    return getStartOfSecond()
}

/**
 * 获取一分钟中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfMinute(): Calendar {
    set(Calendar.SECOND, getActualMaximum(Calendar.SECOND))
    return getEndOfSecond()
}

/**
 * 获取一天中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfHour(): Calendar {
    set(Calendar.MINUTE, getActualMinimum(Calendar.MINUTE))
    return getStartOfMinute()
}

/**
 * 获取一小时中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfHour(): Calendar {
    set(Calendar.MINUTE, getActualMaximum(Calendar.MINUTE))
    return getEndOfMinute()
}

/**
 * 获取一小时中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, getActualMinimum(Calendar.HOUR_OF_DAY))
    return getStartOfHour()
}

/**
 * 获取一天中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, getActualMaximum(Calendar.HOUR_OF_DAY))
    return getEndOfHour()
}

/**
 * 获取一周中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfWeek(): Calendar {
    set(Calendar.DAY_OF_WEEK, getActualMinimum(Calendar.DAY_OF_WEEK))
    return getStartOfDay()
}

/**
 * 获取一周中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfWeek(): Calendar {
    set(Calendar.DAY_OF_WEEK, getActualMaximum(Calendar.DAY_OF_WEEK))
    return getEndOfDay()
}

/**
 * 获取一月中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfMonth(): Calendar {
    set(Calendar.DAY_OF_MONTH, getActualMinimum(Calendar.DAY_OF_MONTH))
    return getStartOfDay()
}

/**
 * 获取一月中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfMonth(): Calendar {
    set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
    return getEndOfDay()
}

/**
 * 获取一年中最早的时刻
 * @return 计算结果
 */
fun Calendar.getStartOfYear(): Calendar {
    set(Calendar.MONTH, getActualMinimum(Calendar.MONTH))
    return getStartOfMonth()
}

/**
 * 获取一年中最晚时刻
 * @return 计算结果
 */
fun Calendar.getEndOfYear(): Calendar {
    set(Calendar.MONTH, getActualMaximum(Calendar.MONTH))
    return getEndOfMonth()
}
