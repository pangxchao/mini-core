package com.mini.core.jdbc;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;


public class MiniJdbcBaseRepositoryImpl implements MiniJdbcBaseRepository {
    @NotNull
    private final JdbcAggregateOperations entityOperations;

    public MiniJdbcBaseRepositoryImpl(@NotNull JdbcAggregateOperations entityOperations) {
        this.entityOperations = entityOperations;
    }

    @Override
    public final <T> T insertOrUpdate(@NotNull T entity) {
        return entityOperations.save(entity);
    }

    @Override
    public final <T> T insert(@NotNull T entity) {
        return entityOperations.insert(entity);
    }

    @Override
    public final <T> T update(@NotNull T entity) {
        return entityOperations.update(entity);
    }

}
