/**
 * Created the sn.mini.jdbc.mapper.cell.ObjectCell.java
 * @created 2016年10月9日 下午5:15:23
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * sn.mini.jdbc.mapper.cell.ObjectCell.java
 * @author XChao
 */
public class ObjectCell implements ICell<Object> {
	@Override
	public Object getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getObject(columnLabel);
	}
}
