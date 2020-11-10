@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("ReplaceBuilderKt")

package com.mini.core.data.builder


fun INDEXED_REPLACE_INFO(table: String, init: IndexedReplaceSql.() -> Unit): IndexedReplaceSql {
    return IndexedReplaceSql.of().REPLACE_INTO(table).apply(init)
}


fun NAMED_REPLACE_INFO(table: String, init: NamedReplaceSql.() -> Unit): NamedReplaceSql {
    return NamedReplaceSql.of().REPLACE_INTO(table).apply(init)
}
