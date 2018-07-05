package sn.mini.kotlin.util.degest

import java.io.InputStream

object MD2Util {
    private val instance = DigestUtil("MD2")

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: String): ByteArray = instance.encodeToByte(data)

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encode(data: String): String = instance.encode(data)

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: ByteArray): ByteArray = instance.encodeToByte(data)

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encode(data: ByteArray): String = instance.encode(data)

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: InputStream): ByteArray = instance.encodeToByte(data)

    /**
     * MD2加密
     *
     * @param data
     * @return
     */
    fun encode(data: InputStream): String = instance.encode(data)

}
