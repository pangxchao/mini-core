/**
 * Created the sn.mini.dao.mapper.cell.IntegerCell.java
 * @created 2016年10月9日 下午5:37:56
 * @version 1.0.0
 */
package sn.mini.dao.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.IntegerCell.java
 * @author XChao
 */
public class IntegerCell implements ICell<Integer> {

	@Override
	public Integer getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getInt(columnLabel);
	}

}
