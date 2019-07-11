package com.mini.jdbc;

import com.mini.jdbc.util.Paging;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class JdbcTemplateMysql extends JdbcTemplate {

    public JdbcTemplateMysql(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public String paging(Paging paging, String sql) {
        return StringUtil.join("", sql, " limit ", paging.getStart(), ", ", paging.getRows());
    }

}
