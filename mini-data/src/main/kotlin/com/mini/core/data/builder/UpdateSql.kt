@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("UpdateSqlKt")

package com.mini.core.data.builder

import com.mini.core.data.builder.statement.SetStatement
import com.mini.core.data.builder.statement.WhereStatement

fun update(vararg tables: String, init: UpdateSql.() -> Unit): UpdateSql {
    return object : UpdateSql() {}.update(*tables).apply(init)
}

fun UpdateSql.where(init: WhereStatement.() -> Unit): UpdateSql {
    this.where { it.apply(init) }
    return this
}

fun UpdateSql.set(init: SetStatement.() -> Unit): UpdateSql {
    this.set { it.apply(init) }
    return this
}

