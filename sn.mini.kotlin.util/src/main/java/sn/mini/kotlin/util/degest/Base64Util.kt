package sn.mini.kotlin.util.degest

import java.nio.ByteBuffer

object Base64Util {

    /**
     * Base64编码
     * @param data
     * @return
     */
    fun encode(data: String): String {
        return java.util.Base64.getEncoder().encodeToString(data.toByteArray())
    }

    /**
     * Base64编码
     * @param data
     * @return
     */
    fun encodeToByte(data: String): ByteArray {
        return java.util.Base64.getEncoder().encode(data.toByteArray())
    }

    /**
     * Base64编码
     * @param data
     * @return
     */
    fun encode(data: ByteArray): String {
        return java.util.Base64.getEncoder().encodeToString(data)
    }

    /**
     * Base64编码
     * @param data
     * @return
     */
    fun encodeToByte(data: ByteArray): ByteArray {
        return java.util.Base64.getEncoder().encode(data)
    }

    /**
     * Base64编码
     * @param data
     * @return
     */
    fun encode(data: ByteBuffer): ByteBuffer {
        return java.util.Base64.getEncoder().encode(data)
    }

    /**
     * Base64编码
     * @param data
     * @return
     */
    @JvmStatic
    fun encode(data: ByteArray, dest: ByteArray): Int {
        return java.util.Base64.getEncoder().encode(data, dest)
    }

    /**
     * Base64解码
     * @param data
     * @return
     */
    fun decode(data: String): String {
        return String(java.util.Base64.getDecoder().decode(data))
    }

    /**
     * Base64解码
     * @param data
     * @return
     */
    fun decodeToByte(data: String): ByteArray {
        return java.util.Base64.getDecoder().decode(data)
    }

    /**
     * Base64解码
     * @param data
     * @return
     */
    fun decode(data: ByteArray): String {
        return String(java.util.Base64.getDecoder().decode(data))
    }

    /**
     * Base64解码
     * @param data
     * @return
     */
    fun decodeToByte(data: ByteArray): ByteArray {
        return java.util.Base64.getDecoder().decode(data)
    }

    /**
     * Base64解码
     * @param data
     * @return
     */
    fun decode(data: ByteBuffer): ByteBuffer {
        return java.util.Base64.getDecoder().decode(data)
    }

    /**
     * Base64解码
     * @param data
     * @return
     */
    fun decode(data: ByteArray, dest: ByteArray): Int {
        return java.util.Base64.getDecoder().decode(data, dest)
    }

    /**
     * URLBase64编码
     * @param data
     * @return
     */
    fun urlEncode(data: String): String {
        return java.util.Base64.getUrlEncoder().encodeToString(data.toByteArray())
    }

    /**
     * URLBase64编码
     * @param data
     * @return
     */
    fun urlEncodeToByte(data: String): ByteArray {
        return java.util.Base64.getUrlEncoder().encode(data.toByteArray())
    }

    /**
     * URLBase64编码
     * @param data
     * @return
     */
    fun urlEncode(data: ByteArray): String {
        return java.util.Base64.getUrlEncoder().encodeToString(data)
    }

    /**
     * URLBase64编码
     * @param data
     * @return
     */
    fun urlEncodeToByte(data: ByteArray): ByteArray {
        return java.util.Base64.getUrlEncoder().encode(data)
    }

    /**
     * URLBase64编码
     * @param data
     * @return
     */
    fun urlEncode(data: ByteBuffer): ByteBuffer {
        return java.util.Base64.getUrlEncoder().encode(data)
    }

    /**
     * URLBase64编码
     * @param data
     * @return
     */
    fun urlEncode(data: ByteArray, dest: ByteArray): Int {
        return java.util.Base64.getUrlEncoder().encode(data, dest)
    }

    /**
     * URLBase64解码
     * @param data
     * @return
     */
    fun urlDecode(data: String): String {
        return String(java.util.Base64.getUrlDecoder().decode(data))
    }

    /**
     * URLBase64解码
     * @param data
     * @return
     */
    fun urlDecodeToByte(data: String): ByteArray {
        return java.util.Base64.getUrlDecoder().decode(data)
    }

    /**
     * URLBase64解码
     * @param data
     * @return
     */
    fun urlDecode(data: ByteArray): String {
        return String(java.util.Base64.getUrlDecoder().decode(data))
    }

    /**
     * URLBase64解码
     * @param data
     * @return
     */
    fun urlDecodeToByte(data: ByteArray): ByteArray {
        return java.util.Base64.getUrlDecoder().decode(data)
    }

    /**
     * URLBase64解码
     * @param data
     * @return
     */
    fun urlDecode(data: ByteBuffer): ByteBuffer {
        return java.util.Base64.getUrlDecoder().decode(data)
    }

    /**
     * URLBase64解码
     * @param data
     * @return
     */
    fun urlDecode(data: ByteArray, dest: ByteArray): Int {
        return java.util.Base64.getUrlDecoder().decode(data, dest)
    }
}
