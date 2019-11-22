package com.mini.core.jdbc;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import static com.mini.core.util.StringUtil.join;

public class JdbcTemplateOracle extends JdbcTemplate {

    public JdbcTemplateOracle(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Nonnull
    @Override
    public String totals(String str) {
        return join("", "select count(*) from (", str, ") t");
    }

    @Nonnull
    @Override
    public String paging(int start, int limit, String str) {
        return join("", "select * from (", //
                "   select max_count.*, rownum row_number from (", str, ") max_count where rownum <= ", (start + limit),
                ") max_count_rownum where row_number > ", limit);
    }
}
