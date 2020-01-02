package com.mini.plugin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class TypeMapperGroup implements AbstractGroup<Map<String, Class<?>>> {
	private final List<Map<String, Class<?>>> list = new ArrayList<>();
	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Map<String, Class<?>>> getElementList() {
		return this.list;
	}

	@Override
	public void addElement(Map<String, Class<?>> element) {
		this.list.add(element);
	}
}
