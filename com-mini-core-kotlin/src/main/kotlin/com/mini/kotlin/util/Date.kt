package com.mini.kotlin.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*

// 一秒的时间戳
const val second: Long = 1000
// 一分钟的时间戳
const val minute = second * 60
// 一小时的时间戳
const val hour = minute * 60
// 一天的时间戳
const val day = hour * 24
// 一周的时间戳
const val week = day * 7

// 定义一个SimpleDateFormat对象的别名
private typealias Format = SimpleDateFormat

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Date.format(format: String): String {
    return Format(format).format(this)
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Long.format(format: String): String {
    return Date(this).format(format)
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Calendar.format(format: String): String {
    return this.time.format(format)
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun LocalDateTime.format(format: String): String {
    return this.format(ofPattern(format))
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun LocalDate.format(format: String): String {
    return this.format(ofPattern(format))
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun LocalTime.format(format: String): String {
    return this.format(ofPattern(format))
}

/**
 * 将日期格式化成：yyyy-MM-dd 格式
 * @return 格式化结果
 */
fun Date.formatDate(): String? {
    return format("yyyy-MM-dd")
}

/**
 * 将日期格式化成：yyyy-MM-dd 格式
 * @return 格式化结果
 */
fun Long.formatDate(): String {
    return format("yyyy-MM-dd")
}

/**
 * 将日期格式化成：yyyy-MM-dd 格式
 * @return 格式化结果
 */
fun Calendar.formatDate(): String {
    return format("yyyy-MM-dd")
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @return 格式化结果
 */
fun LocalDate.formatDate(): String {
    return format("yyyy-MM-dd")
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
fun Date.formatDateTime(): String {
    return format("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
fun Long.formatDateTime(): String {
    return format("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
fun Calendar.formatDateTime(): String {
    return format("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
fun LocalDateTime.formatDateTime(): String {
    return format("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将日期格式化成：HH:mm:ss 格式
 * @return 格式化结果
 */
fun Date.formatTime(): String {
    return format("HH:mm:ss")
}

/**
 * 将日期格式化成：HH:mm:ss 格式
 * @return 格式化结果
 */
fun Long.formatTime(): String {
    return format("HH:mm:ss")
}

/**
 * 将日期格式化成：HH:mm:ss 格式
 * @return 格式化结果
 */
fun Calendar.formatTime(): String {
    return format("HH:mm:ss")
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
fun LocalTime.formatTime(): String {
    return format("HH:mm:ss")
}

/**
 * 将日期格式的字符串转化成日期
 * @param format 字符串格式
 * @return 日期
 */
fun String.toDate(format: String): Date {
    return Format(format).parse(this)
}

/**
 * 将(yyyy-MM-dd HH:mm:ss)时间格式的字符串转换成日期格式
 * @return 日期
 */
fun String.toDateTime(): Date {
    return toDate("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将 yyyy-MM-dd 日期格式的字符串转换成日期格式
 * @return 日期
 */
fun String.toDate(): Date {
    return toDate("yyyy-MM-dd")
}

/**
 * 将(HH:mm:ss)时间格式的字符串转换成日期格式
 * @return 日期
 */
fun String.toTime(): Date {
    return toDate("HH:mm:ss")
}