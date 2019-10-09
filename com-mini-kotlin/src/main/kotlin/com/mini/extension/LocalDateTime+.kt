package com.mini.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun LocalDateTime.format(format: String): String {
    return this.format(ofPattern(format))
}

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 * @return 格式化结果
 */
@Suppress("unused")
fun LocalDateTime.formatDateTime(): String {
    return format("yyyy-MM-dd HH:mm:ss")
}