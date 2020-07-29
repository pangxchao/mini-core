@file:Suppress("unused")

package com.mini.core.security.digest

import java.nio.charset.Charset

class SHA512 : BaseDigest("SHA-512") {
   companion object {

       val instance: SHA512
           get() = SHA512()

       fun encode(data: String?, charset: Charset?): String {
           return instance.update(data!!, charset!!).encode()
       }

       fun encode(data: String?): String {
           return instance.update(data!!).encode()
       }
   }
}