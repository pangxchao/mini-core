@file:Suppress("unused")

package com.mini.core.security.digest

import com.mini.core.security.SecurityBase
import java.security.MessageDigest

/**
 * DigestUtil.java
 * @author xchao
 */
abstract class BaseDigest protected constructor(algorithm: String) : SecurityBase<BaseDigest>() {
    private val digest: MessageDigest = MessageDigest.getInstance(algorithm);

    override fun update(input: ByteArray, offset: Int, len: Int): BaseDigest {
        digest.update(input, offset, len)
        return this
    }

    override fun update(input: ByteArray): BaseDigest {
        digest.update(input)
        return this
    }

    override fun update(input: Byte): BaseDigest {
        digest.update(input)
        return this
    }

    override fun digest(input: ByteArray): ByteArray {
        return digest.digest(input)
    }

    override fun digest(): ByteArray {
        return digest.digest()
    }

    override fun toString(): String {
        return digest.toString()
    }

    fun reset() {
        digest.reset()
    }
}