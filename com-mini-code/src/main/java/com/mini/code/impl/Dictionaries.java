package com.mini.code.impl;

import com.mini.callback.DatabaseMetaDataCallback;
import com.mini.code.Configure;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.mini.util.StringUtil.eq;

/**
 * DictionariesExcel.java
 * @author XChao
 */
public class Dictionaries {

    public static void run(Configure configure) {
        File file = new File(configure.getDocumentPath());
        if (!file.exists() && file.mkdirs()) {
            System.out.println("创建文件夹成功");
        }

        try (OutputStream out = new FileOutputStream(new File(file, "dictionary.xls"))) {
            // 创建工作簿
            HSSFWorkbook workBook = new HSSFWorkbook();
            // 创建工作表 工作表的名字叫helloWorld
            HSSFSheet sheet = workBook.createSheet("Sheet1");
            HSSFFont font = workBook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 12);

            HSSFCellStyle style = workBook.createCellStyle();
            // 水平居中
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            // 垂直居中
            style.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setTopBorderColor((short) Color.GRAY.getRGB());
            style.setRightBorderColor((short) Color.GRAY.getRGB());
            style.setBottomBorderColor((short) Color.GRAY.getRGB());
            style.setLeftBorderColor((short) Color.GRAY.getRGB());
            style.setWrapText(false);
            style.setFont(font);

            HSSFCellStyle style_center = workBook.createCellStyle();
            style_center.cloneStyleFrom(style);
            style_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style_center.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style_center.setFillPattern(CellStyle.SOLID_FOREGROUND);

            // 设置列宽
            for (int i = 0; i < 9; i++) {
                sheet.setColumnWidth(i, 3000);
            }
            // 设置第二列宽， 字段名称列
            sheet.setColumnWidth(1, 6000);
            // 设置第二列宽， 字段类型列
            sheet.setColumnWidth(2, 4000);
            // 设置第八列宽， 字段说明列
            sheet.setColumnWidth(8, 18000);

            configure.getJdbcTemplate().execute((DatabaseMetaDataCallback<Object>) metaData -> {
                HSSFRow row;
                HSSFCell cell;
                String[] cellTitle = new String[]{"序号", "名称", "类型", "长度", "主键", "非空", "自增", "默认值", "说明"};
                List<String> tables = configure.getJdbcTemplate().query("show tables", (rs, number) -> rs.getString(1));
                for (int i = 0, rowNum = 0; i < tables.size(); i++) {
                    String tableName = tables.get(i);
                    // 合并单元格
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
                    // 创建表格行
                    row = sheet.createRow(rowNum);
                    // 设置行高 405
                    row.setHeightInPoints(20);

                    // 表名称标题栏
                    cell = row.createCell(0);
                    cell.setCellValue(tableName);
                    cell.setCellStyle(style_center);
                    for (int j = 1; j <= 8; j++) {
                        cell = row.createCell(j);
                        cell.setCellStyle(style_center);
                    }

                    rowNum = rowNum + 1;
                    row    = sheet.createRow(rowNum);
                    row.setHeightInPoints(20);

                    for (int j = 0, l = cellTitle.length; j < l; j++) {
                        cell = row.createCell(j);
                        cell.setCellValue(cellTitle[j]);
                        cell.setCellStyle(style_center);
                    }

                    // 获取主键字段
                    List<String> pkFieldList = new ArrayList<>();
                    try (ResultSet rs = metaData.getPrimaryKeys(configure.getDatabaseName(), null, tableName)) {
                        while (rs != null && rs.next()) pkFieldList.add(rs.getString("COLUMN_NAME"));
                    }

                    try (ResultSet rs = metaData.getColumns(configure.getDatabaseName(), null, tableName, null)) {
                        for (int index = 1; rs != null && rs.next(); index++) {
                            rowNum = rowNum + 1;
                            row    = sheet.createRow(rowNum);
                            row.setHeightInPoints(15);

                            // 获取字段的信息
                            String name = rs.getString("COLUMN_NAME");
                            String typeName = rs.getString("TYPE_NAME");
                            int columnSize = rs.getInt("COLUMN_SIZE");
                            String remarks = rs.getString("REMARKS");
                            String isNull = rs.getString("IS_NULLABLE");
                            String isAuto = rs.getString("IS_AUTOINCREMENT");
                            String columnDef = rs.getString("COLUMN_DEF");

                            // 序号
                            cell = row.createCell(0);
                            cell.setCellValue(index);
                            cell.setCellStyle(style);

                            // 字段名称
                            cell = row.createCell(1);
                            cell.setCellStyle(style);
                            cell.setCellValue(name);

                            // 字段类型
                            cell = row.createCell(2);
                            cell.setCellStyle(style);
                            cell.setCellValue(typeName);

                            // 字段长度
                            cell = row.createCell(3);
                            cell.setCellStyle(style);
                            cell.setCellValue(columnSize);

                            // 是否为主键
                            cell = row.createCell(4);
                            cell.setCellStyle(style);
                            cell.setCellValue(pkFieldList.stream().anyMatch(text -> eq(text, name)) ? "是" : "");

                            // 字段是否为非空字段
                            cell = row.createCell(5);
                            cell.setCellStyle(style);
                            cell.setCellValue(eq("NO", isNull) ? "是" : "");

                            // 字段是否为自增字段
                            cell = row.createCell(6);
                            cell.setCellStyle(style);
                            cell.setCellValue(eq("YES", isAuto) ? "是" : "");

                            // 字段默认值
                            cell = row.createCell(7);
                            cell.setCellStyle(style);
                            cell.setCellValue(columnDef);

                            // 字段说明
                            cell = row.createCell(8);
                            cell.setCellStyle(style);
                            cell.setCellValue(remarks);
                        }
                    }

                    // 每个表结束之后有一个空白行
                    rowNum = rowNum + 1;
                    row    = sheet.createRow(rowNum);
                    row.setHeightInPoints(20);

                    // 准备下一个表格
                    rowNum = rowNum + 1;
                    row    = sheet.createRow(rowNum);
                    row.setHeightInPoints(20);
                }

                return null;
            });
            workBook.write(out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
