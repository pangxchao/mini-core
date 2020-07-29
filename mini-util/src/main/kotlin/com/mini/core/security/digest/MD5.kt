@file:Suppress("unused")

package com.mini.core.security.digest

import java.nio.charset.Charset

class MD5 : BaseDigest("MD5") {
    companion object {
        @JvmStatic
        val instance: MD5
            get() = MD5()

        @JvmStatic
        fun encode(data: String, charset: Charset): String {
            return instance.update(data, charset).encode()
        }

        @JvmStatic
        fun encode(data: String): String {
            return instance.update(data).encode()
        }
    }
}