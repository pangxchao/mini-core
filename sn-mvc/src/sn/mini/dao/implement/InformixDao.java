/**
 * Created the sn.mini.dao.implement.InformixDao.java
 * @created 2017年11月23日 上午11:02:03
 * @version 1.0.0
 */
package sn.mini.dao.implement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sn.mini.dao.Paging;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.dao.implement.InformixDao.java
 * @author XChao
 */
public class InformixDao extends AbstractDao {
	public InformixDao(Connection connection) {
		super(connection);
	}

	public InformixDao(DataSource dataSource) throws SQLException {
		super(dataSource);
	}

	@Override
	protected String totals(String sql) {
		return StringUtil.join("select count(*) from (", sql, ") t");
	}

	@Override
	protected String paging(Paging paging, String sql) {
		// select skip 2 first 2 * from test_rowcols where 1=1 order by score;
		return StringUtil.join( //
			"select skip " + paging.getStart() + " first " + paging.getRows() + " ( ", //
			"	", sql, "	", //
			") ");
	}
}
