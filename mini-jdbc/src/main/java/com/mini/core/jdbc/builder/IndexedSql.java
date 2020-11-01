package com.mini.core.jdbc.builder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;

public abstract class IndexedSql<T extends IndexedSql<T>> extends BaseSql<T> {
    private final List<Object> args = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public final T ARGS(Object... args) {
        addAll(this.args, args);
        return (T) this;
    }

    public final List<Object> getArgs() {
        return this.args;
    }
}
