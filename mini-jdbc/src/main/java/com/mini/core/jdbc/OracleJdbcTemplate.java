package com.mini.core.jdbc;

import javax.sql.DataSource;

import static java.lang.String.format;

public class OracleJdbcTemplate extends JdbcTemplate {
    public OracleJdbcTemplate() {
    }

    public OracleJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public OracleJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    protected String paging(int start, int limit, String sql) {
        return format("" +
                "SELECT MAX_COUNT_ROWNUM.* FROM (" +
                "    SELECT MAX_COUNT.*, rownum ROW_NUMBER FROM (" +
                "        %s" +
                "    ) MAX_COUNT WHERE rownum <= %d" +
                ") MAX_COUNT_ROWNUM WHERE ROW_NUMBER > %d" +
                "", sql, start + limit, limit);
    }

    @Override
    protected String totals(String sql) {
        return format("SELECT COUNT(*) FROM (%s) TB", sql);
    }
}

