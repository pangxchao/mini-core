/**
 * Created the sn.mini.dao.mapper.cell.ByteCell.java
 * @created 2016年10月9日 下午5:39:31
 * @version 1.0.0
 */
package sn.mini.dao.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.dao.mapper.cell.ByteCell.java
 * @author XChao
 */
public class ByteCell implements ICell<Byte> {

	@Override
	public Byte getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getByte(columnLabel);
	}

}
