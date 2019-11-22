package com.mini.core.extension

import java.text.SimpleDateFormat
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
@Suppress("unused")
const val week = day * 7

// 定义一个SimpleDateFormat对象的别名
typealias Format = SimpleDateFormat

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Date.format(format: String): String {
    return Format(format).format(this)
}

/**
 * 将日期格式化成：yyyy-MM-dd 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun Date.formatDate(): String? {
    return format("yyyy-MM-dd")
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun Date.formatDateTime(): String {
    return format("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将日期格式化成：HH:mm:ss 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun Date.formatTime(): String {
    return format("HH:mm:ss")
}