/**
 * Created the com.cfinal.db.mapper.cell.CFShortCell.java
 * @created 2016年10月9日 下午5:38:41
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFShortCell.java
 * @author XChao
 */
public class CFShortCell implements CFCell<Short> {

	@Override
	public Short getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getShort(columnLabel);
	}

}
