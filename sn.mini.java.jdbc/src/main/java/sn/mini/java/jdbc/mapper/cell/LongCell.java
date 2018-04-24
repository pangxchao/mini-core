/**   
 * Created the sn.mini.dao.mapper.cell.LongCell.java
 * @created 2016年10月9日 下午5:37:01 
 * @version 1.0.0 
 */
package sn.mini.java.jdbc.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * sn.mini.dao.mapper.cell.LongCell.java 
 * @author XChao
 */
public class LongCell implements ICell<Long>{

	@Override
	public Long getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getLong(columnLabel);
	}

}
