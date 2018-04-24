/**
 * Created the sn.mini.dao.implement.OracleDao.java
 * @created 2017年11月3日 下午3:21:40
 * @version 1.0.0
 */
package sn.mini.java.jdbc.implement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sn.mini.java.jdbc.Paging;
import sn.mini.java.util.lang.StringUtil;

/**
 * sn.mini.dao.implement.OracleDao.java
 * @author XChao
 */
public class OracleDao extends AbstractDao {
	public OracleDao(Connection connection) {
		super(connection);
	}

	public OracleDao(DataSource dataSource) throws SQLException {
		super(dataSource);
	}

	@Override
	protected String totals(String sql) {
		return StringUtil.join("select count(*) from (", sql, ") t");
	}

	@Override
	protected String paging(Paging paging, String sql) {
		return StringUtil.join( //
			"select * from ( ", //
			"	select oracle_max_count_rownum.*, rownum oracle_max_count_rownum from (", //
			"		", sql, "	", //
			"	) oracle_max_count_sql_table where rownum <= ", (paging.getStart() + paging.getRows()), " ", //
			") pages_t1 where oracle_max_count_rownum > ", paging.getStart(), " ");
	}
}
