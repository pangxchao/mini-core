package com.mini.plugin.config;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface AbstractGroup<E, T extends AbstractGroup<E, T>> {
	void setElements(List<E> elements);
	
	void addElement(E element);
	
	void setName(String name);
	
	@Nullable
	List<E> getElements();
	
	@Nullable
	String getName();
	
	T copy();
}
