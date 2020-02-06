package com.mini.plugin.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Optional;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;

public final class GroupCode implements AbstractGroup<Template, GroupCode> {
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
		if (GroupCode.this.elements == null) {
			elements = new LinkedHashMap<>();
		}
		return elements;
	}
	
	@Override
	public synchronized void addElement(Template element) {
		if (GroupCode.this.elements == null) {
			elements = new LinkedHashMap<>();
		}
		if (element == null) return;
		elements.put(element.getName(), element);
	}
	
	@Nullable
	@Override
	public synchronized Template get(String name) {
		return elements.get(name);
	}
	
	@Override
	public synchronized void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	@Override
	public synchronized String getName() {
		return defaultIfEmpty(name, "");
	}
	
	@NotNull
	@Override
	public synchronized final GroupCode copy() {
		LinkedHashMap<String, Template> map = new LinkedHashMap<>();
		getElements().forEach((key, value) -> map.put(key, Template.builder()
			.name(value.getName())
			.code(value.getCode())
			.build()));
		GroupCode code = new GroupCode();
		code.setElements(map);
		code.setName(name);
		return code;
	}
	
	public static GroupCode.Builder builder() {
		return new Builder(new GroupCode());
	}
	
	public static final class Builder {
		private final GroupCode code;
		
		protected Builder(GroupCode code) {
			this.code = code;
		}
		
		public Builder elements(LinkedHashMap<String, Template> elements) {
			code.setElements(elements);
			return this;
		}
		
		public Builder element(Template element) {
			code.addElement(element);
			return this;
		}
		
		public Builder name(String val) {
			this.code.setName(val);
			return this;
		}
		
		
		public GroupCode build() {
			return code;
		}
	}
}
