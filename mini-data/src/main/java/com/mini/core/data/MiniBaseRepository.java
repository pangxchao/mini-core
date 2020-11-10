package com.mini.core.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface MiniBaseRepository<E, ID> {
    <S extends E> S insertOrUpdate(@NotNull S instance);

    <S extends E> S insert(@NotNull S entity);

    <S extends E> S update(@NotNull S entity);

    Page<E> queryAll(@NotNull Pageable pageable);

    Iterable<E> queryAll(@NotNull Sort sort);

    E queryById(@NotNull ID id);

    @Nullable
    Iterable<E> queryAll();
}
