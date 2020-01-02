package com.sjhy.plugin.entity;


import java.util.List;

/**
 * 模板分组类
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/18 09:33
 */
public class TemplateGroup implements AbstractGroup<Template> {
	/**
	 * 分组名称
	 */
	private String name;
	/**
	 * 元素对象
	 */
	private List<Template> elementList;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Template> getElementList() {
		return elementList;
	}

	@Override
	public void setElementList(List<Template> elementList) {
		this.elementList = elementList;
	}
}
