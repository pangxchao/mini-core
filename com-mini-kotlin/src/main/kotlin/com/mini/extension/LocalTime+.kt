package com.mini.extension

import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern


/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun LocalTime.format(format: String): String {
    return this.format(ofPattern(format))
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun LocalTime.formatTime(): String {
    return format("HH:mm:ss")
}