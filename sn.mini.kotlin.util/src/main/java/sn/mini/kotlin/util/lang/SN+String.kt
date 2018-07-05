package sn.mini.kotlin.util.lang

import java.text.SimpleDateFormat
import java.util.*

/**
 * 将当前 字符串按指定格式转换成上期
 * @param format
 * @return
 */
fun String.toDateOrNull(format: String): Date? {
    try {
        SimpleDateFormat(format).parse(this)
    } catch (e: Exception) {
    }
    return null
}

/**
 * 将当前 字符串按指定格式转换成上期
 * @param format
 * @return
 */
fun String.toDate(format: String): Date = this.toDateOrNull(format)
        ?: throw RuntimeException(""" UnParseAble date: “$this” """)

/**
 * 格式：yyyy-MM-dd
 * @return
 */
fun String.toDateOrNull(): Date? {
    try {
        return this.toDate("yyyy-MM-dd")
    } catch (e: Exception) {
    }
    try {
        return this.toDate("yyyy/MM/dd")
    } catch (e: Exception) {
    }
    try {
        return this.toDate("yyyy/dd/MM")
    } catch (e: Exception) {
    }
    try {
        return this.toDate("dd/MM/yyyy")
    } catch (e: Exception) {
    }
    try {
        return this.toDate("yy.MM.dd")
    } catch (e: Exception) {
    }
    try {
        return this.toDate("yy/MM/dd")
    } catch (e: Exception) {
    }
    try {
        return this.toDate("yy-MM-dd")
    } catch (e: Exception) {
    }
    try {
        return this.toDate("yy/dd/MM")
    } catch (e: Exception) {
    }
    return null
}

/**
 * 格式：yyyy-MM-dd
 * @return
 */
fun String.toDate(): Date = this.toDateOrNull() ?: throw RuntimeException(""" UnParseAble date: “$this” """)

