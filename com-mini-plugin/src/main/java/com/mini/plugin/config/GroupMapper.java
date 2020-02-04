package com.mini.plugin.config;

import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Optional;

public final class GroupMapper implements AbstractGroup<TypeMapper, GroupMapper> {
	private LinkedHashMap<String, TypeMapper> elements;
	private String name;
	
	@Override
	public synchronized void setElements(LinkedHashMap<String, TypeMapper> elements) {
		Optional.ofNullable(elements).ifPresent(ele -> //
			ele.forEach((e, v) -> addElement(v)));
	}
	
	@NotNull
	@Override
	public synchronized LinkedHashMap<String, TypeMapper> getElements() {
		if (GroupMapper.this.elements == null) {
			elements = new LinkedHashMap<>();
		}
		return elements;
	}
	
	@Override
	public synchronized final void addElement(TypeMapper element) {
		if (GroupMapper.this.elements == null) {
			elements = new LinkedHashMap<>();
		}
		if (element == null) return;
		elements.put(element.getDatabaseType()//
			.toUpperCase(), element);
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public TypeMapper get(String name) {
		if (StringUtil.isEmpty(name)) {
			return null;
		}
		return elements.get(name //
			.toUpperCase());
	}
	
	@NotNull
	@Override
	public String getName() {
		return name;
	}
	
	@NotNull
	@Override
	public synchronized final GroupMapper copy() {
		LinkedHashMap<String, TypeMapper> map = new LinkedHashMap<>();
		getElements().forEach((key, value) -> map.put(key, TypeMapper.builder()
			.nullJavaType(value.getNullJavaType())
			.databaseType(value.getDatabaseType())
			.javaType(value.getJavaType())
			.build()));
		GroupMapper mapper = new GroupMapper();
		mapper.setElements(map);
		mapper.setName(name);
		return mapper;
	}
	
	public static GroupMapper.Builder builder() {
		return new Builder(new GroupMapper());
	}
	
	public static final class Builder {
		private final GroupMapper mapper;
		
		protected Builder(GroupMapper mapper) {
			this.mapper = mapper;
		}
		
		public Builder elements(LinkedHashMap<String, TypeMapper> elements) {
			mapper.setElements(elements);
			return this;
		}
		
		public Builder element(TypeMapper element) {
			mapper.addElement(element);
			return this;
		}
		
		public Builder name(String val) {
			this.mapper.setName(val);
			return this;
		}
		
		public GroupMapper build() {
			return mapper;
		}
	}
}
