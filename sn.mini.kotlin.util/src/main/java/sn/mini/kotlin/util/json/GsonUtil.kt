package sn.mini.kotlin.util.json

import com.google.gson.*

val gson: Gson? = GsonBuilder()
        .setLenient()// json宽松
        .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
        .serializeNulls() //智能null
        .setPrettyPrinting()// 调教格式
        .disableHtmlEscaping() //默认是GSON把HTML 转义
        .create() //

val parser: JsonParser = JsonParser()

fun parserObject(json: String?): JsonObject? {
    return json?.let {
        return@let parser.parse(it)?.asJsonObject
    }
}

fun parserArray(json: String?): JsonArray? {
    return json?.let {
        return@let parser.parse(it)?.asJsonArray
    }
}

