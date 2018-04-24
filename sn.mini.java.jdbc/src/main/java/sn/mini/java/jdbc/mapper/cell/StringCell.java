/**
 * Created the sn.mini.dao.mapper.cell.StringCell.java
 * @created 2016年10月9日 下午5:34:09
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.StringCell.java
 * @author XChao
 */
public class StringCell implements ICell<String> {

	@Override
	public String getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getString(columnLabel);
	}

}
