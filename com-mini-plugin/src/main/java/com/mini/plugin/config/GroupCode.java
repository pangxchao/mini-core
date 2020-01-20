package com.mini.plugin.config;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

public final class GroupCode implements AbstractGroup<Template, GroupCode> {
	private List<Template> elements;
	private String name;
	
	@Override
	public synchronized final void addElement(Template element) {
		if (GroupCode.this.elements == null) {
			elements = new ArrayList<>();
		}
		elements.add(element);
	}
	
	@Override
	public void setElements(List<Template> elements) {
		this.elements = elements;
	}
	
	@Nullable
	@Override
	public List<Template> getElements() {
		return elements;
	}
	
	@Nullable
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public synchronized final GroupCode copy() {
		List<Template> list = new ArrayList<>();
		ofNullable(elements).ifPresent(el -> el.forEach(e -> {
			list.add(Template.builder()
				.name(e.getName())
				.code(e.getCode())
				.build());
		}));
		GroupCode mapper = new GroupCode();
		mapper.setElements(list);
		mapper.setName(name);
		return mapper;
	}
}
