package com.sjhy.plugin.entity;

/**
 * 类型隐射信息
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class TypeMapper {
	/**
	 * 列类型
	 */
	private String columnType;
	/**
	 * java类型
	 */
	private String javaType;

	public TypeMapper(String columnType, String javaType) {
		this.columnType = columnType;
		this.javaType   = javaType;
	}

	public TypeMapper() {
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
}
