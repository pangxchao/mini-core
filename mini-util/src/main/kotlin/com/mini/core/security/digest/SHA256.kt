@file:Suppress("unused")

package com.mini.core.security.digest

import java.nio.charset.Charset

class SHA256 : BaseDigest("SHA-256") {
    companion object{

        val instance: SHA256
            get() = SHA256()

        fun encode(data: String?, charset: Charset?): String {
            return instance.update(data!!, charset!!).encode()
        }

        fun encode(data: String?): String {
            return instance.update(data!!).encode()
        }
    }
}