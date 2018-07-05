package sn.mini.kotlin.util.degest

import java.io.InputStream

fun main(args: Array<String>) {
    println(SHA1Util.encode("111"))
}

object SHA1Util {
    private val instance = DigestUtil("SHA-1")

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: String): ByteArray = instance.encodeToByte(data)

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    fun encode(data: String): String = instance.encode(data)

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: ByteArray): ByteArray = instance.encodeToByte(data)

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    fun encode(data: ByteArray): String = instance.encode(data)

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: InputStream): ByteArray = instance.encodeToByte(data)

    /**
     * SHA1加密
     *
     * @param data
     * @return
     */
    fun encode(data: InputStream): String = instance.encode(data)

}
