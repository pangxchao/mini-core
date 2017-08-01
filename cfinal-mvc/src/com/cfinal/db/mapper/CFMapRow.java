/**
 * Created the com.cfinal.db.mapper.CFMapRow.java
 * @created 2016年10月9日 下午4:21:17
 * @version 1.0.0
 */
package com.cfinal.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;
import com.cfinal.db.CFDB;

/**
 * com.cfinal.db.mapper.CFMapRow.java
 * @author XChao
 */
public class CFMapRow extends CFAbstractRow<JSONObject> {

	@Override
	public JSONObject getRow(CFDB db, ResultSet resultSet) throws SQLException {
		JSONObject entrys = new JSONObject();
		for (String columnLable : this.cells.keySet()) {
			entrys.put(columnLable, this.cells.get(columnLable).getCell(db, resultSet, columnLable));
		}
		return entrys;
	}

}
