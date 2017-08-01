/**
 * Created the com.cfinal.db.mapper.cell.CFDateFormatCell.java
 * @created 2016年10月9日 下午5:47:44
 * @version 1.0.0
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;
import com.cfinal.util.lang.CFDate;

/**
 * com.cfinal.db.mapper.cell.CFDateFormatCell.java
 * @author XChao
 */
public class CFDateFormatCell implements CFCell<String> {

	private String formatter;

	public CFDateFormatCell() {
	}

	public CFDateFormatCell(String formatter) {
		this.formatter = formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	@Override
	public String getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return CFDate.format(resultSet.getTimestamp(columnLabel), this.formatter);
	}

}
