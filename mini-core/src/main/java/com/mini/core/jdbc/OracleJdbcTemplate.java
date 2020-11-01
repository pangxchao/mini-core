package com.mini.core.jdbc;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public final class OracleJdbcTemplate extends JdbcTemplate {
	
	public OracleJdbcTemplate(@Nonnull DataSource dataSource) {
		super(dataSource);
	}
	
	@Nonnull
	@Override
	public String totals(String str) {
		return StringUtils.join("SELECT COUNT(*) FROM (", str, ") t");
	}
	
	@Nonnull
	@Override
	public String paging(int start, int limit, String str) {
		return StringUtils.join("SELECT MAX_COUNT_ROWNUM.* FROM ( \n", //
				"   SELECT MAX_COUNT.*, rownum ROW_NUMBER FROM ( \n",
				"      ", str, " \n",
				"   ) MAX_COUNT WHERE rownum <= ", (start + limit), "\n",
				") MAX_COUNT_ROWNUM WHERE ROW_NUMBER > ", limit, "\n");
	}
}
