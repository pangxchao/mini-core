@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("SelectSqlKt")

package com.mini.core.data.builder

import com.mini.core.data.builder.statement.HavingStatement
import com.mini.core.data.builder.statement.SelectStatement
import com.mini.core.data.builder.statement.WhereStatement


fun select(vararg columns: String, init: SelectStatement.() -> Unit): SelectSql {
    return object : SelectSql() {}.selects(*columns).apply{
        select { it.apply(init) }
    }
}

fun SelectSql.having(init: HavingStatement.() -> Unit): SelectSql {
    this.having { it.apply(init) }
    return this
}

fun SelectSql.where(init: WhereStatement.() -> Unit): SelectSql {
    this.where { it.apply(init) }
    return this
}





