package com.sjhy.plugin.entity;

import java.util.List;

/**
 * 全局配置分组
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/27 13:10
 */
public class GlobalConfigGroup implements AbstractGroup<GlobalConfig> {
	/**
	 * 分组名称
	 */
	private String name;
	/**
	 * 元素对象集合
	 */
	private List<GlobalConfig> elementList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GlobalConfig> getElementList() {
		return elementList;
	}

	public void setElementList(List<GlobalConfig> elementList) {
		this.elementList = elementList;
	}
}
