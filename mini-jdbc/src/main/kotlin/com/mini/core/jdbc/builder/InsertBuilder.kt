@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("InsertSqlKt")

package com.mini.core.jdbc.builder


fun INDEXED_INSERT_INFO(table: String, init: IndexedInsertSql.() -> Unit): IndexedInsertSql {
    return IndexedInsertSql.of().INSERT_INTO(table).apply(init)
}


fun NAMED_INSERT_INFO(table: String, init: NamedInsertSql.() -> Unit): NamedInsertSql {
    return NamedInsertSql.of().INSERT_INTO(table).apply(init)
}

