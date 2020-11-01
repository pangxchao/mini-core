@file:Suppress("unused", "FunctionName", "RedundantLambdaArrow")
@file:JvmName("SelectBuilderKt")

package com.mini.core.jdbc.builder


import com.mini.core.jdbc.builder.statement.*

fun INDEXED_SELECT(vararg columns: String, init: SelectStatement.() -> Unit): IndexedSelectSql {
    return IndexedSelectSql.of().SELECT(*columns).apply {
        SELECT { it.apply(init) }
    }
}

fun IndexedSelectSql.ORDER_BY(init: OrderByStatement.() -> Unit): IndexedSelectSql {
    this.ORDER_BY { it.apply(init) }
    return this
}

fun IndexedSelectSql.HAVING(init: HavingStatement.() -> Unit): IndexedSelectSql {
    this.HAVING { it.apply(init) }
    return this
}

fun IndexedSelectSql.WHERE(init: WhereStatement.() -> Unit): IndexedSelectSql {
    this.WHERE { it.apply(init) }
    return this
}

fun IndexedSelectSql.JOIN(init: JoinStatement.() -> Unit): IndexedSelectSql {
    this.JOIN { it.apply(init) }
    return this
}


fun NAMED_SELECT(vararg columns: String, init: SelectStatement.() -> Unit): NamedSelectSql {
    return NamedSelectSql.of().SELECT(*columns).apply {
        SELECT { it.apply(init) }
    }
}

fun NamedSelectSql.ORDER_BY(init: OrderByStatement.() -> Unit): NamedSelectSql {
    this.ORDER_BY { it.apply(init) }
    return this
}

fun NamedSelectSql.HAVING(init: HavingStatement.() -> Unit): NamedSelectSql {
    this.HAVING { it.apply(init) }
    return this
}

fun NamedSelectSql.WHERE(init: WhereStatement.() -> Unit): NamedSelectSql {
    this.WHERE { it.apply(init) }
    return this
}

fun NamedSelectSql.JOIN(init: JoinStatement.() -> Unit): NamedSelectSql {
    this.JOIN { it.apply(init) }
    return this
}




