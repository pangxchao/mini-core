package com.mini.plugin.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.database.model.DasColumn;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.EventListener;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

public final class ColumnInfo implements Serializable, EventListener {
	private boolean auto, id, createAt, updateAt, del, lock;
	private String columnName, fieldName, comment, dbType;
	private boolean notNull;
	@JsonIgnore
	private DasColumn column;
	private int delValue;
	
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
	
	public void setDel(boolean del) {
		this.del = del;
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
	
	public boolean isDel() {
		return del;
	}
	
	public boolean isId() {
		return id;
	}
}
