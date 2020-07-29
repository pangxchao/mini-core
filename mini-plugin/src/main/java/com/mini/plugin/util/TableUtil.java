package com.mini.plugin.util;

import com.intellij.database.model.DasColumn;
import com.intellij.database.psi.DbDataSource;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.openapi.project.Project;
import com.mini.plugin.config.ColumnInfo;
import com.mini.plugin.config.DatabaseInfo;
import com.mini.plugin.config.Settings;
import com.mini.plugin.config.TableInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.*;

import static com.intellij.database.util.DasUtil.getSchema;
import static com.intellij.openapi.ui.Messages.showWarningDialog;
import static com.mini.plugin.util.Constants.SAVE_FAILED_TABLE;
import static com.mini.plugin.util.Constants.TITLE_INFO;
import static com.mini.plugin.util.StringUtil.toJavaName;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

public final class TableUtil implements EventListener, Serializable {
	private static final Settings setting = Settings.getInstance();
	
	@NotNull
	public static TableInfo createTableInfo(Project project, DbTable table) {
		final TableInfo tableInfo = new TableInfo();
		tableInfo.setComment(table.getComment());
		tableInfo.setTableName(table.getName());
		tableInfo.setTable(table);
		// 获取所有字段
		LinkedHashMap<String, ColumnInfo> columnMap = new LinkedHashMap<>();
		for (DasColumn column : DasUtil.getColumns(table)) {
			final ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setDatabaseType(column.getDataType().typeName.toUpperCase());
			columnInfo.setFieldName(toJavaName(column.getName(), false));
			columnInfo.setColumnName(column.getName());
			columnInfo.setId(DasUtil.isPrimary(column));
			columnInfo.setComment(column.getComment());
			columnInfo.setAuto(DasUtil.isAuto(column));
			columnInfo.setNotNull(column.isNotNull());
			columnMap.put(columnInfo.getColumnName(), columnInfo);
		}
		// 设置所有字段
		tableInfo.setColumnMap(columnMap);
		// 读取本地配置
		ofNullable(readTableInfo(project, table)).ifPresent(info -> {
			if (StringUtil.isNotEmpty(info.getEntityName())) {
				tableInfo.setEntityName(info.getEntityName());
			}
			if (StringUtil.isNotEmpty(info.getNamePrefix())) {
				tableInfo.setNamePrefix(info.getNamePrefix());
			}
			of(info.getColumnMap()).ifPresent(map -> map.forEach((key, column) -> {
				of(tableInfo.getColumnMap()).map(v -> v.get(key)).ifPresent(c -> {
					if (StringUtil.isNotEmpty(column.getFieldName())) {
						c.setFieldName(column.getFieldName());
					}
					c.setDelValue(column.getDelValue());
					c.setCreateAt(column.isCreateAt());
					c.setUpdateAt(column.isUpdateAt());
					c.setLock(column.isLock());
					c.setDel(column.isDel());
				});
			}));
		});
		return tableInfo;
	}
	
	@Nullable
	public static TableInfo readTableInfo(Project project, DbTable table) {
		try {
			return Optional.of(setting.getDatabaseInfoMap())
					.map(map -> map.get(getSchema(table)))
					.map(db -> db.getTableInfo(table.getName()))
					.orElse(null);
		} catch (Throwable e) {
			return null;
		}
	}
	
	public static void saveTableInfo(Project project, TableInfo tableInfo) {
		try {
			if (tableInfo.getTable() == null) return;
			String databaseName = getSchema(tableInfo.getTable());
			HashMap<String, DatabaseInfo> m = setting.getDatabaseInfoMap();
			
			// 清理多余未使用的数据库
			HashMap<String, DatabaseInfo> dbMap = new HashMap<>();
			DbDataSource dataSource = tableInfo.getTable().getDataSource();
			DasUtil.getSchemas(dataSource).forEach(names -> {
				DatabaseInfo info = m.get(names.getName());
				dbMap.put(names.getName(), info);
			});
			
			// 获取当前数据库的所有已保存的表信息
			DatabaseInfo databaseInfo = dbMap.computeIfAbsent(databaseName, (key) ->
					new DatabaseInfo().setDatabaseName(databaseName)
							.setTableInfoMap(new TreeMap<>()));
			
			// 清理多余未使用的数据库表
			TreeMap<String, TableInfo> tMap = new TreeMap<>();
			DasUtil.getTables(dataSource).forEach(t -> {
				TableInfo info = databaseInfo.getTableInfo(t.getName());
				tMap.put(t.getName(), info);
			});
			
			// 保存数据库和表信息
			tMap.put(tableInfo.getTableName(), tableInfo);
			databaseInfo.setTableInfoMap(tMap);
			dbMap.put(databaseName, databaseInfo);
			setting.setDatabaseInfoMap(dbMap);
		} catch (Error | RuntimeException e) {
			showWarningDialog(SAVE_FAILED_TABLE, //
					TITLE_INFO);
		}
	}
	
	
}
