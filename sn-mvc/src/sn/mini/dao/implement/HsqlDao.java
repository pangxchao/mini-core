/**
 * Created the sn.mini.dao.implement.HsqlDao.java
 * @created 2017年11月3日 下午3:30:16
 * @version 1.0.0
 */
package sn.mini.dao.implement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sn.mini.dao.Paging;
import sn.mini.util.lang.StringUtil;

/**
 * sn.mini.dao.implement.HsqlDao.java
 * @author XChao
 */
public class HsqlDao extends AbstractDao {
	public HsqlDao(Connection connection) {
		super(connection);
	}

	public HsqlDao(DataSource dataSource) throws SQLException {
		super(dataSource);
	}

	@Override
	protected String totals(String sql) {
		return StringUtil.join("select count(*) from (", sql, ") t");
	}

	@Override
	protected String paging(Paging paging, String sql) {
		return StringUtil.join("select limit ", paging.getStart(), "  ", paging.getRows(), " from (", sql, ")");
	}
}
