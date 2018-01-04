/**
 * Created the com.cfinal.gen.GENCode.java
 * @created 2017年7月26日 下午3:31:04
 * @version 1.0.0
 */
package com.cfinal.gen;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import sn.mini.dao.IDao;
import sn.mini.dao.model.DaoTable;
import sn.mini.util.json.JSONArray;
import sn.mini.util.json.JSONObject;

/**
 * com.cfinal.gen.GENCode.java
 * @author XChao
 */
public class GENBean extends GENAll {
	public static void main(String[] args) throws SQLException, Exception {
		try (IDao dao = GENConfig.getDao();) {
			JSONArray pks = DaoTable.getPrimaryKey(dao, GENConfig.TABLE_DB_NAME);
			Map<String, Boolean> pkMaps = new HashMap<>();
			for (int i = 0, len = pks.size(); i < len; i++) {
				JSONObject columnItem = pks.getJSONObject(i);
				pkMaps.put(columnItem.getString("COLUMN_NAME"), true);
			}
			// 查询当前表的所有字段
			GENAll.genBean(DaoTable.getColumns(dao, GENConfig.TABLE_DB_NAME), pkMaps);
		}
	}

}
