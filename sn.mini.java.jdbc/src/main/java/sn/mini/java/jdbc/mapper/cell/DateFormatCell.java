/**
 * Created the sn.mini.jdbc.mapper.cell.DateFormatCell.java
 * @created 2016年10月9日 下午5:47:44
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import sn.mini.java.util.lang.DateUtil;

/**
 * sn.mini.jdbc.mapper.cell.DateFormatCell.java
 * @author XChao
 */
public class DateFormatCell implements ICell<String> {

	private String formatter;

	public DateFormatCell() {
	}

	public DateFormatCell(String formatter) {
		this.formatter = formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	@Override
	public String getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return DateUtil.format(resultSet.getTimestamp(columnLabel), this.formatter);
	}

}
