/**
 * Created the sn.mini.db.implement.MysqlDao.java
 * @created 2017年10月11日 下午5:20:27
 * @version 1.0.0
 */
package sn.mini.java.jdbc.implement;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import sn.mini.java.jdbc.Paging;
import sn.mini.java.util.lang.StringUtil;

/**
 * sn.mini.jdbc.implement.MysqlDao.java
 * 
 * @author XChao
 */
public class MysqlDao extends AbstractDao {

	public MysqlDao(Connection connection) {
		super(connection);
	}

	public MysqlDao(DataSource dataSource) throws SQLException {
		super(dataSource);
	}

	@Override
	protected String totals(String sql) {
		return StringUtil.join("select count(*) from (", sql, ") t");
	}

	@Override
	protected String paging(Paging paging, String sql) {
		return StringUtil.join(sql, " limit ", paging.getStart(), ", ", paging.getRows());
	}

}
