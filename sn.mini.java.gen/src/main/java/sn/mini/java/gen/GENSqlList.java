/**
 * Created the com.cfinal.gen.GENSqls.java
 * @created 2017年8月1日 上午9:17:08
 * @version 1.0.0
 */
package sn.mini.java.gen;

import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.model.DaoTable;
import sn.mini.java.util.json.JSONArray;
import sn.mini.java.util.json.JSONObject;
import sn.mini.java.util.lang.StringUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * com.cfinal.gen.GENSqls.java
 *
 * @author XChao
 */
public class GENSqlList {

    public static void main(String[] args) throws Exception {
        try (IDao dao = GENConfig.getDao()) {
            File file = new File(GENConfig.PROJECT_PATH, "document");
            if (!file.exists() && file.mkdirs()) System.out.println("创建文件夹成功");
            List<String> tableList = dao.query(rs -> rs.getString(1), "show tables");
            try (OutputStream outputStream = new FileOutputStream(new File(file, "sqlList.sql"));
                 OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
                writer.write("create database if not exists " + dao.getCatalog() + "; \r\n\r\n");

                for (String tableName : tableList) {
                    writer.write("drop table if exists " + tableName + ";\r\n");
                    writer.write(DaoTable.getCreateTable(dao, tableName));
                    writer.write(";\r\n\r\n");
                }

                for (String tableName : tableList) {
                    JSONArray columns = DaoTable.getColumns(dao, tableName);
                    List<String> allDbName = new ArrayList<>();
                    for (int i = 0, len = columns.size(); i < len; i++) {
                        JSONObject columnItem = columns.getJSONObject(i);
                        allDbName.add(columnItem.getString("COLUMN_NAME"));
                    }
                    JSONArray dataArray = dao.query(GENConfig.PAGING, "select * from " + tableName);
                    StringBuilder sql = new StringBuilder("insert into ").append(tableName);
                    sql.append("(").append(StringUtil.join(allDbName, ", ")).append(")\r\n values");
                    List<String> allValues = new ArrayList<>();
                    for (int j = 0, size = dataArray.size(); j < size; j++) {
                        JSONObject data = dataArray.getJSONObject(j);
                        List<String> values = new ArrayList<>();
                        for (String columnName : allDbName) {
                            if (data.get(columnName) == null) {
                                values.add("null");
                                continue;
                            }
                            Reader reader = new StringReader(data.getString(columnName));
                            BufferedReader buffer = new BufferedReader(reader);
                            String line, value;
                            List<String> lineVal = new ArrayList<>();
                            while ((line = buffer.readLine()) != null) {
                                lineVal.add(line);
                            }
                            value = StringUtil.join(lineVal, "\\r\\n");
                            values.add("'" + value + "'");
                        }
                        allValues.add("(" + StringUtil.join(values, ", ") + ")");
                    }
                    if (allValues.size() > 0) {
                        sql.append(StringUtil.join(allValues, ", \r\n"));
                        writer.write(sql.toString());
                        writer.write(";");
                        writer.write("\r\n");
                        writer.write("\r\n");
                    }
                }


                System.out.println("---------------- 键表语句生成完成 ------------------");
            }
        }
    }
}
