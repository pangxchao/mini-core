package com.mini.plugin.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * 数据库连接信息-库
 * @author xchao
 */
public final class DatabaseInfo extends AbstractClone<DatabaseInfo> implements Serializable {
	public static final TypeReference<HashMap<String, DatabaseInfo>> TYPE = new TypeReference<HashMap<String, DatabaseInfo>>() {};
	private TreeMap<String, TableInfo> tableInfoMap;
	private String databaseName;
	
	public DatabaseInfo setTableInfoMap(TreeMap<String, TableInfo> tableInfoMap) {
		this.tableInfoMap = tableInfoMap;
		return this;
	}
	
	public DatabaseInfo setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
		return this;
	}
	
	@NotNull
	public TreeMap<String, TableInfo> getTableInfoMap() {
		if (DatabaseInfo.this.tableInfoMap == null) {
			tableInfoMap = new TreeMap<>();
		}
		return tableInfoMap;
	}
	
	@NotNull
	public synchronized String getDatabaseName() {
		if (StringUtil.isEmpty(databaseName)) {
			databaseName = "";
		}
		return databaseName;
	}
	
	public DatabaseInfo addTableInfo(@NotNull TableInfo tableInfo) {
		getTableInfoMap().put(tableInfo.getTableName(), tableInfo);
		return this;
	}
	
	@Nullable
	public TableInfo getTableInfo(String tableName) {
		if (tableInfoMap == null) return null;
		return tableInfoMap.get(tableName);
	}
	
	@NotNull
	@Override
	public final DatabaseInfo clone() throws Error {
		final DatabaseInfo info = new DatabaseInfo();
		info.setTableInfoMap(new TreeMap<>(getTableInfoMap()));
		info.setDatabaseName(databaseName);
		return info;
	}
	
	
}
