package com.mini.core.jdbc.common;

public interface StringId extends BaseId<String> {
    void setId(String id);

    String getId();
}
