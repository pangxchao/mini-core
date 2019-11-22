package com.mini.core.jdbc;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.sql.DataSource;

import static com.mini.core.util.StringUtil.join;

@Singleton
public class JdbcTemplateMysql extends JdbcTemplate {

    public JdbcTemplateMysql(@Nonnull DataSource dataSource) {
        super(dataSource);
    }

    @Nonnull
    @Override
    public final String totals(String str) {
        return join("", "select count(*) from (", str, ") t");
    }

    @Nonnull
    @Override
    protected String paging(int start, int limit, String str) {
        return join("", str, " limit ", start, ", ", limit);
    }
}
