package com.sjhy.plugin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.database.psi.DbTable;

import java.util.List;

/**
 * 表信息
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class TableInfo {
	/**
	 * 原始对象
	 */
	@JsonIgnore
	private DbTable obj;
	/**
	 * 表名（首字母大写）
	 */
	private String name;
	/**
	 * 注释
	 */
	private String comment;
	/**
	 * 所有列
	 */
	private List<ColumnInfo> fullColumn;
	/**
	 * 主键列
	 */
	private List<ColumnInfo> pkColumn;
	/**
	 * 其他列
	 */
	private List<ColumnInfo> otherColumn;
	/**
	 * 保存的包名称
	 */
	private String savePackageName;
	/**
	 * 保存路径
	 */
	private String savePath;
	/**
	 * 保存的model名称
	 */
	private String saveModelName;

	public DbTable getObj() {
		return obj;
	}

	public void setObj(DbTable obj) {
		this.obj = obj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ColumnInfo> getFullColumn() {
		return fullColumn;
	}

	public void setFullColumn(List<ColumnInfo> fullColumn) {
		this.fullColumn = fullColumn;
	}

	public List<ColumnInfo> getPkColumn() {
		return pkColumn;
	}

	public void setPkColumn(List<ColumnInfo> pkColumn) {
		this.pkColumn = pkColumn;
	}

	public List<ColumnInfo> getOtherColumn() {
		return otherColumn;
	}

	public void setOtherColumn(List<ColumnInfo> otherColumn) {
		this.otherColumn = otherColumn;
	}

	public String getSavePackageName() {
		return savePackageName;
	}

	public void setSavePackageName(String savePackageName) {
		this.savePackageName = savePackageName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getSaveModelName() {
		return saveModelName;
	}

	public void setSaveModelName(String saveModelName) {
		this.saveModelName = saveModelName;
	}
}
