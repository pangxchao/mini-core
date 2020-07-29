package com.mini.core.security

import java.nio.charset.Charset
import java.util.*

abstract class SecurityBase<T : SecurityBase<T>?> : EventListener {

    companion object {
        // 用来将字节转换成 16 进制表示的字符
        private val DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

        /** 将byte数组转换成String  */
        private fun toHexString(bytes: ByteArray): String {
            val result = CharArray(bytes.size * 2)
            for (i in bytes.indices) {
                result[i * 2] = DIGITS[bytes[i].toInt().ushr(4).and(0xff)]
                result[i * 2 + 1] = DIGITS[bytes[i].toInt().and(0xf)]
            }
            return String(result)
        }
    }

    abstract fun update(input: ByteArray, offset: Int, len: Int): T

    abstract fun update(input: ByteArray): T

    abstract fun update(input: Byte): T

    fun update(input: String, charset: Charset): T {
        return update(input.toByteArray(charset))
    }

    fun update(input: String): T {
        return update(input.toByteArray())
    }

    abstract fun digest(input: ByteArray): ByteArray

    abstract fun digest(): ByteArray

    fun digest(data: String, charset: Charset): ByteArray {
        return digest(data.toByteArray(charset))
    }

    fun digest(data: String): ByteArray {
        return digest(data.toByteArray())
    }

    fun encode(): String {
        val b = this.digest()
        return toHexString(b)
    }

    fun base64(): String {
        val bytes = this.digest()
        val code = Base64.getEncoder()
        return code.encodeToString(bytes)
    }

}