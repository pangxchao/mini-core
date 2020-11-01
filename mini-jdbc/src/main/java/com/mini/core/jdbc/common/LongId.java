package com.mini.core.jdbc.common;

public interface LongId extends BaseId<Long> {
    void setId(Long id);

    Long getId();
}
