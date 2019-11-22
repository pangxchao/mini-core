package com.mini.core.http.converter

import okhttp3.Call
import okhttp3.Response
import java.io.IOException

@Suppress("unused")
class StringConverter : Converter<String?> {
    @Throws(IOException::class)
    override fun apply(call: Call, response: Response): String? {
        return if (response.isSuccessful) {
            response.body()?.use { body ->
                body.string()
            }
        } else throw IOException(response.message())
    }
}