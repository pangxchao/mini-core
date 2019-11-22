package com.mini.core.extension

import java.util.*

/**
 * 将日期格式的字符串转化成日期
 * @param format 字符串格式
 * @return 日期
 */
fun String.toDate(format: String): Date {
    return Format(format).parse(this)
}

/**
 * 将(yyyy-MM-dd HH:mm:ss)时间格式的字符串转换成日期格式
 * @return 日期
 */
@Suppress("unused")
fun String.toDateTime(): Date {
    return toDate("yyyy-MM-dd HH:mm:ss")
}

/**
 * 将 yyyy-MM-dd 日期格式的字符串转换成日期格式
 * @return 日期
 */
@Suppress("unused")
fun String.toDate(): Date {
    return toDate("yyyy-MM-dd")
}

/**
 * 将(HH:mm:ss)时间格式的字符串转换成日期格式
 * @return 日期
 */
@Suppress("unused")
fun String.toTime(): Date {
    return toDate("HH:mm:ss")
}