@file:Suppress("FunctionName", "PrivatePropertyName", "MemberVisibilityCanBePrivate", "unused", "PropertyName")

package com.mini.core.jdbc.builder

import com.mini.core.jdbc.builder.SQLInterfaceDef.Companion.getSQLInterface
import java.io.Serializable
import java.util.*
import java.util.function.Consumer
import kotlin.reflect.KClass

/**
 * SQL构建器
 *
 * 构建结果会根据最后一次提交更改的类型构建，其它与该构建类型不相关的子句将会被忽略
 *
 *
 *
 * @author xchao
 */
class SQLBuilder constructor(init: SQLBuilder.() -> Unit) : EventListener, Serializable {
    private val ON_DUPLICATE_KEY_UPDATE = OnDuplicateKeyUpdateStatement()
    private val OUTER_JOIN = OuterJoinStatement()
    private val INNER_JOIN = InnerJoinStatement()
    private val RIGHT_JOIN = RightJoinStatement()
    private var STATEMENT: StatementType? = null
    private val LEFT_JOIN = LeftJoinStatement()
    private val GROUP_BY = GroupByStatement()
    private val ORDER_BY = OrderByStatement()
    private var LAST: WhereStatement? = null
    private val COLUMNS = ColumnStatement()
    private val HAVING = HavingStatement()
    private val VALUES = ValuesStatement()
    private val SELECT = SelectStatement()
    private val TABLE = TableStatement()
    private val WHERE = WhereStatement()
    private val FROM = FromStatement()
    private val JOIN = JoinStatement()
    private val SET = SetStatement()
    private var DISTINCT = false

    val ARGS: MutableList<Any?> = mutableListOf()
    val SQL: String
        get() {
            //  INSERT 语句
            if (STATEMENT == StatementType.INSERT) {
                return INSERT_STRING()
            }
            // REPLACE 语句
            if (STATEMENT == StatementType.REPLACE) {
                return REPLACE_STRING()
            }
            // DELETE 语句
            if (STATEMENT == StatementType.DELETE) {
                return DELETE_STRING()
            }
            // UPDATE 语句
            if (STATEMENT == StatementType.UPDATE) {
                return UPDATE_STRING()
            }
            // SELECT 语句
            if (STATEMENT == StatementType.SELECT) {
                return SELECT_STRING()
            }
            // INSERT INTO ON DUPLICATE KEY UPDATE
            if (STATEMENT == StatementType.INSERT_UPDATE) {
                return INSERT_ON_UPDATE_STRING()
            }
            throw RuntimeException("SQL ERROR!")
        }

    init {
        this.apply(init)
    }

    constructor(consumer: Consumer<SQLBuilder>) : this({
        consumer.accept(this)
    })

    fun ARGS(vararg args: Any?): SQLBuilder {
        ARGS.addAll(listOf(args))
        return this
    }

    fun INSERT_INTO(table: String): SQLBuilder {
        STATEMENT = StatementType.INSERT
        TABLE.ADD_VALUES(table)
        return this
    }

    fun <T : Any> INSERT_INTO(instance: T): SQLBuilder {
        getSQLInterface(instance::class as KClass<*>).createInsert(this, instance)
        return this
    }

    fun REPLACE_INTO(table: String): SQLBuilder {
        STATEMENT = StatementType.REPLACE
        TABLE.ADD_VALUES(table)
        return this
    }

    fun <T : Any> REPLACE_INTO(instance: T): SQLBuilder {
        getSQLInterface(instance::class as KClass<*>).createReplace(this, instance)
        return this
    }

    fun DELETE(vararg tables: String): SQLBuilder {
        STATEMENT = StatementType.DELETE
        TABLE.ADD_VALUES(*tables)
        return this
    }

    fun UPDATE(vararg tables: String): SQLBuilder {
        STATEMENT = StatementType.UPDATE
        TABLE.ADD_VALUES(*tables)
        return this
    }

    fun <T : Any> UPDATE(instance: T): SQLBuilder {
        getSQLInterface(instance::class as KClass<*>).createUpdate(this, instance)
        return this
    }

    fun <T : Any> SELECT_FROM_JOIN(type: KClass<T>): SQLBuilder {
        getSQLInterface(type).createSelect(this, type)
        return this
    }

