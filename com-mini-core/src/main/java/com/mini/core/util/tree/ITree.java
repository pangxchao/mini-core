package com.mini.core.util.tree;

import javax.annotation.Nonnull;
import java.util.List;

public interface ITree<T extends ITree> {
	long getId();
	
	@Nonnull
	String getName();
	
	Long getParentId();
	
	@Nonnull
	List<T> children();
	
	default void addChild(@Nonnull T item) {
		children().add(item);
	}
	
	default void setChildren(@Nonnull List<T> children) {
		ITree.this.children().clear();
		children().addAll(children);
	}
}
