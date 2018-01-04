/**
 * Created the sn.mini.dao.mapper.cell.DateToLongCell.java
 * @created 2016年10月9日 下午5:46:02
 * @version 1.0.0
 */
package sn.mini.dao.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.DateToLongCell.java
 * @author XChao
 */
public class DateToLongCell implements ICell<Long> {

	@Override
	public Long getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getTimestamp(columnLabel).getTime();
	}

}
