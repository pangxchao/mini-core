@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.mini.core.security.crypto

import com.mini.core.security.SecurityBase
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

abstract class BaseMac constructor(private val algorithm: String) : SecurityBase<BaseMac>() {
    private var mac: Mac = Mac.getInstance(algorithm)

    fun init(input: ByteArray?, offset: Int, len: Int, params: AlgorithmParameterSpec?): BaseMac {
        mac.init(SecretKeySpec(input, offset, len, algorithm), params)
        return this
    }

    fun init(input: ByteArray?, offset: Int, len: Int): BaseMac {
        mac.init(SecretKeySpec(input, offset, len, algorithm))
        return this
    }

    fun init(input: ByteArray?, params: AlgorithmParameterSpec?): BaseMac {
        mac.init(SecretKeySpec(input, algorithm), params)
        return this
    }

    fun init(input: ByteArray?): BaseMac {
        mac.init(SecretKeySpec(input, algorithm))
        return this
    }

    fun init(input: String, charset: Charset?, params: AlgorithmParameterSpec?): BaseMac {
        return init(input.toByteArray(charset!!), params)
    }

    fun init(input: String, charset: Charset?): BaseMac {
        return init(input.toByteArray(charset!!))
    }

    fun init(input: String, params: AlgorithmParameterSpec?): BaseMac {
        return init(input.toByteArray(), params)
    }

    fun init(input: String): BaseMac {
        return init(input.toByteArray())
    }

    override fun update(input: ByteArray, offset: Int, len: Int): BaseMac {
        mac.update(input, offset, len)
        return this
    }

    override fun update(input: ByteArray): BaseMac {
        mac.update(input)
        return this
    }

    override fun update(input: Byte): BaseMac {
        mac.update(input)
        return this
    }

    fun update(input: ByteBuffer?): BaseMac {
        mac.update(input)
        return this
    }

    fun doFinal(output: ByteArray?, outOffset: Int): BaseMac {
        mac.doFinal(output, outOffset)
        return this
    }

    fun doFinal(input: ByteArray?): ByteArray {
        return mac.doFinal(input)
    }

    fun doFinal(): ByteArray {
        return mac.doFinal()
    }

    override fun digest(input: ByteArray): ByteArray {
        return doFinal(input)
    }

    override fun digest(): ByteArray {
        return doFinal()
    }

    override fun toString(): String {
        return mac.toString()
    }

    fun reset() {
        mac.reset()
    }

}