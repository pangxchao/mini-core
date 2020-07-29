package com.mini.core.util.tree;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public interface ITree<T extends ITree> {
	default void addChild(@Nonnull T child) {
		if (this.getChildren() == null) {
			var c = new ArrayList<T>();
			setChildren(c);
		}
		getChildren().add(child);
	}
	
	void setChildren(List<T> children);
	
	List<T> getChildren();
	
	Long getParentId();
	
	String getName();
	
	long getId();
}
