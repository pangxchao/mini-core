package com.mini.jdbc;

import com.mini.jdbc.util.Paging;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.sql.DataSource;

import static com.mini.util.StringUtil.join;

@Singleton
public final class JdbcTemplateMysql extends JdbcTemplate {

    public JdbcTemplateMysql(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public final String totals(String sql) {
        return join("", "select count(*) from (", sql, ") t");
    }

    @Override
    public final String paging(Paging paging, String sql) {
        return join("", sql, " limit ", paging.getSkip(), //
                ", ", paging.getLimit());
    }

}
