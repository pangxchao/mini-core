/**
 * Created the com.cfinal.gen.GENDictionaries.java
 * @created 2017年8月1日 上午9:16:54
 * @version 1.0.0
 */
package com.cfinal.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cfinal.db.CFDB;
import com.cfinal.db.CFDBFactory;
import com.cfinal.db.model.mapping.CFDBTables;
import com.cfinal.util.logger.CFLogger;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * com.cfinal.gen.GENDictionaries.java
 * @author XChao
 */
public class GENDictionaries {

	public static void main(String[] args) throws Exception {
		OutputStream outputStream = null;
		WritableWorkbook book = null;
		try {
			File file = new File(GENConfig.PROJECT_PATH, "documents/dictionary.xls");
			if(!file.exists() && file.mkdirs()) {
				System.out.println("创建文件夹成功");
			}
			outputStream = new FileOutputStream(file);
			book = Workbook.createWorkbook(outputStream);

			WritableSheet sheet = book.createSheet("Sheet1", 0);
//			FontName fontName = WritableFont.createFont("微软雅黑");
			WritableFont font = new WritableFont(WritableFont.ARIAL, 12);
			WritableCellFormat cellformat = new WritableCellFormat(font);
			// cellformat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			cellformat.setBorder(Border.ALL, BorderLineStyle.THIN);// 设置线条
			cellformat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直居中
			cellformat.setAlignment(Alignment.LEFT); // 文字水平对齐方式 左对齐
			cellformat.setWrap(true); // 文字是否换行

			WritableFont font1 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
			WritableCellFormat cellformat1 = new WritableCellFormat(font1);
			cellformat1.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellformat1.setBackground(Colour.GRAY_25);
			cellformat1.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直居中
			cellformat1.setAlignment(Alignment.CENTRE); // 文字水平对齐方式 左对齐
			cellformat1.setWrap(false); // 文字是否换行

			// 设置列宽
			for (int i = 0; i < 9; i++) {
				sheet.setColumnView(i, 10); // 设置列的宽度
			}
			sheet.setColumnView(1, 30); // 设置第二列宽， 字段名称列
			sheet.setColumnView(2, 20); // 设置第二列宽， 字段名称列
			sheet.setColumnView(4, 45); // 设置第五列宽， 字段说明列

			CFDB db = null;
			try {
				db = GENConfig.getDB();
				// JSONArray arrays = CFDBTables.getCreateTable(db, tableName)
				List<String> tables = db.query((db1, rs) -> {
					return rs.getString(1);
				}, "show tables");
				for (int i = 0, row = 0, len = tables.size(); i < len; i++) {
					sheet.mergeCells(0, row, 8, row); // 合并单元格
					sheet.setRowView(row, 405, false); // 设置行高

					sheet.addCell(new Label(0, row, tables.get(i), cellformat1));
					row = row + 1;

					sheet.setRowView(row, 405, false); // 设置行高
					sheet.addCell(new Label(0, row, "序号", cellformat));
					sheet.addCell(new Label(1, row, "名称", cellformat));
					sheet.addCell(new Label(2, row, "类型", cellformat));
					sheet.addCell(new Label(3, row, "长度", cellformat));
					sheet.addCell(new Label(4, row, "说明", cellformat));
					sheet.addCell(new Label(5, row, "主键", cellformat));
					sheet.addCell(new Label(6, row, "为空", cellformat));
					sheet.addCell(new Label(7, row, "自增", cellformat));
					sheet.addCell(new Label(8, row, "默认值", cellformat));
					row = row + 1;

					JSONArray pks = CFDBTables.getPrimaryKey(db, tables.get(i));
					Map<String, Boolean> pkMaps = new HashMap<>();
					for (int j = 0, size = pks.size(); j < size; j++) {
						JSONObject columnItem = pks.getJSONObject(j);
						pkMaps.put(columnItem.getString("COLUMN_NAME"), true);
					}

					JSONArray columns = CFDBTables.getColumns(db, tables.get(i));
					for (int j = 0, size = columns.size(); j < size; j++) {
						JSONObject columnItem = columns.getJSONObject(j);
						sheet.setRowView(row, 405, false); // 设置行高
						sheet.addCell(new Label(0, row, String.valueOf(j + 1), cellformat));
						sheet.addCell(new Label(1, row, columnItem.getString("COLUMN_NAME"), cellformat)); // 名称
						sheet.addCell(new Label(2, row, columnItem.getString("TYPE_NAME"), cellformat)); // 类型
						sheet.addCell(new Label(3, row, columnItem.getString("COLUMN_SIZE"), cellformat)); // 长度
						sheet.addCell(new Label(4, row, columnItem.getString("REMARKS"), cellformat)); // 说明
						Boolean isPK = pkMaps.get(columnItem.getString("COLUMN_NAME"));
						String _isPK = isPK != null && isPK == true ? "YES" : "NO";
						sheet.addCell(new Label(5, row, _isPK, cellformat)); // 是否为主键
						sheet.addCell(new Label(6, row, columnItem.getString("IS_NULLABLE"), cellformat)); // 是否可以为空
						sheet.addCell(new Label(7, row, columnItem.getString("IS_AUTOINCREMENT"), cellformat)); // 是否自增长
						sheet.addCell(new Label(8, row, columnItem.getString("COLUMN_DEF"), cellformat)); // 默认值
						row = row + 1;
					}
					sheet.setRowView(row, 405, false); // 设置行高
					row = row + 1;
				}
			} finally {
				CFDBFactory.close(db);
			}
			book.write();
			outputStream.flush();

			System.out.println("---------------- 数据字典生成完成 ------------------");
		} finally {
			if(book != null) {
				try {
					book.close();
				} catch (Exception e) {
					CFLogger.severe("book close exception", e);
				}
			}
			if(null != outputStream) {
				outputStream.close();
			}
		}
	}
}
