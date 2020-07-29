@file:Suppress("unused")

package com.mini.core.jdbc;

import javax.sql.DataSource

class OracleJdbcTemplate @JvmOverloads
constructor(
        dataSource: DataSource,
        lazyInit: Boolean = true
) : JdbcTemplate(dataSource, lazyInit) {
    override fun paging(start: Int, limit: Int, sql: String): String {
        return """ 
            |SELECT MAX_COUNT_ROWNUM.* FROM ( 
            |    SELECT MAX_COUNT.*, rownum ROW_NUMBER FROM (
            |        $sql
            |    ) MAX_COUNT WHERE rownum <= ${start + limit}"  
            |) MAX_COUNT_ROWNUM WHERE ROW_NUMBER > $limit, 
        """.trimIndent()
    }

    override fun totals(sql: String): String {
        return "SELECT COUNT(*) FROM (${sql}) TB"
    }
}

