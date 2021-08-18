package com.mini.core.mybatis.database;

import org.springframework.context.ApplicationEvent;

import javax.annotation.Nullable;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNullElse;

public class DatabaseEvent extends ApplicationEvent {

    public DatabaseEvent(@Nullable Object source) {
        super(requireNonNullElse(source, "0"));
    }

    public final int getVersion() {
        var v = source.toString();
        return parseInt(v);
    }
}
