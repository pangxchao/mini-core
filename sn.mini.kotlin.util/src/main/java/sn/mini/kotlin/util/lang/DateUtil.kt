package sn.mini.kotlin.util.lang

import java.util.*

object DataUtil {

    /**
     * 获得与当前系统时间的相差天数
     * @param date
     * @return 相差天数 如果传入时间大于当前系统时间为负数
     */
    fun compareDay(date: Long): Int {
        return compareDay(date, System.currentTimeMillis())

    }

    /**
     * 获得传入两个时间的相差天数
     * @param startDate 传入开始时间
     * @param endDate 传结束时间
     * @return 开始时间到结束时间相关的开数，如果开始时间比结束时间大，返回负数
     */
    fun compareDay(startDate: Long, endDate: Long): Int {
        val day = (1000 * 3600 * 24).toLong()
        return (endDate / day - startDate / day).toInt()

    }


    /**
     * 获得与当前系统时间的相差月数
     * @param date
     * @return 相差月数 如果传入时间大于当前系统时间为负数
     */
    fun compareMonth(date: Long): Long {
        val now = Calendar.getInstance()
        val input = Calendar.getInstance()
        input.timeInMillis = date
        val yearNow = now.get(Calendar.YEAR)
        val monthNow = now.get(Calendar.MONTH)
        val yearInput = input.get(Calendar.YEAR)
        val monthInput = input.get(Calendar.MONTH)
        return (yearNow * 12 + monthNow - yearInput * 12 - monthInput).toLong()
    }

    /**
     * 计算年龄
     * @param date 出生日期
     * @return
     */
    fun calcAge(date: Date): Int {
        val now = Calendar.getInstance()
        val age = now.get(Calendar.YEAR)
        now.time = date
        return age - now.get(Calendar.YEAR)
    }
}