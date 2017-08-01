/**
 * Created the com.cfinal.db.mapper.cell.CFStringCell.java
 * @created 2016年10月9日 下午5:34:09
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFStringCell.java
 * @author XChao
 */
public class CFStringCell implements CFCell<String> {

	@Override
	public String getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getString(columnLabel);
	}

}
