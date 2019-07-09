/**
 * Created the sn.mini.jdbc.mapper.row.MapRow.java
 * @created 2016年10月9日 下午4:21:17
 * @version 1.0.0
 */
package sn.mini.java.jdbc.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import sn.mini.java.util.json.JSONObject;

/**
 * sn.mini.jdbc.mapper.row.MapRow.java
 * @author XChao
 */
public final class MapRow extends AbstractRow<JSONObject> {

	@Override
	public JSONObject getRow(ResultSet resultSet) throws SQLException {
		JSONObject entrys = new JSONObject();
		for (String columnLable : this.cells.keySet()) {
			entrys.put(columnLable, this.cells.get(columnLable).getCell(resultSet, columnLable));
		}
		return entrys;
	}

}
