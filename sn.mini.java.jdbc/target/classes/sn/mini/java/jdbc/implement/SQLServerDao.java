/**
 * Created the sn.mini.dao.implement.SQLServerDao.java
 * @created 2017年11月23日 上午10:49:10
 * @version 1.0.0
 */
package sn.mini.java.jdbc.implement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sn.mini.java.jdbc.Paging;
import sn.mini.java.util.lang.StringUtil;

/**
 * sn.mini.dao.implement.SQLServerDao.java<br/>
 * Ms Sql 数据库
 * @author XChao
 */
public class SQLServerDao extends AbstractDao {
	public SQLServerDao(Connection connection) {
		super(connection);
	}

	public SQLServerDao(DataSource dataSource) throws SQLException {
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
