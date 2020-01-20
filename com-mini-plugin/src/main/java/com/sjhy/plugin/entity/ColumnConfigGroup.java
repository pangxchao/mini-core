package com.sjhy.plugin.entity;


import java.util.List;

/**
 * 列配置分组
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/18 09:33
 */
public class ColumnConfigGroup implements AbstractGroup<ColumnConfig> {
	/**
	 * 分组名称
	 */
	private String name;
	/**
	 * 元素对象
	 */
	private List<ColumnConfig> elementList;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<ColumnConfig> getElementList() {
		return elementList;
	}

	@Override
	public void setElementList(List<ColumnConfig> elementList) {
		this.elementList = elementList;
	}
}
