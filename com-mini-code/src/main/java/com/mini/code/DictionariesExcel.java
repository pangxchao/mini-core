package com.mini.code;

import com.mini.dao.IDao;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * DictionariesExcel.java
 * @author XChao
 */
public class DictionariesExcel {

    public static void main(String[] args) {
        File file = new File(Config.DOCUMENT_PATH);
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
            // 水平居中
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 垂直居中
            style.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
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
                sheet.setColumnWidth(i, 3000);
            }
            // 设置第二列宽， 字段名称列
            sheet.setColumnWidth(1, 6000);
            // 设置第二列宽， 字段名称列
            sheet.setColumnWidth(2, 4000);
            // 设置第五列宽， 字段说明列
            sheet.setColumnWidth(4, 9000);

            HSSFRow row;
            HSSFCell cell;
            String[] cellTitle = new String[]{"序号", "名称", "类型", "长度", "说明", "主键", "为空", "自增", "默认值"};
            String[] cellValue = new String[]{"COLUMN_NAME", "TYPE_NAME", "COLUMN_SIZE", "REMARKS", "COLUMN_NAME", "IS_NULLABLE", "IS_AUTOINCREMENT",
                    "COLUMN_DEF"};
            try (IDao dao = Config.getDao()) {
                List<String> tables = dao.query("show tables", (rs, number) -> rs.getString(1));
                for (int i = 0, rowNum = 0, len = tables.size(); i < len; i++) {
                    // 合并单元格
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
                    // 创建表格行
                    row = sheet.createRow(rowNum);
                    // 设置行高 405
                    row.setHeightInPoints(15);

                    // 表名称标题栏
                    cell = row.createCell(0);
                    cell.setCellValue(tables.get(i));
                    cell.setCellStyle(style);

                    rowNum = rowNum + 1;
                    row    = sheet.createRow(rowNum);
                    row.setHeightInPoints(15);

                    for (int j = 0, l = cellTitle.length; j < l; j++) {
                        cell = row.createCell(j);
                        cell.setCellValue(cellTitle[j]);
                        cell.setCellStyle(style);
                    }

                    //// 该段是设置主
                    //JSONArray pks = DaoTable.getPrimaryKey(dao, tables.get(i));
                    //Map<String, Boolean> pkMaps = getPrimaryKey(pks);
                    //
                    //JSONArray columns = DaoTable.getColumns(dao, tables.get(i));
                    //for (int j = 0, size = columns.size(); j < size; j++) {
                    //    JSONObject columnItem = columns.getJSONObject(j);
                    //    rowNum = rowNum + 1;
                    //    row    = sheet.createRow(rowNum);
                    //    row.setHeightInPoints(15);
                    //    cell = row.createCell(0);
                    //    cell.setCellValue(String.valueOf(j + 1));
                    //    cell.setCellStyle(style);
                    //
                    //
                    //    for (int k = 0, lv = cellValue.length; k < lv; k++) {
                    //        cell = row.createCell((k + 1));
                    //        if ("COLUMN_NAME".equals(cellValue[k])) {
                    //            Boolean isPK = pkMaps.get(columnItem.getString("COLUMN_NAME"));
                    //            cell.setCellValue(isPK != null && isPK ? "YES" : "NO");
                    //        } else {
                    //            cell.setCellValue(columnItem.getString(cellValue[k]));
                    //        }
                    //        cell.setCellStyle(style);
                    //    }
                    //}
                    // 每个表结束之后有一个空白行
                    rowNum = rowNum + 1;
                    row    = sheet.createRow(rowNum);
                    row.setHeightInPoints(15);
                }
            }
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private static Map<String, Boolean> getPrimaryKey(JSONArray pks) {
    //    Map<String, Boolean> pkMaps = new HashMap<>();
    //    for (int j = 0, size = pks.size(); j < size; j++) {
    //        JSONObject columnItem = pks.getJSONObject(j);
    //        pkMaps.put(columnItem.getString("COLUMN_NAME"), true);
    //    }
    //    return pkMaps;
    //}
}
