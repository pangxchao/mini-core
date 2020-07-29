@file:Suppress("unused")

package com.mini.core.extension

import java.util.*


/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Calendar.format(format: String = DATE_TIME): String {
    return time.format(format)
}

