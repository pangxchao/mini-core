/**
 * Created the sn.mini.dao.mapper.cell.BooleanCell.java
 * @created 2016年10月9日 下午5:42:21
 * @version 1.0.0
 */
package sn.mini.dao.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.BooleanCell.java
 * @author XChao
 */
public class BooleanCell implements ICell<Boolean> {

	@Override
	public Boolean getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getBoolean(columnLabel);
	}

}
