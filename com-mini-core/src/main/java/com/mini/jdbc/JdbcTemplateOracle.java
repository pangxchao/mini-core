package com.mini.jdbc;

import static com.mini.util.StringUtil.join;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

public class JdbcTemplateOracle extends JdbcTemplate {

	public JdbcTemplateOracle(@Nonnull DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public String totals(String str) {
		return join("", "select count(*) from (", str, ") t");
	}

	@Override
	public String paging(int start, int limit, String str) {
		return join("", "select * from (", //
				"   select max_count.*, rownum row_number from (", str, ") max_count where rownum <= ", (start + limit),
				") max_count_rownum where row_number > ", limit);
	}
}
