package com.mini.jdbc.spring;

import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.util.Paging;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

public class JdbcTemplateOracle extends JdbcTemplate {

    public JdbcTemplateOracle(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    protected String paging(Paging paging, String sql) {
        return StringUtil.join("", "select * from (",
                "   select max_count.*, rownum row_number from (", sql, ") max_count where rownum <= ", (paging.getStart() + paging.getRows()),
                ") max_count_rownum where row_number > ", paging.getStart());
    }
}
