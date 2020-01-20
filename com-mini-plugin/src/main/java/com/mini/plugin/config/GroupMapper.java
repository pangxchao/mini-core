package com.mini.plugin.config;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

public final class GroupMapper implements AbstractGroup<TypeMapper, GroupMapper> {
	private List<TypeMapper> elements;
	private String name;
	
	@Override
	public synchronized final void addElement(TypeMapper element) {
		if (GroupMapper.this.elements == null) {
			elements = new ArrayList<>();
		}
		elements.add(element);
	}
	
	@Override
	public void setElements(List<TypeMapper> elements) {
		this.elements = elements;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Nullable
	@Override
	public List<TypeMapper> getElements() {
		return elements;
	}
	
	@Nullable
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public synchronized final GroupMapper copy() {
		List<TypeMapper> list = new ArrayList<>();
		ofNullable(elements).ifPresent(el -> el.forEach(e -> {
			list.add(TypeMapper.builder()
				.nullJavaType(e.getNullJavaType())
				.databaseType(e.getDatabaseType())
				.javaType(e.getJavaType())
				.build());
		}));
		GroupMapper mapper = new GroupMapper();
		mapper.setElements(list);
		mapper.setName(name);
		return mapper;
	}
}
