/**
 * Created the sn.mini.dao.mapper.cell.ICell.java
 * @created 2016年9月24日 上午11:49:55
 * @version 1.0.0
 */
package sn.mini.dao.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.ICell.java
 * @author XChao
 */
@FunctionalInterface
public interface ICell<T> {
	public T getCell(ResultSet resultSet, String columnLabel) throws SQLException;
}
