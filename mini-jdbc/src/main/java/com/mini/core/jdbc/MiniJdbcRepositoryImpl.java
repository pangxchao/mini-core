package com.mini.core.jdbc;

import org.springframework.data.jdbc.core.JdbcAggregateOperations;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

public class MiniJdbcRepositoryImpl extends DefaultMiniJdbcTemplate implements MiniJdbcRepository {
    private final JdbcAggregateOperations operations;

    public MiniJdbcRepositoryImpl(@Nonnull DataSource dataSource, JdbcAggregateOperations operations) {
        super(dataSource);
        this.operations = operations;
    }

    @Nonnull
    @Override
    public <Entity> Entity insert(@Nonnull Entity entity) {
        return operations.insert(entity);
    }

    @Nonnull
    @Override
    public <Entity> Entity update(@Nonnull Entity entity) {
        return operations.update(entity);
    }
}
