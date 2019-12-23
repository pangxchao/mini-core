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
		return StringUtils.join("select count(*) from (", str, ") t");
	}

	@Nonnull
	@Override
	public String paging(int start, int limit, String str) {
		return StringUtils.join("select * from (", //
			"   select max_count.*, rownum row_number from (", str, ") max_count where rownum <= ", (start + limit),
			") max_count_rownum where row_number > ", limit);
	}
}