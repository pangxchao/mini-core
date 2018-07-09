/**
 * Created com.cfinal.util.CFPKGenerator.java
 * @created 2017年4月25日 上午8:43:01
 * @version 1.0.0
 */
package sn.mini.kotlin.util

import java.util.*

//import jdk.nashorn.api.scripting.JSObject;

fun main(args: Array<String>) {
    println("-------------")
    println(null.toString())
}

/**
 * 主键获取,规则：当前时间缀转36进制字符串 + 两位36进制IP码 + 一位36进制随机码
 * @author xchao
 */
object PKGenerator {
    private const val BASE_TIME = 1451606400000L
    private val DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', //
            'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')

    private var workerId = 0L
    private var sequence = 0L
    private var lasttimestamp = -1L

    init {
        workerId = (Random().nextInt(16) + 1).toLong()
    }

    // 生成主键
    @Synchronized
    private fun generate(): Long {
        if (sequence > 0x3FFF) {
            sequence = 0
        }
        val timestamp = System.currentTimeMillis()
        while (sequence == 0L && lasttimestamp > timestamp) {
            lasttimestamp = timestamp
        }
        val `val` = 0x7fffffffffffffffL and (timestamp - BASE_TIME shl 22)
        return `val` or (sequence++ and 0x3FFF shl 8) or (workerId and 0xff)
    }

    // 根据主键获取生成主键时的时间缀
    private fun sequence(generate: Long): Long {
        return (generate and -0x400000L shr 22) + BASE_TIME
    }

    /**
     * 根据主键获取生成主键生成时的时间缀
     * @param key
     * @return
     */
    fun millis(key: Long): Long = sequence(key)

    /**
     * 生成主键
     * @return
     */
    fun key(): Long = generate()

    /**
     * 生成一个UUID 替换掉"-"
     * @return
     */
    fun uuid(): String = UUID.randomUUID().toString().replace("-", "").toUpperCase()

    /**
     * 生成length位长度的纯数字的随机字符串
     * @param length
     * @return
     */
    fun genNum(length: Int): String {
        val result = StringBuilder()
        val random = Random()
        for (i in 0 until length) {
            result.append(random.nextInt(10))
        }
        return result.toString()
    }

    /**
     * 生成length位长度的字母加数据的随机字符串
     * @param length
     * @return
     */
    fun genSeed(length: Int): String {
        val result = StringBuilder("")
        val random = Random()
        for (i in 0 until length) {
            result.append(DIGITS[random.nextInt(DIGITS.size)])
        }
        return result.toString()
    }
}
