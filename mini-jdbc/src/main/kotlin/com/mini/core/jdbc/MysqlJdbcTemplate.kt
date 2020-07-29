@file:Suppress("unused")

package com.mini.core.jdbc

import javax.sql.DataSource

class MysqlJdbcTemplate @JvmOverloads constructor(
        dataSource: DataSource,
        lazyInit: Boolean = true
) : JdbcTemplate(dataSource, lazyInit) {
    override fun paging(start: Int, limit: Int, sql: String): String {
        return "$sql LIMIT $start, $limit"
    }

    override fun totals(sql: String): String {
        return "SELECT COUNT(*) FROM (${sql}) TB"
    }
}