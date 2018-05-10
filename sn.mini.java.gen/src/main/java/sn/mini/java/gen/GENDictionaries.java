/**
 * Created the com.cfinal.gen.GENDictionaries.java
 * @created 2017年8月1日 上午9:16:54
 * @version 1.0.0
 */
package sn.mini.java.gen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.model.DaoTable;
import sn.mini.java.util.json.JSONArray;
import sn.mini.java.util.json.JSONObject;

/**
 * com.cfinal.gen.GENDictionaries.java
 * 
 * @author XChao
 */
public class GENDictionaries {

	public static void main(String[] args) throws Exception {
		File file = new File(GENConfig.PROJECT_PATH, "document");
		if (!file.exists() && file.mkdirs()) {
			System.out.println("创建文件夹成功");
		}

		try (OutputStream out = new FileOutputStream(new File(file, "dictionary.xls"))) {
			// 创建工作簿
			HSSFWorkbook workBook = new HSSFWorkbook();
			// 创建工作表 工作表的名字叫helloWorld
			HSSFSheet sheet = workBook.createSheet("helloWorld");
			HSSFFont font = workBook.createFont();
			font.setFontName(HSSFFont.FONT_ARIAL);
			font.setFontHeightInPoints((short) 12);

			HSSFCellStyle style = workBook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
			style.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 垂直居中
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
			style.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
			style.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
			style.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);

			// style.setBottomBorderColor(Color.GRAY.getRGB());
			style.setWrapText(false);
			style.setFont(font);

			
			// 设置列宽
			for (int i = 0; i < 9; i++) {
				sheet.setColumnWidth(i, 10000);
			}
			sheet.setColumnWidth(1, 30000); // 设置第二列宽， 字段名称列
			sheet.setColumnWidth(2, 20000); // 设置第二列宽， 字段名称列
			sheet.setColumnWidth(4, 45000); // 设置第五列宽， 字段说明列

			HSSFRow row;
			HSSFCell cell;
			try (IDao dao = GENConfig.getDao();) {
				// JSONArray arrays = CFDBTables.getCreateTable(db, tableName)
				List<String> tables = dao.query(rs -> {
					return rs.getString(1);
				}, "show tables");
				for (int i = 0, rowNum = 0, len = tables.size(); i < len; i++) {
					// 该段是设置整个每张表的名称行
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8)); // 合并单元格
					row = sheet.createRow(rowNum);// 创建表格行
					row.setHeightInPoints(15);// 设置行高 405
					cell = row.createCell(0);
					cell.setCellValue(tables.get(i));
					cell.setCellStyle(style);

					// 该段是设置每张表的每个列说明行
					rowNum = rowNum + 1;
					row = sheet.createRow(rowNum);// 创建表格行
					row.setHeightInPoints(15);// 设置行高 405
					String[] cellTitle = new String[] { "序号", "名称", "类型", "长度", "说明", "主键", "为空", "自增", "默认值" };
					for (int j = 0, l = cellTitle.length; j < l; j++) {
						cell = row.createCell(j);
						cell.setCellValue(cellTitle[j]);
						cell.setCellStyle(style);
					}

					// 该段是设置主
					JSONArray pks = DaoTable.getPrimaryKey(dao, tables.get(i));
					Map<String, Boolean> pkMaps = new HashMap<>();
					for (int j = 0, size = pks.size(); j < size; j++) {
						JSONObject columnItem = pks.getJSONObject(j);
						pkMaps.put(columnItem.getString("COLUMN_NAME"), true);
					}

					JSONArray columns = DaoTable.getColumns(dao, tables.get(i));
					for (int j = 0, size = columns.size(); j < size; j++) {
						JSONObject columnItem = columns.getJSONObject(j);
						rowNum = rowNum + 1;
						row = sheet.createRow(rowNum);// 创建表格行
						row.setHeightInPoints(15);// 设置行高 405
						cell = row.createCell(0);
						cell.setCellValue(String.valueOf(j + 1));
						cell.setCellStyle(style);

						String[] cellValue = new String[] { "COLUMN_NAME", "TYPE_NAME", "COLUMN_SIZE", "REMARKS",
								"COLUMN_NAME", "IS_NULLABLE", "IS_AUTOINCREMENT", "COLUMN_DEF" };
						for (int k = 0, lv = cellValue.length; k < lv; k++) {
							cell = row.createCell((k + 1));
							if ("COLUMN_NAME".equals(cellValue[k])) {
								Boolean isPK = pkMaps.get(columnItem.getString("COLUMN_NAME"));
								cell.setCellValue(isPK != null && isPK == true ? "YES" : "NO");
							} else {
								cell.setCellValue(columnItem.getString(cellValue[k]));
							}
							cell.setCellStyle(style);
						}
					}
					rowNum = rowNum + 1;
					row = sheet.createRow(rowNum);// 创建表格行
					row.setHeightInPoints(15);// 设置行高 405
				}
			}
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
