/**
 * Created the com.cfinal.gen.GENSqls.java
 * @created 2017年8月1日 上午9:17:08
 * @version 1.0.0
 */
package com.cfinal.gen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import sn.mini.dao.IDao;
import sn.mini.dao.model.DaoTable;
import sn.mini.util.json.JSONArray;
import sn.mini.util.json.JSONObject;
import sn.mini.util.lang.StringUtil;

/**
 * com.cfinal.gen.GENSqls.java
 * @author XChao
 */
public class GENSqls {

	public static void main(String[] args) throws Exception {
		try (IDao db = GENConfig.getDao();) {
			List<String> tables = db.query(rs -> {
				return rs.getString(1);
			}, "show tables");
			OutputStreamWriter writer = null;
			OutputStream outputStream = null;
			try {
				File file = new File(GENConfig.PROJECT_PATH, "document");
				if(!file.exists() && file.mkdirs()) {
					System.out.println("创建文件夹成功");
				}
				outputStream = new FileOutputStream(new File(file, "sqls.sql"));
				writer = new OutputStreamWriter(outputStream);
				for (String tableName : tables) {
					writer.write("drop table if exists " + tableName + ";\r\n");
					writer.write(DaoTable.getCreateTable(db, tableName));
					writer.write(";\r\n\r\n");
				}

				for (String tableName : tables) {
					JSONArray columns = DaoTable.getColumns(db, tableName);
					List<String> allDbName = new ArrayList<>();
					for (int i = 0, len = columns.size(); i < len; i++) {
						JSONObject columnItem = columns.getJSONObject(i);
						allDbName.add(columnItem.getString("COLUMN_NAME"));
					}
					JSONArray datas = db.query(GENConfig.PAGING, "select * from " + tableName);
					StringBuilder sql = new StringBuilder("insert into ").append(tableName);
					sql.append("(").append(StringUtil.join(allDbName, ", ")).append(")\r\n values");
					List<String> allValues = new ArrayList<>();
					for (int j = 0, size = datas.size(); j < size; j++) {
						JSONObject data = datas.getJSONObject(j);
						List<String> values = new ArrayList<>();
						for (String columnName : allDbName) {
							if(data.get(columnName) == null) {
								values.add("null");
								continue;
							}
							Reader reader = new StringReader(data.getString(columnName));
							BufferedReader buffer = new BufferedReader(reader);
							String line = null, value = null;
							List<String> lineVal = new ArrayList<>();
							while ((line = buffer.readLine()) != null) {
								lineVal.add(line);
							}
							value = StringUtil.join(lineVal, "\\r\\n");
							values.add("'" + value + "'");
						}
						allValues.add("(" + StringUtil.join(values, ", ") + ")");
					}
					if(allValues.size() > 0) {
						sql.append(StringUtil.join(allValues, ", \r\n"));
						writer.write(sql.toString());
						writer.write(";");
						writer.write("\r\n");
						writer.write("\r\n");
					}
				}

				
				System.out.println("---------------- 键表语句生成完成 ------------------");
			} finally {
				if(writer != null) {
					writer.close();
				}
				if(outputStream != null) {
					outputStream.close();
				}
			}
		}
	}
}
