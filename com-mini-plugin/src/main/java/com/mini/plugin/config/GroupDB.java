package com.mini.plugin.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Optional;

public final class GroupDB implements AbstractGroup<Template, GroupDB> {
	private LinkedHashMap<String, Template> elements;
	private String name;
	
	@Override
	public synchronized void setElements(LinkedHashMap<String, Template> elements) {
		Optional.ofNullable(elements).ifPresent(ele -> //
			ele.forEach((e, v) -> addElement(v)));
	}
	
	@NotNull
	@Override
	public synchronized LinkedHashMap<String, Template> getElements() {
		if (GroupDB.this.elements == null) {
			elements = new LinkedHashMap<>();
		}
		return elements;
	}
	
	@Override
	public synchronized void addElement(Template element) {
		if (GroupDB.this.elements == null) {
			elements = new LinkedHashMap<>();
		}
		if (element == null) return;
		elements.put(element.getName(), element);
	}
	
	@Nullable
	@Override
	public Template get(String name) {
		return elements.get(name);
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	@Override
	public String getName() {
		return name;
	}
	
	@NotNull
	@Override
	public synchronized final GroupDB copy() {
		LinkedHashMap<String, Template> map = new LinkedHashMap<>();
		getElements().forEach((key, value) -> map.put(key, Template.builder()
			.name(value.getName())
			.code(value.getCode())
			.build()));
		GroupDB db = new GroupDB();
		db.setElements(map);
		db.setName(name);
		return db;
	}
	
	public static Builder builder() {
		return new Builder(new GroupDB());
	}
	
	public static final class Builder {
		private final GroupDB db;
		
		protected Builder(GroupDB db) {
			this.db = db;
		}
		
		public Builder elements(LinkedHashMap<String, Template> elements) {
			db.setElements(elements);
			return this;
		}
		
		public Builder element(Template element) {
			db.addElement(element);
			return this;
		}
		
		public Builder name(String val) {
			this.db.setName(val);
			return this;
		}
		
		
		public GroupDB build() {
			return db;
		}
	}
	
	
}
