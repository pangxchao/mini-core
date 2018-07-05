package sn.mini.kotlin.util.json

import com.google.gson.*
import com.google.gson.reflect.TypeToken

object GsonUtil {
    val gson: Gson? = GsonBuilder()
            .setLenient()// json宽松
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            .serializeNulls() //智能null
            .setPrettyPrinting()// 调教格式
            .disableHtmlEscaping() //默认是GSON把HTML 转义的
            .create()

    fun toJson(src: Any): String? = gson?.toJson(src)

    fun parseObject(src: String): JsonObject? = JsonParser().parse(src)?.asJsonObject

    fun parseArray(src: String): JsonArray? = JsonParser().parse(src)?.asJsonArray

    fun <T> parse(src: String): T? = gson?.fromJson(src, object : TypeToken<T>() {
    }.type)

    fun <T> parseList(src: String): List<T>? = gson?.fromJson(src, object : TypeToken<List<T>>() {
    }.type)
}