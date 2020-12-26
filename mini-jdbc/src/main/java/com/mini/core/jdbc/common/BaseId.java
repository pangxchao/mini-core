package com.mini.core.jdbc.common;

public interface BaseId<T> {
    void setId(T id);

    T getId();
}
