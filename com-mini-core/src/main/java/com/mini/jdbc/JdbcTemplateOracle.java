package com.mini.jdbc;

import com.mini.jdbc.util.Paging;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

import static com.mini.util.StringUtil.join;

public class JdbcTemplateOracle extends JdbcTemplate {

    public JdbcTemplateOracle(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String totals(String str) {
        return join("", "select count(*) from (", str, ") t");
    }

    @Override
    public String paging(Paging paging, String str) {
        return join("", "select * from (", //
                "   select max_count.*, rownum row_number from (", str, ") max_count ",
                "        where rownum <= ", (paging.getSkip() + paging.getLimit()),
                ") max_count_rownum where row_number > ", paging.getSkip());
    }
}
