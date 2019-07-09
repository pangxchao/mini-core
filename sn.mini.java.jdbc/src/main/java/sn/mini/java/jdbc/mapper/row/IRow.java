/**
 * Created the sn.mini.jdbc.mapper.row.IRow.java
 * @created 2016年9月24日 上午11:54:02
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.jdbc.mapper.row.IRow.java
 * @author XChao
 */
@FunctionalInterface
public interface IRow<T> {
	public T getRow(ResultSet resultSet) throws SQLException;
}
