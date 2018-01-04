/**
 * Created the sn.mini.dao.implement.SybaseDao.java
 * @created 2017年11月23日 上午10:59:20
 * @version 1.0.0
 */
package sn.mini.dao.implement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sn.mini.dao.Paging;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.dao.implement.SybaseDao.java
 * @author XChao
 */
public class SybaseDao extends AbstractDao {
	public SybaseDao(Connection connection) {
		super(connection);
	}

	public SybaseDao(DataSource dataSource) throws SQLException {
		super(dataSource);
	}

	@Override
	protected String totals(String sql) {
		return StringUtil.join("select count(*) from (", sql, ") t");
	}

	@Override
	protected String paging(Paging paging, String sql) {
		return StringUtil.join( // TODO: 未实现
			"select * from ( ", //
			"	select oracle_max_count_rownum.*, rownum oracle_max_count_rownum from (", //
			"		", sql, "	", //
			"	) oracle_max_count_sql_table where rownum <= ", (paging.getStart() + paging.getRows()), " ", //
			") pages_t1 where oracle_max_count_rownum > ", paging.getStart(), " ");
	}
}
