package com.mini.core.data.common;

public interface BaseId<T> {
    void setId(T id);

    T getId();
}
