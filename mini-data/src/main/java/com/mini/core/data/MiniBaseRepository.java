package com.mini.core.data;

import org.jetbrains.annotations.NotNull;


public interface MiniBaseRepository {
    <T> T insertOrUpdate(@NotNull T instance);

    <T> T insert(@NotNull T entity);

    <T> T update(@NotNull T entity);

}
