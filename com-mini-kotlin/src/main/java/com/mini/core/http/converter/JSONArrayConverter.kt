package com.mini.core.http.converter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

@Suppress("unused")
class JSONArrayConverter : Converter<JSONArray?> {
    @Throws(IOException::class)
    override fun apply(call: Call, response: Response): JSONArray? {
        return if (response.isSuccessful) {
            response.body()?.use { body ->
                JSON.parseArray(body.string())
            }
        } else throw IOException(response.message())
    }
}