package com.mini.core.jdbc.builder

private const val AND = ") AND ("
private const val OR = ") OR ("

open class SQLBuilder constructor() {
    private val params: MutableList<Any?> = mutableListOf()
    private val outerJoin = OuterJoinStatement()
    private val rightJoin = RightJoinStatement()
    private val leftJoin = LeftJoinStatement()
    private val groupBy = GroupByStatement()
    private val orderBy = OrderByStatement()
    private val columns = ColumnStatement()
    private val having = HavingStatement()
    private val values = ValuesStatement()
    private val select = SelectStatement()
    private val table = TableStatement()
    private val where = WhereStatement()
    private val from = FromStatement()
    private val join = JoinStatement()
    private val set = SetStatement()
    private var statement: StatementType? = null
    private var last: WhereStatement? = null
    private var distinct = false

    constructor(builder: SQLBuilder.() -> Unit) : this() {
        builder.invoke(this)
    }

    fun params(vararg param: Any?): SQLBuilder {
        param.forEach { params.add(it) }
        return this
    }

    fun toArray(): Array<Any?> {
        return params.toTypedArray()
    }

    fun insertInto(table: String): SQLBuilder {
        statement = StatementType.INSERT
        this.table.addValues(table)
        return this
    }

    fun replaceInto(table: String): SQLBuilder {
        statement = StatementType.REPLACE
        this.table.addValues(table)
        return this
    }

    fun delete(vararg tables: String): SQLBuilder {
        statement = StatementType.DELETE
        table.addValues(*tables)
        return this
    }

    fun update(vararg tables: String): SQLBuilder {
        statement = StatementType.UPDATE
        table.addValues(*tables)
        return this
    }

    fun select(vararg columns: String?): SQLBuilder {
        statement = StatementType.SELECT
        select.addSelect(*columns)
        return this
    }

    @Suppress("unused")
    fun selectDistinct(vararg columns: String?): SQLBuilder {
        distinct = true
        select(*columns)
        return this
    }

    @JvmOverloads
    fun values(column: String, value: String = "?"): SQLBuilder {
        this.columns.addValues(column)
        values.addValues(value)
        return this
    }

    fun from(vararg tables: String): SQLBuilder {
        from.addValues(*tables)
        return this
    }

    fun join(format: String, vararg args: Any): SQLBuilder {
        join.addValues(String.format(format, *args))
        return this
    }

    fun leftJoin(format: String, vararg args: Any): SQLBuilder {
        leftJoin.addValues(String.format(format, *args))
        return this
    }

    @Suppress("unused")
    fun rightJoin(format: String, vararg args: Any): SQLBuilder {
        rightJoin.addValues(String.format(format, *args))
        return this
    }

    @Suppress("unused")
    fun outerJoin(format: String, vararg args: Any): SQLBuilder {
        outerJoin.addValues(String.format(format, *args))
        return this
    }

    fun set(format: String, vararg args: Any?): SQLBuilder {
        set.addValues(String.format(format, *args))
        return this
    }

    fun where(format: String, vararg args: Any): SQLBuilder {
        where.addValues(String.format(format, *args))
        last = where
        return this
    }

    fun and(): SQLBuilder {
        last?.addAND()
        return this
    }

    fun or(): SQLBuilder {
        last?.addOR()
        return this
    }


    fun groupBy(vararg columns: String): SQLBuilder {
        groupBy.addValues(*columns)
        return this
    }

    @Suppress("unused")
    fun having(format: String, vararg args: Any?): SQLBuilder {
        having.addValues(String.format(format, *args))
        last = having
        return this
    }

    fun orderBy(format: String, vararg args: Any?): SQLBuilder {
        orderBy.addValues(String.format(format, *args))
        return this
    }

    // Insert Into
    @Throws(Error::class)
    private fun insertString(): String {
        val builder = StringBuilder()
        builder.append("INSERT INTO ")
        table.builder(builder)
        columns.builder(builder)
        values.builder(builder)
        return builder.toString() //
    }

    // Replace Into
    @Throws(Error::class)
    private fun replaceString(): String {
        val builder = StringBuilder()
        builder.append("REPLACE INTO ")
        table.builder(builder)
        columns.builder(builder)
        values.builder(builder)
        return builder.toString() //
    }

    // Delete Into
    @Throws(Error::class)
    private fun deleteString(): String {
        val builder = StringBuilder()
        table.builder(builder.append("DELETE "))
        from.builder(builder)
        join.builder(builder)
        leftJoin.builder(builder)
        rightJoin.builder(builder)
        outerJoin.builder(builder)
        where.builder(builder)
        return builder.toString() //
    }

