package sn.mini.kotlin.util.degest

import java.io.InputStream

object MD5Util {
    private val instance = DigestUtil("MD5")

    /**
     * MD5加密
     *
     * @param data
     * @
     */
    fun encodeToByte(data: String): ByteArray = instance.encodeToByte(data)


    /**
     * MD5加密
     *
     * @param data
     * @
     */
    fun encode(data: String): String = instance.encode(data)

    /**
     * MD5加密
     *
     * @param data
     * @
     */
    fun encodeToByte(data: ByteArray): ByteArray = instance.encodeToByte(data)

    /**
     * MD5加密
     *
     * @param data
     * @
     */
    fun encode(data: ByteArray): String = instance.encode(data)

    /**
     * MD5加密
     *
     * @param data
     * @
     */
    fun encodeToByte(data: InputStream): ByteArray = instance.encodeToByte(data)

    /**
     * MD5加密
     *
     * @param data
     * @
     */
    fun encode(data: InputStream): String = instance.encode(data)

}
