package com.mini.core.util.tree;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface ITree<T extends ITree<T>> {
    default void addChild(@NotNull T child) {
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

    Long getId();
}