package com.mini.plugin.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.database.model.DasColumn;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.EventListener;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

public final class ColumnInfo implements Serializable, EventListener {
	private boolean auto, id, ref, createAt, updateAt, del, lock;
	private String columnName, fieldName, comment, dbType;
	private String refTable, refColumn;
	private boolean notNull;
	@JsonIgnore
	private DasColumn column;
	private int delValue;
	private boolean ext;

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setCreateAt(boolean createAt) {
		this.createAt = createAt;
	}

	public void setUpdateAt(boolean updateAt) {
		this.updateAt = updateAt;
	}

	public void setRefColumn(String refColumn) {
		this.refColumn = refColumn;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setColumn(DasColumn column) {
		this.column = column;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public void setDelValue(int delValue) {
		this.delValue = delValue;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public void setRef(boolean ref) {
		this.ref = ref;
	}

	public void setDel(boolean del) {
		this.del = del;
	}

	public void setExt(boolean ext) {
		this.ext = ext;
	}

	public void setId(boolean id) {
		this.id = id;
	}

	@NotNull
	public String getColumnName() {
		return defaultIfEmpty(columnName, "");
	}

	@NotNull
	public String getFieldName() {
		return defaultIfEmpty(fieldName, "");
	}

	@NotNull
	public String getRefColumn() {
		return defaultIfEmpty(refColumn, "");
	}

	@NotNull
	public String getRefTable() {
		return defaultIfEmpty(refTable, "");
	}

	public boolean isNotNull() {
		return notNull;
	}

	@NotNull
	public String getComment() {
		return defaultIfEmpty(comment, "");
	}

	@NotNull
	public String getDbType() {
		return defaultIfEmpty(dbType, "");
	}

	public boolean isCreateAt() {
		return createAt;
	}

	public boolean isUpdateAt() {
		return updateAt;
	}

	@NotNull
	public DasColumn getColumn() {
		return column;
	}

	public int getDelValue() {
		return delValue;
	}

	public boolean isAuto() {
		return auto;
	}

	public boolean isLock() {
		return lock;
	}

	public boolean isRef() {
		return ref;
	}

	public boolean isDel() {
		return del;
	}

	public boolean isExt() {
		return ext;
	}

	public boolean isId() {
		return id;
	}
}
