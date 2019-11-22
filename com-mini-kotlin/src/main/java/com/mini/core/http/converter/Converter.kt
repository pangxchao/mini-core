package com.mini.core.http.converter

import okhttp3.Call
import okhttp3.Response
import java.io.IOException

@FunctionalInterface
interface Converter<T> {
    /**
     * 数据转换方法
     * @param call     调用器
     * @param response Response 对象
     * @return 转换后的数据对象
     * @throws IOException 异常对象
     */
    @Throws(IOException::class)
    fun apply(call: Call, response: Response): T
}