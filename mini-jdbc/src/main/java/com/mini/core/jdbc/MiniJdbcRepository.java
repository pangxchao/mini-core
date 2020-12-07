package com.mini.core.jdbc;

import com.mini.core.data.MiniBaseRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface MiniJdbcRepository extends MiniBaseRepository {

    <T> T insertOrUpdate(@NotNull T instance);

    <T> T insert(@NotNull T entity);

    <T> T update(@NotNull T entity);
}
