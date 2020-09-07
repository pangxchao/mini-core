package com.mini.plugin.state;

public interface AbstractData<T extends AbstractData<T>> extends AbstractCopy<T> {
    void setName(String name);

    String getName();
}