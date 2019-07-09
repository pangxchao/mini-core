package com.mini.jdbc;

import com.mini.jdbc.util.Paging;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

public class JdbcTemplateOracle extends JdbcTemplate {

    public JdbcTemplateOracle(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", "select * from (",
                "   select max_count.*, rownum row_number from (", sql, ") max_count where rownum <= ", (paging.getStart() + paging.getRows()),
                ") max_count_rownum where row_number > ", paging.getStart());
    }
}
