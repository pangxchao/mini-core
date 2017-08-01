/**
 * Created the com.cfinal.db.mapper.cell.CFDoubleCell.java
 * @created 2016年10月9日 下午5:40:46
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFDoubleCell.java
 * @author XChao
 */
public class CFDoubleCell implements CFCell<Double> {

	@Override
	public Double getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getDouble(columnLabel);
	}

}