    fun SELECT(vararg columns: String): SQLBuilder {
        STATEMENT = StatementType.SELECT
        SELECT.ADD_SELECT(*columns)
        return this
    }

    fun SELECT_DISTINCT(vararg columns: String): SQLBuilder {
        DISTINCT = true
        SELECT(*columns)
        return this
    }

    @JvmOverloads
    fun VALUES(column: String, value: String = "?"): SQLBuilder {
        COLUMNS.ADD_VALUES(column)
        VALUES.ADD_VALUES(value)
        return this
    }

    fun FROM(vararg tables: String): SQLBuilder {
        FROM.ADD_VALUES(*tables)
        return this
    }

    fun FROM(builder: SQLBuilder): SQLBuilder {
        return FROM(builder.toString())
    }

    fun JOIN(join: String): SQLBuilder {
        JOIN.ADD_VALUES(join)
        return this
    }

    fun INNER_JOIN(join: String): SQLBuilder {
        INNER_JOIN.ADD_VALUES(join)
        return this
    }

    fun LEFT_JOIN(join: String): SQLBuilder {
        LEFT_JOIN.ADD_VALUES(join)
        return this
    }

    fun RIGHT_JOIN(join: String): SQLBuilder {
        RIGHT_JOIN.ADD_VALUES(join)
        return this
    }

    fun OUTER_JOIN(joins: String): SQLBuilder {
        OUTER_JOIN.ADD_VALUES(joins)
        return this
    }

    fun SET(sets: String): SQLBuilder {
        SET.ADD_VALUES(sets)
        return this
    }

    fun ON_DUPLICATE_KEY_UPDATE(sets: String): SQLBuilder {
        ON_DUPLICATE_KEY_UPDATE.ADD_VALUES(sets)
        STATEMENT = StatementType.INSERT_UPDATE
        return this
    }

    fun <T : Any> ON_DUPLICATE_KEY_UPDATE(instance: T): SQLBuilder {
        getSQLInterface(instance::class as KClass<*>).createInsertOnUpdate(this, instance)
        return this
    }

    fun AND(): SQLBuilder {
        if (LAST != null) {
            LAST!!.AND()
        }
        return this
    }

    fun OR(): SQLBuilder {
        if (LAST != null) {
            LAST!!.OR()
        }
        return this
    }

    fun WHERE(wheres: String): SQLBuilder {
        WHERE.ADD_VALUES(wheres)
        LAST = WHERE
        return this
    }

    fun WHERE_IN(column: String, args: Array<Any?>): SQLBuilder {
        return WHERE("""$column IN (${args.joinToString { "?" }}""").ARGS(*args)
    }

    fun WHERE_MATCH(vararg column: String, args: String): SQLBuilder {
        return WHERE("MATCH(${column.joinToString()}) AGAINST(?)").ARGS(args)
    }

    fun WHERE_MATCH_IN_BOOL(vararg column: String, args: String): SQLBuilder {
        return WHERE("MATCH(${column.joinToString()}) AGAINST(?)").ARGS(args)
    }

    fun GROUP_BY(vararg columns: String): SQLBuilder {
        GROUP_BY.ADD_VALUES(*columns)
        return this
    }

    fun HAVING(having: String): SQLBuilder {
        HAVING.ADD_VALUES(having)
        LAST = HAVING
        return this
    }

    fun HAVING_IN(column: String, args: Array<Any?>): SQLBuilder {
        return HAVING("""$column IN (${args.joinToString(", ") { "?" }}""").ARGS(*args)
    }

    fun HAVING_MATCH(vararg column: String, args: String): SQLBuilder {
        return HAVING("MATCH(${column.joinToString()}) AGAINST(?)").ARGS(args)
    }

    fun HAVING_MATCH_IN_BOOL(vararg column: String, args: String): SQLBuilder {
        return HAVING("MATCH(${column.joinToString()}) AGAINST(?)").ARGS(args)
    }

    fun ORDER_BY(orderBy: String): SQLBuilder {
        ORDER_BY.ADD_VALUES(orderBy)
        return this
    }

    // Insert Into
    private fun INSERT_STRING(): String {
        val builder = StringBuilder()
        builder.append("INSERT INTO ")
        TABLE.BUILDER(builder)
        COLUMNS.BUILDER(builder)
        VALUES.BUILDER(builder)
        return builder.toString()
    }

