@file:Suppress("unused")

package com.mini.core.security.digest

import java.nio.charset.Charset

class SHA1 : BaseDigest("SHA-1") {
    companion object {
        @JvmStatic
        val instance: SHA1
            get() = SHA1()

        @JvmStatic
        fun encode(data: String?, charset: Charset?): String {
            return instance.update(data!!, charset!!).encode()
        }

        @JvmStatic
        fun encode(data: String?): String {
            return instance.update(data!!).encode()
        }
    }
}