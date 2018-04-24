/**
 * Created the sn.mini.dao.mapper.cell.DoubleCell.java
 * @created 2016年10月9日 下午5:40:46
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.DoubleCell.java
 * @author XChao
 */
public class DoubleCell implements ICell<Double> {

	@Override
	public Double getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getDouble(columnLabel);
	}

}
