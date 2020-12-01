@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("DeleteSqlKt")

package com.mini.core.data.builder


import com.mini.core.data.builder.statement.WhereStatement


fun delete(vararg tables: String, init: DeleteSql.() -> Unit): DeleteSql {
    return object : DeleteSql() {}.delete(*tables).apply(init)
}

fun DeleteSql.where(init: WhereStatement.() -> Unit): DeleteSql {
    this.where { it.apply(init) }
    return this
}





