package sn.mini.kotlin.util.degest

import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class DigestUtil(private val algorithm: String) {
    companion object {
        /**
         * 用来将字节转换成 16 进制表示的字符
         */
        private val ENCODE_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    }

    /**
     * 获取MD2加密文摘
     *
     * @return
     */
    private val digest: MessageDigest
        get() {
            try {
                return MessageDigest.getInstance(algorithm)
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e.message, e)
            }

        }

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: String): ByteArray {
        return digest.digest(data.toByteArray())
    }

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encode(data: String): String {
        return toString(encodeToByte(data))
    }

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: ByteArray): ByteArray {
        return digest.digest(data)
    }

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encode(data: ByteArray): String {
        return toString(encodeToByte(data))
    }

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: InputStream): ByteArray {
        try {
            val digest = digest
            val buffer = ByteArray(1024)
            var read = data.read(buffer)
            while (read > -1) {
                digest.update(buffer, 0, read)
                read = data.read(buffer)
            }
            return digest.digest()
        } catch (e: IOException) {
            throw RuntimeException(e.message, e)
        }

    }

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encode(data: InputStream): String {
        return toString(encodeToByte(data))
    }


    /**
     * 将byte数组转换成String
     *
     * @param bytes
     * @return
     */
    private fun toString(bytes: ByteArray): String {
        val result = CharArray(bytes.size * 2)
        // 从第一个字节开始，对每一个字节,转换成 16 进制字符的转换
        for (i in bytes.indices) {
            // 取字节中高 4 位的数字转换, >>> 为逻辑右移，将符号位一起右移
            result[i * 2] = ENCODE_DIGITS[bytes[i].toInt().ushr(4) and 0xf]
            // 取字节中低 4 位的数字转换
            result[i * 2 + 1] = ENCODE_DIGITS[bytes[i].toInt() and 0xf]
        }
        // 换后的结果转换为字符串
        return String(result)
    }


}
