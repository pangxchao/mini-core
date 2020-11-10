@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("DeleteSqlKt")

package com.mini.core.data.builder


import com.mini.core.data.builder.statement.JoinStatement
import com.mini.core.data.builder.statement.WhereStatement


fun INDEXED_DELETE(vararg tables: String, init: IndexedDeleteSql.() -> Unit): IndexedDeleteSql {
    return IndexedDeleteSql.of().DELETE(*tables).apply(init)
}

fun IndexedDeleteSql.WHERE(init: WhereStatement.() -> Unit): IndexedDeleteSql {
    this.WHERE { it.apply(init) }
    return this;
}

fun IndexedDeleteSql.JOIN(init: JoinStatement.() -> Unit): IndexedDeleteSql {
    this.JOIN { it.apply(init) }
    return this;
}


fun NAMED_DELETE(vararg tables: String, init: NamedDeleteSql.() -> Unit): NamedDeleteSql {
    return NamedDeleteSql.of().DELETE(*tables).apply(init)
}

fun NamedDeleteSql.WHERE(init: WhereStatement.() -> Unit): NamedDeleteSql {
    this.WHERE { it.apply(init) }
    return this;
}

fun NamedDeleteSql.JOIN(init: JoinStatement.() -> Unit): NamedDeleteSql {
    this.JOIN { it.apply(init) }
    return this;
}