    // Replace Into
    private fun REPLACE_STRING(): String {
        val builder = StringBuilder()
        builder.append("REPLACE INTO ")
        TABLE.BUILDER(builder)
        COLUMNS.BUILDER(builder)
        VALUES.BUILDER(builder)
        return builder.toString()
    }

    // Delete Into
    private fun DELETE_STRING(): String {
        val builder = StringBuilder()
        TABLE.BUILDER(builder.append("DELETE "))
        FROM.BUILDER(builder)
        JOIN.BUILDER(builder)
        LEFT_JOIN.BUILDER(builder)
        RIGHT_JOIN.BUILDER(builder)
        OUTER_JOIN.BUILDER(builder)
        WHERE.BUILDER(builder)
        return builder.toString()
    }

    // Update Into
    private fun UPDATE_STRING(): String {
        val builder = StringBuilder()
        TABLE.BUILDER(builder.append("UPDATE "))
        JOIN.BUILDER(builder)
        LEFT_JOIN.BUILDER(builder)
        RIGHT_JOIN.BUILDER(builder)
        OUTER_JOIN.BUILDER(builder)
        SET.BUILDER(builder)
        WHERE.BUILDER(builder)
        return builder.toString()
    }

    // Select Into
    private fun SELECT_STRING(): String {
        val builder = StringBuilder("SELECT ")
        builder.append(if (DISTINCT) "DISTINCT " else "")
        SELECT.BUILDER(builder)
        FROM.BUILDER(builder)
        JOIN.BUILDER(builder)
        LEFT_JOIN.BUILDER(builder)
        RIGHT_JOIN.BUILDER(builder)
        OUTER_JOIN.BUILDER(builder)
        WHERE.BUILDER(builder)
        GROUP_BY.BUILDER(builder)
        HAVING.BUILDER(builder)
        ORDER_BY.BUILDER(builder)
        return builder.toString()
    }

    private fun INSERT_ON_UPDATE_STRING(): String {
        val builder = StringBuilder()
        builder.append("INSERT INTO ")
        TABLE.BUILDER(builder)
        COLUMNS.BUILDER(builder)
        VALUES.BUILDER(builder)
        ON_DUPLICATE_KEY_UPDATE.BUILDER(builder)
        return builder.toString()
    }

    @Synchronized
    override fun toString(): String {
        return "${SQL}\n$ARGS"
    }

    // Base Statement
    private abstract class BaseStatement constructor(val keyWord: String, val join: String) {
        protected val values: MutableList<String> = mutableListOf()

        companion object {
            const val AND = ") AND ("
            const val OR = ") OR ("
        }

        protected open fun OPEN(): String {
            return ""
        }

        protected open fun CLOSE(): String {
            return ""
        }

        fun ADD_VALUES(vararg values: String) {
            values.takeIf { it.isNotEmpty() }?.let {
                this.values.addAll(values)
            }
        }

        fun BUILDER(builder: StringBuilder) {
            this.values.takeIf { it.isNotEmpty() }?.let {
                builder.append(keyWord).append(OPEN())
                var last = "_________________________"
                for (i in values.indices) {
                    val part = values[i]
                    if (IS_JOIN(i, part, last)) {
                        builder.append(join)
                    }
                    builder.append(part)
                    last = part
                }
                builder.append(CLOSE())
            }
        }

        private fun IS_JOIN(index: Int, part: String, last: String): Boolean {
            return index > 0 && AND != part && AND != last && OR != part && OR != last
        }
    }

    // Table Statement
    private class TableStatement : BaseStatement("", ", ") {
        override fun OPEN(): String {
            return ""
        }

        override fun CLOSE(): String {
            return " "
        }
    }

    // Column Statement
    private class ColumnStatement : BaseStatement("", ", ") {
        override fun OPEN(): String {
            return "("
        }

        override fun CLOSE(): String {
            return ") "
        }
    }

    // Values Statement
    private class ValuesStatement : BaseStatement("\nVALUES ", ", ") {
        override fun OPEN(): String {
            return "("
        }

        override fun CLOSE(): String {
            return ")"
        }
    }

    // Field Statement
    private class SelectStatement : BaseStatement("", ", ") {
        override fun OPEN(): String {
            return ""
        }

