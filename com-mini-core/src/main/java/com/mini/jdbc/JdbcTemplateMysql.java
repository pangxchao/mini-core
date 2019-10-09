package com.mini.jdbc;

import static com.mini.util.StringUtil.join;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public final class JdbcTemplateMysql extends JdbcTemplate {

	public JdbcTemplateMysql(@Nonnull DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public final String totals(String str) {
		return join("", "select count(*) from (", str, ") t");
	}

	@Override
	protected String paging(int start, int limit, String str) {
		return join("", str, " limit ", start, ", ", limit);
	}
}
