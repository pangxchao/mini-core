@file:Suppress("unused")

package com.mini.core.extension

import java.util.*

/**
 * 将日期格式的字符串转化成日期
 * @param format 字符串格式
 * @return 日期
 */
fun String.toDate(format: String = DATE_TIME): Date {
    return Format(format).parse(this)
}
