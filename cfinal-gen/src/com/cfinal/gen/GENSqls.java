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

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfinal.db.CFDB;
import com.cfinal.db.CFDBFactory;
import com.cfinal.db.model.mapping.CFDBTables;

/**
 * com.cfinal.gen.GENSqls.java
 * @author XChao
 */
public class GENSqls {

	public static void main(String[] args) throws Exception {
		CFDB db = null;
		try {
			db = GENConfig.getDB();
			List<String> tables = db.query((db1, rs) -> {
				return rs.getString(1);
			}, "show tables");
			OutputStreamWriter writer = null;
			OutputStream outputStream = null;
			try {
				File file = new File(GENConfig.PROJECT_PATH, "documents");
				if(!file.exists() && file.mkdirs()) {
					System.out.println("创建文件夹成功");
				}
				outputStream = new FileOutputStream(new File(file, "sqls.sql"));
				writer = new OutputStreamWriter(outputStream);
				for (String tableName : tables) {
					writer.write("drop table if exists " + tableName + ";\r\n");
					writer.write(CFDBTables.getCreateTable(db, tableName));
					writer.write(";\r\n\r\n");
				}

				for (String tableName : tables) {
					JSONArray columns = CFDBTables.getColumns(db, tableName);
					List<String> allDbName = new ArrayList<>();
					for (int i = 0, len = columns.size(); i < len; i++) {
						JSONObject columnItem = columns.getJSONObject(i);
						allDbName.add(columnItem.getString("COLUMN_NAME"));
					}
					JSONArray datas = db.query(GENConfig.PAGING, "select * from " + tableName);
					StringBuilder sql = new StringBuilder("insert into ").append(tableName);
					sql.append("(").append(StringUtils.join(allDbName, ", ")).append(")\r\n values");
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
							value = StringUtils.join(lineVal, "\\r\\n");
							values.add("'" + value + "'");
						}
						allValues.add("(" + StringUtils.join(values, ", ") + ")");
					}
					if(allValues.size() > 0) {
						sql.append(StringUtils.join(allValues, ", \r\n"));
						writer.write(sql.toString());
						writer.write(";");
						writer.write("\r\n");
						writer.write("\r\n");
					}
				}

				writer.flush(); // 刷新buffer
				System.out.println("---------------- 键表语句生成完成 ------------------");
			} finally {
				if(writer != null) {
					writer.close();
				}
				if(outputStream != null) {
					outputStream.close();
				}
			}

		} finally {
			CFDBFactory.close(db);
		}
	}
}
