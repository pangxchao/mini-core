package com.mini.plugin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DasDataSource;
import com.intellij.database.model.DasForeignKey;
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
import java.util.*;

import static com.intellij.openapi.ui.Messages.showWarningDialog;
import static com.intellij.openapi.util.io.FileUtil.loadFile;
import static com.intellij.openapi.util.io.FileUtil.writeToFile;
import static com.intellij.openapi.vfs.CharsetToolkit.UTF8_CHARSET;
import static com.mini.plugin.util.Constants.SAVE_FAILED_TABLE;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.StringUtil.toJavaName;
import static java.util.Optional.of;
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
		TableInfo tableInfo = ofNullable(readTableInfo(project, table)).orElseGet(() -> {
			final TableInfo info = new TableInfo();
			info.setEntityName(toJavaName(table.getName(), true));
			info.setComment(table.getComment());
			return info;
		});
		// 默认所有字段都为扩展字段
		tableInfo.getColumnList().forEach(column -> {
			column.setExt(true); //
		});
		tableInfo.setTableName(table.getName());
		tableInfo.setTable(table);
		
		// 获取所有的外键信息
		Map<String, Ref> foreignKey = new HashMap<>();
		for (DasForeignKey key : DasUtil.getForeignKeys(table)) {
			Iterator<String> target = key.getRefColumns().iterate();
			Iterator<String> source = key.getColumnsRef().iterate();
			while (target != null && source != null && target.hasNext()
				&& source.hasNext()) {
				Ref ref = new Ref(source.next(), target.next(),
					key.getRefTableName());
				foreignKey.put(ref.name, ref);
			}
		}
		// 获取所有字段
		for (DasColumn column : DasUtil.getColumns(table)) {
			ColumnInfo columnInfo = of(tableInfo.getColumns())
				.map(columns -> columns.get(column.getName()))
				.orElseGet(() -> {
					final ColumnInfo info = new ColumnInfo();
					info.setComment(column.getComment());
					return info;
				});
			columnInfo.setDbType(column.getDataType().typeName.toUpperCase());
			columnInfo.setColumnName(column.getName());
			columnInfo.setId(DasUtil.isPrimary(column));
			columnInfo.setAuto(DasUtil.isAuto(column));
			columnInfo.setNotNull(column.isNotNull());
			// java 属性名称处理
			if (StringUtil.isEmpty(columnInfo.getFieldName())) {
				columnInfo.setFieldName(toJavaName(column.getName(), false));
			}
			// 处理字段说明
			if (StringUtil.isEmpty(columnInfo.getComment())) {
				columnInfo.setComment(column.getComment());
			}
			// 处理外键关联
			ofNullable(foreignKey.get(column.getName()))
				.ifPresent(ref -> {
					columnInfo.setRef(true);
					columnInfo.setRefTable(ref.refTable);
					columnInfo.setRefColumn(ref.refName);
				});
			columnInfo.setExt(false);
			// 添加字段到表信息中
			tableInfo.addColumn(columnInfo);
		}
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
	
	private static final class Ref implements EventListener {
		private String name, refName, refTable;
		
		Ref(String name, String refName, String refTable) {
			this.refTable = refTable;
			this.refName  = refName;
			this.name     = name;
		}
	}
}
