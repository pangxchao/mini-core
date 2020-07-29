package com.mini.core.util

import java.io.Serializable
import java.lang.System.currentTimeMillis

class WorkerId constructor(private val workerId: Long) : Serializable {
    private var lastTimestamp = -1L
    private var sequence: Long = 0

    @Synchronized
    fun nextId(): Long {
        var now = currentTimeMillis()
        while (sequence > MAX_SEQUENCE) {
            now = currentTimeMillis()
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
        private const val serialVersionUID = 27599062740655580L

        // 自增长序列部分（设计14位）
        private const val MAX_SEQUENCE = (-1L shl 14).inv()

        // 集群编号部分(设计8位)
        private const val MAX_WORK = (-1L shl 8).inv()

        // 2016-01-01 08:00:00+8:00
        private const val BASE_TIME = 1451606400000L

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