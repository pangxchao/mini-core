@file:JvmName("LocalTimeKt")
@file:Suppress("unused")

package com.mini.core.util

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern


/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
@JvmOverloads
fun LocalTime.format(format: String = TIME): String {
    return this.format(ofPattern(format))
}

fun LocalTime.formatDateTime(): String {
    return this.format(DATE_TIME)
}

fun LocalTime.formatDate(): String {
    return this.format(DATE)
}

fun LocalTime.formatTime(): String {
    return this.format(TIME)
}