        override fun CLOSE(): String {
            return " "
        }

        fun ADD_SELECT(vararg columns: String) = mutableListOf(*columns).takeIf {
            it.isNotEmpty()
        }?.let {
            it.set(index = 0, element = "\t\n${it[0]}")
            this@SelectStatement.values.addAll(it)
        }
    }

    // From Statement
    private class FromStatement : BaseStatement("\nFROM ", ", ") {
        override fun OPEN(): String {
            return ""
        }

        override fun CLOSE(): String {
            return " "
        }
    }

    // Join Statement
    private open class JoinStatement constructor(word: String = "\nJOIN ") : BaseStatement(word, word) {

        override fun OPEN(): String {
            return ""
        }


        override fun CLOSE(): String {
            return " "
        }
    }

    // Inner Join Statement
    private class InnerJoinStatement constructor(word: String = "\nINNER JOIN ") : JoinStatement(word)

    // Left Join Statement
    private class LeftJoinStatement constructor(word: String = "\nLEFT JOIN ") : JoinStatement(word)

    // Right Join Statement
    private class RightJoinStatement constructor(word: String = "\nRIGHT JOIN ") : JoinStatement(word)

    // Outer Join Statement
    private class OuterJoinStatement constructor(word: String = "\nOUTER JOIN ") : JoinStatement(word)

    // Set Statement
    private class SetStatement : BaseStatement("\nSET ", ", ") {
        override fun OPEN(): String {
            return ""
        }

        override fun CLOSE(): String {
            return " "
        }
    }

    private class OnDuplicateKeyUpdateStatement : BaseStatement("\nON DUPLICATE KEY UPDATE ", ",") {
        override fun OPEN(): String {
            return ""
        }

        override fun CLOSE(): String {
            return " "
        }
    }

    // Where Statement
    private open class WhereStatement constructor(word: String = "\nWHERE ") : BaseStatement(word, AND) {
        override fun OPEN(): String {
            return "("
        }

        override fun CLOSE(): String {
            return ") "
        }

        fun AND() {
            values.add(AND)
        }

        fun OR() {
            values.add(OR)
        }
    }

    // Group By Statement
    private class GroupByStatement : BaseStatement("\nGROUP BY ", ", ") {
        override fun OPEN(): String {
            return ""
        }

        override fun CLOSE(): String {
            return " "
        }
    }

    // Having Statement
    private class HavingStatement constructor(word: String = "\nHAVING ") : WhereStatement(word)

    // Order By Statement
    private class OrderByStatement : BaseStatement("\nORDER BY ", ", ") {
        override fun OPEN(): String {
            return ""
        }

        override fun CLOSE(): String {
            return " "
        }
    }

    private enum class StatementType {
        INSERT, REPLACE, DELETE, UPDATE, SELECT, INSERT_UPDATE
    }

    class SQLWhere(private val sqlBuilder: SQLBuilder) : Serializable {
        private val WHERE = WhereStatement()
        fun AND(): SQLWhere {
            WHERE.AND()
            return this
        }

        fun OR(): SQLWhere {
            WHERE.OR()
            return this
        }

        fun WHERE(wheres: String): SQLWhere {
            WHERE.ADD_VALUES(wheres)
            return this
        }

        fun WHERE_IN(column: String, args: Array<Any?>): SQLWhere {
            args.takeIf { it.isNotEmpty() }?.let {
                WHERE("$column IN(${args.joinToString(", ") { "?" }})")
                sqlBuilder.ARGS(*args)
            }
            return this
        }

    }

    class SQLHaving(private val sqlBuilder: SQLBuilder) : Serializable {
        private val HAVING: HavingStatement = HavingStatement()
        fun AND(): SQLHaving {
            HAVING.AND()
            return this
        }

        fun OR(): SQLHaving {
            HAVING.OR()
            return this
        }

        fun HAVING(having: String): SQLHaving {
            HAVING.ADD_VALUES(having)
            return this
        }

        fun WHERE_IN(column: String, args: Array<Any?>): SQLHaving {
            args.takeIf { it.isNotEmpty() }?.let {
                HAVING("$column IN(${args.joinToString(", ") { "?" }})")
                sqlBuilder.ARGS(*args)
            }
            return this
        }
    }
}