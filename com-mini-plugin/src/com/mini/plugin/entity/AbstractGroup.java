package com.mini.plugin.entity;

import java.util.List;

public interface AbstractGroup<E> {
	String getName();

	void setName(String name);

	List<E> getElementList();

	void addElement(E element);
}
