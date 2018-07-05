/**
 * Created the sn.mini.dao.DaoFactory.java
 * @created 2017年3月2日 上午11:52:48
 * @version 1.0.0
 */
package sn.mini.java.jdbc;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * sn.mini.dao.DaoFactory.java
 * @author XChao
 */
@FunctionalInterface
public interface DaoFactory {
	IDao create(DataSource dataSource) throws SQLException;
}
