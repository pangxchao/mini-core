/**
 * Created the com.cfinal.db.mapper.cell.CFDateToLongCell.java
 * @created 2016年10月9日 下午5:46:02
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFDateToLongCell.java
 * @author XChao
 */
public class CFDateToLongCell implements CFCell<Long> {

	@Override
	public Long getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getTimestamp(columnLabel).getTime();
	}

}
