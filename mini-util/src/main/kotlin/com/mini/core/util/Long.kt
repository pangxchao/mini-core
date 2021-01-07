@file:JvmName("LongKt")
@file:Suppress("unused", "HasPlatformType")

package com.mini.core.util

import java.util.*

/**
 * 将Long转换成日期
 * @return 格式化结果
 */
fun Long.toDate(): Date {
    return Date(this)
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
@JvmOverloads
fun Long.format(format: String = DATE_TIME): String {
    return toDate().format(format)
}

fun Long.formatDateTime(): String {
    return this.format(DATE_TIME)
}

fun Long.formatDate(): String {
    return this.format(DATE)
}

fun Long.formatTime(): String {
    return this.format(TIME)
}

@JvmOverloads
fun Long.toStart(
    clearMonth: Boolean = false,
    clearDayOfMonth: Boolean = false,
    clearHour: Boolean = true,
    clearMinute: Boolean = true,
    clearSecond: Boolean = true
) = toDate().toStart(
    clearMonth = clearMonth,
    clearDayOfMonth = clearDayOfMonth,
    clearHour = clearHour,
    clearMinute = clearMinute,
    clearSecond = clearSecond
)

@JvmOverloads
fun Long.toEnd(
    maximumMonth: Boolean = false,
    maximumDayOfMonth: Boolean = false,
    maximumHour: Boolean = true,
    maximumMinute: Boolean = true,
    maximumSecond: Boolean = true
) = toDate().toEnd(
    maximumMonth = maximumMonth,
    maximumDayOfMonth = maximumDayOfMonth,
    maximumHour = maximumHour,
    maximumMinute = maximumMinute,
    maximumSecond = maximumSecond
)
