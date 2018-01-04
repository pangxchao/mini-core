/**
 * Created the sn.mini.dao.implement.DB2Dao.java
 * @created 2017年11月3日 下午3:28:43
 * @version 1.0.0
 */
package sn.mini.dao.implement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sn.mini.dao.Paging;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.dao.implement.DB2Dao.java
 * @author XChao
 */
public class DB2Dao extends AbstractDao {
	public DB2Dao(Connection connection) throws SQLException {
		super(connection);
	}

	public DB2Dao(DataSource dataSource) throws SQLException {
		super(dataSource);
	}

	@Override
	protected String totals(String sql) {
		return StringUtil.join("select count(*) from (", sql, ") t");
	}

	@Override
	protected String paging(Paging paging, String sql) {
		return StringUtil.join( //
			"select top " + paging.getRows() + " * from ( ", //
			"	select row_number() over(order by id) as rownumber, * (", //
			"		", sql, "	", //
			"	) ", //
			") ms_sql_max_count_sql_table where rownumber > " + paging.getStart());
	}
}
