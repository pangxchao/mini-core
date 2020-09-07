package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;

public interface AbstractCopy<T> {

    @NotNull
    T copy();
}
