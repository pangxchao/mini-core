/**
 * Created the sn.mini.dao.DaoFactory.java
 * @created 2017年3月2日 上午11:52:48
 * @version 1.0.0
 */
package sn.mini.java.jdbc;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * sn.mini.dao.DaoFactory.java
 * @author XChao
 */
@FunctionalInterface
public interface DaoFactory {
	public abstract IDao create(DataSource dataSource) throws SQLException;
}
