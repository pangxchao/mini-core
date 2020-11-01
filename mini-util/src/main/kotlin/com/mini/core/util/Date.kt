@file:JvmName("DateKt")
@file:Suppress("unused")

package com.mini.core.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance

const val DATE_TIME: String = "yyyy-MM-dd HH:mm:ss"
const val DATE: String = "yyyy-MM-dd"
const val TIME: String = "yyyy-MM-dd"

// 一秒的时间戳
const val SECOND: Long = 1000L

// 一分钟的时间戳
const val MINUTE = SECOND * 60

// 一小时的时间戳
const val HOUR = MINUTE * 60

// 一天的时间戳
const val DAY = HOUR * 24

// 一周的时间戳
const val WEEK = DAY * 7

// 定义一个SimpleDateFormat对象的别名
typealias Format = SimpleDateFormat

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Date.format(format: String = DATE_TIME): String {
    return Format(format).format(this)
}

fun Date.toCalendar(): Calendar = getInstance().let {
    it.time = this
    it
}

/**
 * 获取毫秒偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addMillisecond(value: Int): Date = toCalendar().addMillisecond(value).time

/**
 * 获取秒偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addSecond(value: Int): Date = toCalendar().addSecond(value).time


/**
 * 获取分钟偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addMinute(value: Int): Date = toCalendar().addMinute(value).time


/**
 * 获取小时偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addHourOfDay(value: Int): Date = toCalendar().addHourOfDay(value).time


/**
 * 获取日偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addDayOfMonth(value: Int): Date = toCalendar().addDayOfMonth(value).time


/**
 * 获取星期偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addDayOfWeek(value: Int): Date = toCalendar().addDayOfWeek(value).time


/**
 * 获取月偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addMonth(value: Int): Date = toCalendar().addMonth(value).time


/**
 * 获取年偏移
 * @param value 偏移值
 * @return ｛@code this｝
 */
fun Date.addYear(value: Int): Date = toCalendar().addYear(value).time


/**
 * 获取一秒中最早的时刻
 * @return 计算结果
 */
fun Date.getStartOfSecond(): Date = toCalendar().getStartOfSecond().time


/**
 * 获取一秒中最晚时刻
 * @return 计算结果
 */
fun Date.getEndOfSecond(): Date = toCalendar().getEndOfSecond().time


/**
 * 获取一分钟中最早的时刻
 * @return 计算结果
 */
fun Date.getStartOfMinute(): Date = toCalendar().getStartOfMinute().time


/**
 * 获取一分钟中最晚时刻
 * @return 计算结果
 */
fun Date.getEndOfMinute(): Date = toCalendar().getEndOfMinute().time


/**
 * 获取一天中最早的时刻
 * @return 计算结果
 */
fun Date.getStartOfHour(): Date = toCalendar().getStartOfHour().time


/**
 * 获取一小时中最晚时刻
 * @return 计算结果
 */
fun Date.getEndOfHour(): Date = toCalendar().getEndOfHour().time


/**
 * 获取一小时中最早的时刻
 * @return 计算结果
 */
fun Date.getStartOfDay(): Date = toCalendar().getStartOfDay().time


/**
 * 获取一天中最晚时刻
 * @return 计算结果
 */
fun Date.getEndOfDay(): Date = toCalendar().getEndOfDay().time


/**
 * 获取一周中最早的时刻
 * @return 计算结果
 */
fun Date.getStartOfWeek(): Date = toCalendar().getStartOfWeek().time


/**
 * 获取一周中最晚时刻
 * @return 计算结果
 */
fun Date.getEndOfWeek(): Date = toCalendar().getEndOfWeek().time


/**
 * 获取一月中最早的时刻
 * @return 计算结果
 */
fun Date.getStartOfMonth(): Date = toCalendar().getStartOfMonth().time


/**
 * 获取一月中最晚时刻
 * @return 计算结果
 */
fun Date.getEndOfMonth(): Date = toCalendar().getEndOfMonth().time


/**
 * 获取一年中最早的时刻
 * @return 计算结果
 */
fun Date.getStartOfYear(): Date = toCalendar().getStartOfYear().time


/**
 * 获取一年中最晚时刻
 * @return 计算结果
 */
fun Date.getEndOfYear(): Date = toCalendar().getEndOfYear().time
