package com.mini.jdbc.spring;

import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.util.Paging;
import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

public class JdbcTemplateMysql extends JdbcTemplate {

    public JdbcTemplateMysql(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String totals(String sql) {
        return StringUtil.join("", "select count(*) from (", sql, ") t");
    }

    @Override
    protected String paging(Paging paging, String sql) {
        return StringUtil.join("", sql, " limit ", paging.getStart(), ", ", paging.getRows());
    }
}
