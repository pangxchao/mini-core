package com.mini.plugin.service;

import com.intellij.database.model.*;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.project.Project;
import com.mini.plugin.config.ColumnInfo;
import com.mini.plugin.config.TableInfo;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.util.*;

import static com.intellij.openapi.components.ServiceManager.getService;
import static com.mini.plugin.util.StringUtil.toJavaName;

public final class TableInfoService implements EventListener {
	private static final String SAVE_PATH = "/.idea/Mini-Code";
	
	public static TableInfoService getInstance(Project project) {
		return getService(project, TableInfoService.class);
	}
	
	@NotNull
	public TableInfo createTableInfo(Project project, DbTable table) {
		final TableInfo tableInfo = new TableInfo();
		tableInfo.setEntityName(toJavaName(table.getName(), true));
		tableInfo.setComment(table.getComment());
		tableInfo.setTableName(table.getName());
		tableInfo.setTable(table);
		
		class Ref {
			String name;
			String refName;
			String refTable;
		}
		
		Map<String, Ref> foreignKey = new HashMap<>();
		for (DasForeignKey key : DasUtil.getForeignKeys(table)) {
			System.out.println(key.getRefTable());
			Iterator<String> a = key.getRefColumns().iterate();
			Iterator<String> b = key.getColumnsRef().iterate();
			while (a != null && b != null && a.hasNext() && b.hasNext()) {
				String aa = a.next(), bb = b.next();
				Ref ref = new Ref();
				ref.refName  = aa;
				ref.name     = bb;
				ref.refTable = key.getRefTableName();
				foreignKey.put(bb, ref);
			}
		}
		System.out.println("---------------------------------------------------");
		// 获取所有字段
		for (DasColumn column : DasUtil.getColumns(table)) {
			ColumnInfo columnInfo = new ColumnInfo();
			//columnInfo.setDbType(column.getDataType().getSpecification());
			columnInfo.setColumnName(column.getName());
			columnInfo.setId(DasUtil.isPrimary(column));
			columnInfo.setFieldName(StringUtil.toJavaName(column.getName(), false));
			columnInfo.setComment(column.getComment());
			columnInfo.setDbType(column.getDataType().typeName);
			columnInfo.setAuto(DasUtil.isAuto(column));
			Optional.ofNullable(foreignKey.get(column.getName()))
				.ifPresent(ref -> columnInfo.setRef(true));
			columnInfo.setExt(false);
			
			System.out.println("!--------------------");
			
			DataType dataType = column.getDataType();
			System.out.println(dataType.typeName);
			
			tableInfo.addColumn(columnInfo);
		}
		
		System.out.println(getAbsolutePath(project, table));
		
		return tableInfo;
	}
	
	public void saveTableInfo(Project project, TableInfo tableInfo) {
		File file = new File(project.getBasePath(), SAVE_PATH);
		tableInfo.getTable().getDataSource();
	}
	
	private File getAbsolutePath(Project project, DbTable table) {
		System.out.println("-------------------------------------------");
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
