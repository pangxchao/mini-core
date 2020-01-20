package com.sjhy.plugin.entity;


/**
 * 调试方法实体类
 * @author makejava
 * @version 1.0.0
 * @since 2018/09/03 11:10
 */
public class DebugMethod {
	/**
	 * 方法名
	 */
	private String name;
	/**
	 * 方法描述
	 */
	private String desc;
	/**
	 * 执行方法得到的值
	 */
	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
