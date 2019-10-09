package com.mini.extension

import java.util.*

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Long.format(format: String): String {
    return Date(this).format(format)
}

/**
 * 将日期格式化成：yyyy-MM-dd 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun Long.formatDate(): String {
    return format("yyyy-MM-dd")
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun Long.formatDateTime(): String {
    return format("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将日期格式化成：HH:mm:ss 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun Long.formatTime(): String {
    return format("HH:mm:ss")
}