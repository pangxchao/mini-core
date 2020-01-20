package com.sjhy.plugin.entity;


/**
 * 调试字段实体类
 * @author makejava
 * @version 1.0.0
 * @since 2018/09/03 11:09
 */
public class DebugField {
	/**
	 * 字段名
	 */
	private String name;
	/**
	 * 字段类型
	 */
	private Class<?> type;
	/**
	 * 字段值
	 */
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
