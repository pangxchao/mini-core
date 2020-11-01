package com.mini.core.jdbc.builder;

import java.util.HashMap;
import java.util.Map;

public abstract class NamedSql<T extends NamedSql<T>> extends BaseSql<T> {
    public final Map<String, Object> args = new HashMap<>();

    @SuppressWarnings("unchecked")
    public final T ARG(String key, Object arg) {
        this.args.put(key, arg);
        return (T) this;
    }

    public final Map<String, Object> getArgs() {
        return this.args;
    }
}
