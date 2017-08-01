/**
 * Created the com.cfinal.db.mapper.cell.CFDateCell.java
 * @created 2016年10月9日 下午5:44:46
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFDateCell.java
 * @author XChao
 */
public class CFDateCell implements CFCell<java.util.Date> {

	@Override
	public java.util.Date getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getTimestamp(columnLabel);
	}

}
