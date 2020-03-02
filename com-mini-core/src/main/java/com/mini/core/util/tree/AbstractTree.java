package com.mini.core.util.tree;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTree<T extends ITree> implements ITree<T> {
	private final List<T> children = new ArrayList<>();

	@Nonnull
	@Override
	public final List<T> children() {
		return children;
	}

	@Override
	public final void addChild(@Nonnull T item) {
		children.add(item);
	}

	@Override
	public void setChildren(@Nonnull List<T> children) {
		AbstractTree.this.children.clear();
		this.children.addAll(children);
	}
}
