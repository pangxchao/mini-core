package com.mini.plugin.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.database.psi.DbTable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

public final class TableInfo implements Serializable, EventListener {
	private final Map<String, ColumnInfo> columns = new LinkedHashMap<>();
	private String tableName, entityName, comment;
	private String packageName, namePrefix;
	@JsonIgnore
	private DbTable table;

	public final synchronized void addColumn(ColumnInfo column) {
		this.columns.put(column.getColumnName(), column);
	}

	public void setColumns(Map<String, ColumnInfo> columns) {
		TableInfo.this.columns.clear();
		this.columns.putAll(columns);
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

	@NotNull
	public final List<ColumnInfo> getColumnList() {
		return new ArrayList<>(columns.values());
	}

	@NotNull
	public final Map<String, ColumnInfo> getColumns() {
		return columns;
	}

	public final boolean hasColumn(String name) {
		return columns.containsKey(name);
	}

	@NotNull
	public final String getPackageName() {
		return defaultIfEmpty(packageName, "");
	}

	@NotNull
	public final String getNamePrefix() {
		return defaultIfEmpty(namePrefix, "");
	}

	@NotNull
	public final String getEntityName() {
		return defaultIfEmpty(entityName, "");
	}

	@NotNull
	public final String getTableName() {
		return defaultIfEmpty(tableName, "");
	}

	@NotNull
	public final String getComment() {
		return defaultIfEmpty(comment, "");
	}

	@NotNull
	public final DbTable getTable() {
		return table;
	}
}
