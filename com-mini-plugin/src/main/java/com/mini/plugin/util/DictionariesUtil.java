package com.mini.plugin.util;

import com.intellij.database.model.DasTypedObject;
import com.intellij.database.model.DataType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import com.mini.plugin.config.ColumnInfo;
import com.mini.plugin.config.TableInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class DictionariesUtil {
	
	public static void generator(List<TableInfo> infoList, VirtualFile file) {
		File outFile = new File(file.getPath(), "dictionary.xls");
		try (OutputStream out = new FileOutputStream(outFile)) {
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
			style.setTopBorderColor((short) JBColor.GRAY.getRGB());
			style.setRightBorderColor((short) JBColor.GRAY.getRGB());
			style.setBottomBorderColor((short) JBColor.GRAY.getRGB());
			style.setLeftBorderColor((short) JBColor.GRAY.getRGB());
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
			
			
			HSSFRow row;
			HSSFCell cell;
			String[] cellTitle = new String[]{"序号", "名称", "类型", "长度", "主键", "非空", "自增", "默认值", "说明"};
			
			int rowNum = 0;
			for (TableInfo info : infoList) {
				// 合并单元格
				sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 8));
				// 创建表格行
				row = sheet.createRow(rowNum);
				// 设置行高 405
				row.setHeightInPoints(20);
				
				// 表名称标题栏
				cell = row.createCell(0);
				cell.setCellValue(info.getTableName());
				cell.setCellStyle(style_center);
				for (int j = 1; j <= 8; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(style_center);
				}
				
				rowNum = rowNum + 1;
				row    = sheet.createRow(rowNum);
				row.setHeightInPoints(20);
				
				// 标题行
				for (int j = 0, l = cellTitle.length; j < l; j++) {
					cell = row.createCell(j);
					cell.setCellValue(cellTitle[j]);
					cell.setCellStyle(style_center);
				}
				
				int index = 1;
				for (ColumnInfo column : info.getColumnList()) {
					rowNum = rowNum + 1;
					row    = sheet.createRow(rowNum);
					row.setHeightInPoints(15);
					
					// 序号
					cell = row.createCell(0);
					cell.setCellValue(index);
					cell.setCellStyle(style);
					
					// 字段名称
					cell = row.createCell(1);
					cell.setCellStyle(style);
					cell.setCellValue(column.getColumnName());
					
					// 字段类型
					cell = row.createCell(2);
					cell.setCellStyle(style);
					cell.setCellValue(column.getDbType());
					
					// 字段长度
					cell = row.createCell(3);
					cell.setCellStyle(style);
					cell.setCellValue(Optional
						.ofNullable(column.getColumn())
						.map(DasTypedObject::getDataType)
						.map(DataType::getLength)
						.orElse(0));
					
					// 是否为主键
					cell = row.createCell(4);
					cell.setCellStyle(style);
					cell.setCellValue(column.isId() ? "是" : "");
					
					// 字段是否为非空字段
					cell = row.createCell(5);
					cell.setCellStyle(style);
					cell.setCellValue(column.isNullable() ? "" : "是");
					
					// 字段是否为自增字段
					cell = row.createCell(6);
					cell.setCellStyle(style);
					cell.setCellValue(column.isAuto() ? "是" : "");
					
					// 字段默认值
					cell = row.createCell(7);
					cell.setCellStyle(style);
					cell.setCellValue(Optional
						.ofNullable(column.getColumn())
						.map(DasTypedObject::getDefault)
						.orElse(""));
					
					// 字段说明
					cell = row.createCell(8);
					cell.setCellStyle(style);
					cell.setCellValue(column.getComment());
					
					index++;
				}
				
				// 每个表结束之后有一个空白行
				rowNum = rowNum + 1;
				row    = sheet.createRow(rowNum);
				row.setHeightInPoints(20);
				
				// 准备下一个表格
				rowNum = rowNum + 1;
				row    = sheet.createRow(rowNum);
				row.setHeightInPoints(20);
				
				rowNum++;
			}
			
			workBook.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
