/**
 * Created the sn.mini.dao.mapper.cell.DateCell.java
 * @created 2016年10月9日 下午5:44:46
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.DateCell.java
 * @author XChao
 */
public class DateCell implements ICell<java.util.Date> {

	@Override
	public java.util.Date getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getTimestamp(columnLabel);
	}

}
