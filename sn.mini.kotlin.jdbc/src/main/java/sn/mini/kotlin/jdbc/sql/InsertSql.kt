package sn.mini.kotlin.jdbc.sql

import sn.mini.kotlin.util.lang.join


open class InsertSql : Sql() {
    protected var tableName: String? = null
    protected val keys = arrayListOf<String>()
    protected val values = arrayListOf<Any?>()

    override fun content() = """ insert into $tableName(${keys.join(", ")})
         values(${values.join(", ")})"""

    companion object {
        fun build(tableName: String): InsertBuild {
            val sql = InsertSql()
            sql.tableName = tableName
            return InsertBuild(sql = sql)
        }
    }

    class InsertBuild(override val sql: InsertSql) : Sql.Build(sql) {
        override fun build(): Sql {
            return this.sql
        }

        override fun append(sql: String): InsertBuild {
            return this
        }

        fun add(key: String, param: Any?): InsertBuild {
            this.sql.keys.add(key)
            this.sql.values.add("?")
            return this.addParams(param)
        }

        fun addNative(key: String, value: String, vararg params: Any?): InsertBuild {
            this.sql.keys.add(key)
            this.sql.keys.add(value)
            return this.addParams(*params)

        }

        override fun addParams(vararg params: Any?): InsertBuild {
            return super.addParams(params) as InsertBuild
        }
    }
}