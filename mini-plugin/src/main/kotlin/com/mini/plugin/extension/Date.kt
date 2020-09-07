@file:JvmName("DateKt")
@file:Suppress("unused")

package com.mini.plugin.extension

import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME: String = "yyyy-MM-dd HH:mm:ss"
const val DATE: String = "yyyy-MM-dd"
const val TIME: String = "yyyy-MM-dd"

// 一秒的时间戳
const val second: Long = 1000L

// 一分钟的时间戳
const val minute = second * 60

// 一小时的时间戳
const val hour = minute * 60

// 一天的时间戳
const val day = hour * 24

// 一周的时间戳
const val week = day * 7

// 定义一个SimpleDateFormat对象的别名
typealias Format = SimpleDateFormat

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Date.format(format: String = DATE_TIME): String {
    return Format(format).format(this)
}