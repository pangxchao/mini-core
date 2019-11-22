package com.mini.core.util

import com.mini.core.util.ObjectUtil.sendError
import java.io.Serializable
import java.util.*


// 自增长序列部分（设计14位）
private const val MAX_SEQUENCE = (-1L shl 14).inv()
// 集群编号部分(设计8位)
private const val MAX_WORK = (-1L shl 8).inv()

// 2016-01-01 08:00:00+8:00
private const val BASE_TIME = 1451606400000L

class WorkerId(workerId: Long) : Serializable, EventListener {
    private var lastTimestamp = -1L
    // 自增序列
    private var sequence: Long = 0
    // 集群编号
    private val workerId: Long

    init {
        if (workerId > MAX_WORK) {
            sendError("Maximum")
        }
        this.workerId = workerId
    }

    @Synchronized
    fun nextId(): Long {
        var now = System.currentTimeMillis()
        while (sequence > MAX_SEQUENCE) {
            now = System.currentTimeMillis()
            if (lastTimestamp < now) {
                sequence = 0
            }
        }
        lastTimestamp = now
        val time = lastTimestamp - BASE_TIME shl 22
        val seq = sequence++ and MAX_SEQUENCE shl 8
        return time or seq or (workerId and MAX_WORK)
    }

    companion object {
        /**
         * 根据主键获取生成主键时的时间缀
         * @param id 主键
         * @return 时间戳
         */
        @JvmStatic
        fun millis(id: Long): Long {
            return (id shr 22) + BASE_TIME
        }
    }
}