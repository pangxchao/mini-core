@file:JvmName("LocalDateKt")

package com.mini.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
@JvmOverloads
fun LocalDate.format(format: String = DATE): String {
    return format(ofPattern(format))
}
