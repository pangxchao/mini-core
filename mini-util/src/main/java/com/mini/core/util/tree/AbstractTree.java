
package com.mini.core.util.tree;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractTree<T extends ITree<T>> implements ITree<T> {

    @Override
    public final void addChild(@NotNull T child) {
        ITree.super.addChild(child);
    }

    @Override
    public void setChildren(List<T> children) {
        this.children = children;
    }

    @Override
    public List<T> getChildren() {
        return children;
    }

    private List<T> children;
}