package com.mini.core.jdbc;

public interface MiniBaseRepository<E> {
    <S extends E> S insert(S entity);

    <S extends E> S update(S entity);
}
