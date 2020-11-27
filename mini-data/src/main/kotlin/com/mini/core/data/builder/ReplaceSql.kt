@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("ReplaceBuilderKt")

package com.mini.core.data.builder


fun  replaceInfo(table: String, init: ReplaceSql.() -> Unit): ReplaceSql {
    return ReplaceSql.of().replaceInto(table).apply(init)
}

