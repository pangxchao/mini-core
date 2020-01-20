package com.mini.plugin.config;

import com.google.gson.annotations.Expose;
import com.intellij.database.psi.DbTable;

import java.io.Serializable;
import java.util.EventListener;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TableInfo implements Serializable, EventListener {
	private final Map<String, ColumnInfo> columns = new LinkedHashMap<>();
	private String tableName, entityName, comment, packageName, namePrefix;
	@Expose(serialize = false, deserialize = false)
	private DbTable table;
	
	public synchronized void addColumn(ColumnInfo column) {
		this.columns.put(column.getColumnName(), column);
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public void setTable(DbTable table) {
		this.table = table;
	}
	
	public Map<String, ColumnInfo> getColumns() {
		return columns;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public String getNamePrefix() {
		return namePrefix;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getComment() {
		return comment;
	}
	
	public DbTable getTable() {
		return table;
	}
}
