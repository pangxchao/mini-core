/**
 * Created the sn.mini.dao.mapper.cell.FloatCell.java
 * @created 2016年10月9日 下午5:41:30
 * @version 1.0.0
 */
package sn.mini.dao.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.FloatCell.java
 * @author XChao
 */
public class FloatCell implements ICell<Float> {

	@Override
	public Float getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getFloat(columnLabel);
	}

}
