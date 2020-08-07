package com.mini.plugin.config

import java.io.Serializable

/**
 * 数据类型映射表格信息
 * @author xchao
 */
data class DataType1(
        val databaseType: String = "VARCHAR",
        val javaType: String = "String",
        val nullJavaType: String = javaType
) : AbstractClone<DataType1>(), Serializable {
    override fun clone(): DataType1 {
        return copy()
    }
}

fun main() {
    DataType1().copy()
}