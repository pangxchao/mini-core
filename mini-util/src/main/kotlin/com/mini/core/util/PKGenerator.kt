@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.mini.core.util

import java.util.*

/**
 * 主键获取,规则：当前时间缀转36进制字符串 + 两位36进制IP码 + 一位36进制随机码
 * @author XChao
 */
object PKGenerator {
    private val RANDOM = Random()
    private val DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

    private var workerId: WorkerId? = null
    private var workId: Long = 0

    fun setWorkerId(workId: Long) {
        PKGenerator.workId = workId
    }

    @Synchronized
    fun nextId(): Long {
        if (workerId == null) {
            workerId = WorkerId(workId)
        }
        return workerId!!.nextId()
    }

    /**
     * 生成主键
     * @return 主键
     */
    @Synchronized
    fun id(): Long {
        return nextId()
    }

    /**
     * 根据主键获取ID中的时间戳
     * @param id 主键
     * @return 时间戳
     */
    fun millis(id: Long): Long {
        return WorkerId.millis(id)
    }

    /**
     * 生成一个UUID 替换掉"-"
     * @return UUID
     */
    fun uuid(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid.replace("-", "")
    }

    /**
     * 获取一个随机数
     * @param bound 最大限制
     * @return 随机数
     */
    fun nextInt(bound: Int): Int {
        return RANDOM.nextInt(bound)
    }

    /**
     * 获取一个随机字符
     * @return 随机数
     */
    fun nextNum(): Char {
        return DIGITS[nextInt(10)]
    }

    /**
     * 获取一个随机字符
     * @return 随机字符
     */
    fun nextChar(): Char {
        val length = DIGITS.size
        return DIGITS[nextInt(length)]
    }

    /**
     * 生成length位长度的纯数字的随机字符串
     * @param length 长度
     * @return 随机数字
     */
    fun number(length: Int): String {
        val result = CharArray(length)
        for (i in 0 until length) {
            result[i] = nextNum()
        }
        return String(result)
    }

    /**
     * 生成length位长度的字母加数据的随机字符串
     * @param length 长度
     * @return 随机字符
     */
    fun random(length: Int): String {
        val result = CharArray(length)
        for (i in 0 until length) {
            result[i] = nextChar()
        }
        return String(result)
    }
}