    // Update Into
    @Throws(Error::class)
    private fun updateString(): String {
        val builder = StringBuilder()
        table.builder(builder.append("UPDATE "))
        join.builder(builder)
        leftJoin.builder(builder)
        rightJoin.builder(builder)
        outerJoin.builder(builder)
        set.builder(builder)
        where.builder(builder)
        return builder.toString() //
    }

    // Select Into
    @Throws(Error::class)
    private fun selectString(): String {
        val builder = StringBuilder("SELECT ")
        builder.append(if (distinct) "DISTINCT " else "")
        select.builder(builder)
        from.builder(builder)
        join.builder(builder)
        leftJoin.builder(builder)
        rightJoin.builder(builder)
        outerJoin.builder(builder)
        where.builder(builder)
        groupBy.builder(builder)
        having.builder(builder)
        orderBy.builder(builder)
        return builder.toString() //
    }

    /**
     * 获取SQL完整内容
     * @return SQL完整内容
     */
    @Synchronized
    override fun toString(): String { //  Insert 语句
        if (statement == StatementType.INSERT) {
            return insertString()
        }
        // Replace 语句
        if (statement == StatementType.REPLACE) {
            return replaceString()
        }
        // Delete 语句
        if (statement == StatementType.DELETE) {
            return deleteString()
        }
        // Update 语句
        if (statement == StatementType.UPDATE) {
            return updateString()
        }
        // Select 语句
        if (statement == StatementType.SELECT) {
            return selectString()
        }
        throw RuntimeException("SQL ERROR!")
    }

    // Base Statement
    private abstract class BaseStatement(val keyWord: String, val join: String) {
        val values: MutableList<String> = mutableListOf()
        protected open val close: String = " "
        protected open val open: String = ""

        fun addValues(vararg values: String) {
            values.iterator().forEachRemaining {
                it.let { this.values.add(it) }
            }
        }

        fun builder(builder: StringBuilder) {
            if (values.isEmpty()) return
            builder.append(keyWord).append(open)
            var last = "_________________________"
            for (i in values.indices) {
                val part = values[i]
                if (isJoin(i, part, last)) {
                    builder.append(join)
                }
                builder.append(part)
                last = part
            }
            builder.append(close)
        }

        private fun isJoin(index: Int, part: String, last: String): Boolean {
            return index > 0 && AND != part && OR != part && AND != last && OR != last
        }

    }

    // Table Statement
    private class TableStatement : BaseStatement("", ", ") {
        override val close: String = " "
        override val open: String = ""
    }

    // Column Statement
    private class ColumnStatement : BaseStatement("", ", ") {
        override val close: String = ") "
        override val open: String = "("
    }

    // Values Statement
    private class ValuesStatement : BaseStatement("\nVALUES ", ", ") {
        override val close: String = ") "
        override val open: String = "("
    }

    // Field Statement
    private class SelectStatement : BaseStatement("", ", ") {
        fun addSelect(vararg columns: String?) {
            for (i in 0..columns.size) {
                columns[i]?.let {
                    values.add(
                        if (i <= 0) {
                            "\n\t" + it
                        } else it
                    )
                }
            }
        }
    }

    // From Statement
    private class FromStatement : BaseStatement("\nFROM ", ", ") {
        override val close: String = " "
        override val open: String = ""
    }

    // Join Statement
    private open class JoinStatement(word: String = "\nJOIN ") : BaseStatement(word, word) {
        override val close: String = " "
        override val open: String = ""
    }

    // Left Join Statement
    private class LeftJoinStatement : JoinStatement("\nLEFT JOIN ")

    // Right Join Statement
    private class RightJoinStatement : JoinStatement("\nRIGHT JOIN ")

    // Outer Join Statement
    private class OuterJoinStatement : JoinStatement("\nOUTER JOIN ")

    // Set Statement
    private class SetStatement : BaseStatement("\nSET ", ", ") {
        override val close: String = " "
        override val open: String = ""
    }

    // Where Statement
    private open class WhereStatement(word: String = "\nWHERE ") : BaseStatement(word, AND) {
        override val close: String = ") "
        override val open: String = "("

        fun addAND() {
            values.add(AND)
        }

        fun addOR() {
            values.add(OR)
        }
    }

    // Group By Statement
    private class GroupByStatement : BaseStatement("\nGROUP BY ", ", ") {
        override val close: String = " "
        override val open: String = ""
    }

    private class HavingStatement : WhereStatement("\nHAVING ")
    // Order By Statement
    private class OrderByStatement : BaseStatement("\nORDER BY ", ", ") {
        override val close: String = " "
        override val open: String = ""
    }

    private enum class StatementType {
        INSERT, REPLACE, DELETE, UPDATE, SELECT
    }
}