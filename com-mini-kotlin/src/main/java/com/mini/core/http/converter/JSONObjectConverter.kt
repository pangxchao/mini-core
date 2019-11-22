package com.mini.core.http.converter

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

@Suppress("unused")
class JSONObjectConverter : Converter<JSONObject?> {
    @Throws(IOException::class)
    override fun apply(call: Call, response: Response): JSONObject? {
        return if (response.isSuccessful) {
            response.body()?.use { body ->
                JSON.parseObject(body.string())
            }
        } else throw IOException(response.message());
    }
}