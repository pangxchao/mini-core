@file:JvmName("LocalTimeKt")
package com.mini.plugin.extension

import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofPattern


/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun LocalTime.format(format: String = TIME): String {
    return this.format(ofPattern(format))
}