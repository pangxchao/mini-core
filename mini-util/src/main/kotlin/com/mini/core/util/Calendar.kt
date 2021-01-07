@file:JvmName("CalendarKt")
@file:Suppress("unused")

package com.mini.core.util

import java.util.*
import java.util.Calendar.*


/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
@JvmOverloads
fun Calendar.format(format: String = DATE_TIME): String {
    return time.format(format)
}

fun Calendar.formatDateTime(): String {
    return this.format(DATE_TIME)
}

fun Calendar.formatDate(): String {
    return this.format(DATE)
}

fun Calendar.formatTime(): String {
    return this.format(TIME)
}

@JvmOverloads
fun Calendar.toStart(
    clearMonth: Boolean = false,
    clearDayOfMonth: Boolean = false,
    clearHour: Boolean = true,
    clearMinute: Boolean = true,
    clearSecond: Boolean = true
): Calendar {
    this.set(MILLISECOND, 0)
    clearSecond.takeIf { it }?.let {
        set(Calendar.SECOND, 0)
    }
    clearMinute.takeIf { it }?.let {
        set(Calendar.MINUTE, 0)
    }
    clearHour.takeIf { it }?.let {
        set(HOUR_OF_DAY, 0)
    }
    clearDayOfMonth.takeIf { it }?.let {
        set(DAY_OF_MONTH, 0)
    }
    clearMonth.takeIf { it }?.let {
        set(MONTH, 0)
    }
    return this
}

@JvmOverloads
fun Calendar.toEnd(
    maximumMonth: Boolean = false,
    maximumDayOfMonth: Boolean = false,
    maximumHour: Boolean = true,
    maximumMinute: Boolean = true,
    maximumSecond: Boolean = true
): Calendar {
    this.set(MILLISECOND, getMaximum(MILLISECOND))
    maximumSecond.takeIf { it }?.let {
        set(Calendar.SECOND, getMaximum(Calendar.SECOND))
    }
    maximumMinute.takeIf { it }?.let {
        set(Calendar.MINUTE, getMaximum(Calendar.MINUTE))
    }
    maximumHour.takeIf { it }?.let {
        set(HOUR_OF_DAY, getMaximum(HOUR_OF_DAY))
    }
    maximumDayOfMonth.takeIf { it }?.let {
        set(DAY_OF_MONTH, getMaximum(DAY_OF_MONTH))
    }
    maximumMonth.takeIf { it }?.let {
        set(MONTH, getMaximum(MONTH))
    }
    return this
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
    this.add(Calendar.DAY_OF_WEEK, value)
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
    this.add(Calendar.YEAR, value)
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
