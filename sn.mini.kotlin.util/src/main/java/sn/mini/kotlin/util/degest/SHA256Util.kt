/**
 * Created the com.cfinal.util.digest.SHA256Util.java
 *
 * @created 2017年5月16日 下午4:31:08
 * @version 1.0.0
 */
package sn.mini.kotlin.util.degest

import java.io.InputStream

/**
 * com.cfinal.util.digest.SHA256Util.java
 *
 * @author XChao
 */
object SHA256Util {
    private val instance = DigestUtil("SHA-256")


    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: String): ByteArray = instance.encodeToByte(data)

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    fun encode(data: String): String = instance.encode(data)

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: ByteArray): ByteArray = instance.encodeToByte(data)

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    fun encode(data: ByteArray): String = instance.encode(data)

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    fun encodeToByte(data: InputStream): ByteArray = instance.encodeToByte(data)

    /**
     * SHA256加密
     *
     * @param data
     * @return
     */
    fun encode(data: InputStream): String = instance.encode(data)

}
