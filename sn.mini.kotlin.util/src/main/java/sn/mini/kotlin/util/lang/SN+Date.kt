package sn.mini.kotlin.util.lang

import java.text.SimpleDateFormat
import java.util.*

/** 日期格式化字符串： yyyy-MM-dd  */
const val STRING_DATE = "yyyy-MM-dd"

/** 日期格式化字符串： yyyy-MM-dd HH:mm:ss  */
const val STRING_DATETIME = "yyyy-MM-dd HH:mm:ss"

/** 日期格式化字符串： HH:mm:ss  */
const val STRING_TIME = "HH:mm:ss"

/**
 * 将日期格式化为 时间 (format) 格式
 * @param format 时间格式
 */
fun Date.format(format: String): String = SimpleDateFormat(format).format(this)

/**
 * 将日期格式化成：yyyy-MM-dd 格式
 */
fun Date.formatDate(): String = this.format(STRING_DATE)

/**
 * 将日期格式化成：yyyy-MM-dd HH:mm:ss 格式
 */
fun Date.formatDateTime(): String = this.format(STRING_DATETIME)

/**
 * 将日期格式化成：HH:mm:ss 格式
 */
fun Date.formatTime(): String = this.format(STRING_TIME)

/**
 * 与系统当前时间相差的天数
 */
fun Date.compareDay(): Int = compareDay(this.time)

/**
 * 获取与传入时间相差的天数
 */
fun Date.compareDay(date: Long): Int = DataUtil.compareDay(date, this.time)

