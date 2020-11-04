package com.mini.core.mvc.test;

import com.mini.core.jdbc.MiniIndexedRepositoryImpl;
import lombok.NonNull;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class MiniTestIndexedRepositoryImpl<E> extends MiniIndexedRepositoryImpl implements MiniTestIndexedRepository<E> {
    @NonNull
    private final JdbcAggregateOperations entityOperations;

    public MiniTestIndexedRepositoryImpl(NamedParameterJdbcOperations jdbcOperations, Dialect jdbcDialect,
                                         @NonNull JdbcAggregateOperations entityOperations) {
        super(jdbcOperations, jdbcDialect);
        this.entityOperations = entityOperations;
    }

    @Override
    public <S extends E> S insert(S entity) {
        return entityOperations.insert(entity);
    }

    @Override
    public <S extends E> S update(S entity) {
        return entityOperations.update(entity);
    }
}
