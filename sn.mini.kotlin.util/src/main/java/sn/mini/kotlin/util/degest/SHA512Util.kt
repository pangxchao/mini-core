/**
 * Created the com.cfinal.util.digest.CFSHA512.java
 * @created 2017年5月16日 下午4:31:08
 * @version 1.0.0
 */
package sn.mini.kotlin.util.degest

import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * com.cfinal.util.digest.CFSHA512.java
 * @author XChao
 */
object SHA512Util {
    /** 用来将字节转换成 16 进制表示的字符  */
    private val encode_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * 获取SHA512加密文摘
     * @return
     */
    private val digest: MessageDigest
        get() {
            try {
                return MessageDigest.getInstance("SHA-512")
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e.message, e)
            }

        }

    /**
     * 将byte数组转换成String
     * @param bytes
     * @return
     */
    private fun toString(bytes: ByteArray): String {
        // 每个字节用 16 进制表示的话，使用两个字符
        val result = CharArray(16 * 2)
        // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
        var i = 0
        var k = 0
        while (i < 16) {
            // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
            result[k++] = encode_DIGITS[bytes[i].toInt().ushr(4) and 0xf]
            // 取字节中低 4 位的数字转换
            result[k++] = encode_DIGITS[bytes[i].toInt() and 0xf]
            i++
        }
        // 换后的结果转换为字符串
        return String(result)
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    fun encodeToByte(data: String): ByteArray {
        return digest.digest(data.toByteArray())
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    fun encode(data: String): String {
        return toString(encodeToByte(data))
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    fun encodeToByte(data: ByteArray): ByteArray {
        return digest.digest(data)
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    fun encode(data: ByteArray): String {
        return toString(encodeToByte(data))
    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    fun encodeToByte(data: InputStream): ByteArray {
        try {
            val digest = digest
            val buffer = ByteArray(1024)
            var read = data.read(buffer, 0, 1024)
            while (read > -1) {
                digest.update(buffer, 0, read)
                read = data.read(buffer, 0, 1024)
            }
            return digest.digest()
        } catch (e: IOException) {
            throw RuntimeException(e.message, e)
        }

    }

    /**
     * SHA512加密
     * @param data
     * @return
     */
    fun encode(data: InputStream): String {
        return toString(encodeToByte(data))
    }

}
