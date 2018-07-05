package sn.mini.kotlin.util.lang

import java.util.*

/**
 * 转换成日期
 */
fun Long.toDate() = Date(this)

/**
 * 转换成文件/硬盘等计算机空间大小的字符串（带KB MB GB TB 等单位）
 */
fun Long.formatFileSize(): String {
    return this.toString()
}