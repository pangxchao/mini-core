@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("UpdateBuilderKt")

package com.mini.core.data.builder

import com.mini.core.data.builder.statement.SetStatement
import com.mini.core.data.builder.statement.WhereStatement

fun update(vararg tables: String, init: UpdateSql.() -> Unit): UpdateSql {
    return UpdateSql.of().update(*tables).apply(init)
}

fun UpdateSql.where(init: WhereStatement.() -> Unit): UpdateSql {
    this.where { it.apply(init) }
    return this
}

fun UpdateSql.set(init: SetStatement.() -> Unit): UpdateSql {
    this.set { it.apply(init) }
    return this
}

