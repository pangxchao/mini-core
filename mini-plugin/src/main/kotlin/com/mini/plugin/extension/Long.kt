@file:JvmName("LongKt")
@file:Suppress("unused")

package com.mini.plugin.extension

import java.util.*

/**
 * 将Long转换成日期
 * @return 格式化结果
 */
fun Long.toDate(): Date {
    return Date(this)
}

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Long.dateFormat(format: String = DATE_TIME): String {
    return toDate().format(format)
}