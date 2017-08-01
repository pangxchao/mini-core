/**
 * Created the com.cfinal.db.mapper.cell.CFFloatCell.java
 * @created 2016年10月9日 下午5:41:30
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.cell.CFFloatCell.java
 * @author XChao
 */
public class CFFloatCell implements CFCell<Float> {

	@Override
	public Float getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getFloat(columnLabel);
	}

}
