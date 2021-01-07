@file:JvmName("LocalDateTimeKt")
@file:Suppress("unused")

package com.mini.core.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
@JvmOverloads
fun LocalDateTime.format(format: String = DATE_TIME): String {
	return this.format(ofPattern(format))
}

fun LocalDateTime.formatDateTime(): String {
    return this.format(DATE_TIME)
}

fun LocalDateTime.formatDate(): String {
    return this.format(DATE)
}

fun LocalDateTime.formatTime(): String {
    return this.format(TIME)
}