@file:JvmName("LocalDateTimeKt")
package com.mini.plugin.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun LocalDateTime.format(format: String = DATE_TIME): String {
	return this.format(ofPattern(format))
}