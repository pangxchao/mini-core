package com.mini.core.jdbc;

import com.mini.core.data.MiniBaseRepositoryImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.dialect.Dialect;

import javax.sql.DataSource;


public class MiniJdbcRepositoryImpl extends MiniBaseRepositoryImpl implements MiniJdbcRepository {
    @NotNull
    private final JdbcAggregateOperations entityOperations;

    public MiniJdbcRepositoryImpl(@NotNull DataSource dataSource, @NotNull Dialect jdbcDialect,
                                  @NotNull JdbcAggregateOperations entityOperations) {
        super(dataSource, jdbcDialect);
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
