/**
 * Created the com.cfinal.db.mapper.cell.CFObjectCell.java
 * @created 2016年10月9日 下午5:15:23
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFObjectCell.java
 * @author XChao
 */
public class CFObjectCell implements CFCell<Object> {
	@Override
	public Object getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getObject(columnLabel);
	}
}
