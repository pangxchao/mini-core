/**
 * Created the sn.mini.dao.mapper.cell.ShortCell.java
 * @created 2016年10月9日 下午5:38:41
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * sn.mini.dao.mapper.cell.ShortCell.java 
 * @author XChao
 */
public class ShortCell implements ICell<Short> {

	@Override
	public Short getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getShort(columnLabel);
	}

}
