package com.mini.plugin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DasDataSource;
import com.intellij.database.model.RawConnectionConfig;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.project.Project;
import com.mini.plugin.config.ColumnInfo;
import com.mini.plugin.config.TableInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.EventListener;
import java.util.Optional;

import static com.intellij.openapi.ui.Messages.showWarningDialog;
import static com.intellij.openapi.util.io.FileUtil.loadFile;
import static com.intellij.openapi.util.io.FileUtil.writeToFile;
import static com.intellij.openapi.vfs.CharsetToolkit.UTF8_CHARSET;
import static com.mini.plugin.util.Constants.SAVE_FAILED_TABLE;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.StringUtil.toJavaName;
import static java.util.Optional.ofNullable;

public final class TableUtil implements EventListener, Serializable {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final String SAVE_PATH = "/.idea/Mini-Code";
	
	//public static Map<String, Object> toData(TableInfo tableInfo) {
	//	Map<String, Object> data = new java.util.HashMap<>();
	//	data.put("columns", new ArrayList<>(tableInfo.getColumns().values()));
	//	data.put("packageName", tableInfo.getPackageName());
	//	data.put("className", tableInfo.getEntityName());
	//	data.put("tableName", tableInfo.getTableName());
	//	data.put("comment", tableInfo.getComment());
	//	//data.put("tool", ToolUtil.class);
	//	return data;
	//}
	
	@NotNull
	public static TableInfo createTableInfo(Project project, DbTable table) {
		final TableInfo tableInfo = new TableInfo();
		tableInfo.setTableName(table.getName());
		tableInfo.setTable(table);
		// 获取所有字段
		for (DasColumn column : DasUtil.getColumns(table)) {
			final ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setDbType(column.getDataType().typeName.toUpperCase());
			columnInfo.setFieldName(toJavaName(column.getName(), false));
			columnInfo.setColumnName(column.getName());
			columnInfo.setId(DasUtil.isPrimary(column));
			columnInfo.setComment(column.getComment());
			columnInfo.setAuto(DasUtil.isAuto(column));
			columnInfo.setNotNull(column.isNotNull());
			tableInfo.addColumn(columnInfo);
		}
		// 读取本地配置
		ofNullable(readTableInfo(project, table)).ifPresent(info -> {
			if (StringUtil.isNotEmpty(info.getEntityName())) {
				tableInfo.setEntityName(info.getEntityName());
			}
			if (StringUtil.isNotEmpty(info.getNamePrefix())) {
				tableInfo.setNamePrefix(info.getNamePrefix());
			}
			info.getColumnList().forEach(column -> { //
				ofNullable(tableInfo.getColumns().get(column.getColumnName())).ifPresent(c -> {
					if (StringUtil.isNotEmpty(column.getFieldName())) {
						c.setFieldName(column.getFieldName());
					}
					c.setDelValue(column.getDelValue());
					c.setCreateAt(column.isCreateAt());
					c.setUpdateAt(column.isCreateAt());
					c.setLock(column.isLock());
					c.setDel(column.isDel());
				});
			});
		});
		return tableInfo;
	}
	
	@Nullable
	public static TableInfo readTableInfo(Project project, DbTable table) {
		try {
			File file = getAbsolutePath(project, table);
			String content = loadFile(file, UTF8_CHARSET);
			return mapper.readValue(content.getBytes( //
					UTF8_CHARSET), TableInfo.class);
		} catch (Throwable e) {
			return null;
		}
	}
	
	public static void saveTableInfo(Project project, TableInfo tableInfo) {
		try {
			File file = getAbsolutePath(project, tableInfo.getTable());
			String content = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(tableInfo);
			writeToFile(file, content.getBytes(UTF8_CHARSET));
		} catch (IOException | RuntimeException e) {
			showWarningDialog(SAVE_FAILED_TABLE, //
					TITLE_INFO);
		}
	}
	
	private static File getAbsolutePath(Project project, DbTable table) {
		File file = new File(project.getBasePath(), SAVE_PATH);
		// 连接目录地址
		file = new File(file, Optional.of(table.getDataSource())
				.map(DasDataSource::getConnectionConfig)
				.map(RawConnectionConfig::getUrl)
				.map(StringUtil::toURI)
				.map(URI::getHost)
				.orElse("0.0.0.0"));
		// 创建文件夹
		if (!file.exists() && !file.mkdirs()) {
			throw new RuntimeException();
		}
		// 返回文件信息
		return new File(file, DasUtil.getSchema(table) //
				+ "." + table.getName() + ".json");
	}
}
