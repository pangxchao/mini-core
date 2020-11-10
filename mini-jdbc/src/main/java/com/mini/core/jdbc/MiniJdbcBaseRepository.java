package com.mini.core.jdbc;

import com.mini.core.data.MiniBaseRepository;

public interface MiniJdbcBaseRepository<E, ID> extends MiniBaseRepository<E, ID> {
    // <S extends E> S insertOnUpdate(@NotNull S instance);
}
