@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("InsertSqlKt")

package com.mini.core.data.builder


fun insertInfo(table: String, init: InsertSql.() -> Unit): InsertSql {
    return object : InsertSql() {}.insertInto(table).apply(init)
}

