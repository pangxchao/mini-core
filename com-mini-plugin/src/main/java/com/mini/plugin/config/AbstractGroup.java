package com.mini.plugin.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;

public interface AbstractGroup<E, T extends AbstractGroup<E, T>> {
	void setElements(LinkedHashMap<String, E> elements);
	
	@NotNull
	LinkedHashMap<String, E> getElements();
	
	void addElement(E element);
	
	void setName(String name);
	
	@Nullable
	E get(String name);
	
	@NotNull
	String getName();
	
	@NotNull
	T copy();
}
