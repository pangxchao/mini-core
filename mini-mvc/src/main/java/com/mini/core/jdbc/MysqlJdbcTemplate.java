package com.mini.core.jdbc;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public final class MysqlJdbcTemplate extends JdbcTemplate {
	
	public MysqlJdbcTemplate(@Nonnull DataSource dataSource) {
		super(dataSource);
	}
	
	@Nonnull
	@Override
	public final String totals(String str) {
		return StringUtils.join("SELECT COUNT(*) FROM (", str, ") t");
	}
	
	@Nonnull
	@Override
	protected String paging(int start, int limit, String str) {
		return StringUtils.join(str, " LIMIT ", start, ", ", limit);
	}
}
