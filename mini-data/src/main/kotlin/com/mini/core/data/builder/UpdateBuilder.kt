@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("UpdateBuilderKt")

package com.mini.core.data.builder

import com.mini.core.data.builder.statement.JoinStatement
import com.mini.core.data.builder.statement.SetStatement
import com.mini.core.data.builder.statement.WhereStatement

fun INDEXED_UPDATE(vararg tables: String, init: IndexedUpdateSql.() -> Unit): IndexedUpdateSql {
    return IndexedUpdateSql.of().UPDATE(*tables).apply(init)
}

fun IndexedUpdateSql.WHERE(init: WhereStatement.() -> Unit): IndexedUpdateSql {
    this.WHERE { it.apply(init) }
    return this
}

fun IndexedUpdateSql.JOIN(init: JoinStatement.() -> Unit): IndexedUpdateSql {
    this.JOIN { it.apply(init) }
    return this
}

fun IndexedUpdateSql.SET(init: SetStatement.() -> Unit): IndexedUpdateSql {
    this.SET { it.apply(init) }
    return this
}


fun NAMED_UPDATE(vararg tables: String, init: NamedUpdateSql.() -> Unit): NamedUpdateSql {
    return NamedUpdateSql.of().UPDATE(*tables).apply(init)
}

fun NamedUpdateSql.WHERE(init: WhereStatement.() -> Unit): NamedUpdateSql {
    this.WHERE { it.apply(init) }
    return this
}

fun NamedUpdateSql.JOIN(init: JoinStatement.() -> Unit): NamedUpdateSql {
    this.JOIN { it.apply(init) }
    return this
}

fun NamedUpdateSql.SET(init: SetStatement.() -> Unit): NamedUpdateSql {
    this.SET { it.apply(init) }
    return this
}


