package sn.mini.kotlin.jdbc.sql

import sn.mini.kotlin.util.lang.join

fun main(args: Array<String>) {
}

class ReplaceSql : InsertSql() {
    override fun content() = """ replace into $tableName(${keys.join(", ")})
         values(${values.join(", ")})"""

    companion object {
        fun build(tableName: String): InsertBuild {
            val sql = ReplaceSql()
            sql.tableName = tableName
            return InsertBuild(sql = sql)
        }
    }
}