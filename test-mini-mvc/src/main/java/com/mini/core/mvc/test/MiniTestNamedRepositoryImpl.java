package com.mini.core.mvc.test;

import com.mini.core.jdbc.MiniNamedRepositoryImpl;
import lombok.NonNull;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class MiniTestNamedRepositoryImpl<E> extends MiniNamedRepositoryImpl implements MiniTestNamedRepository<E> {
    @NonNull
    private final JdbcAggregateOperations entityOperations;

    public MiniTestNamedRepositoryImpl(@NonNull JdbcAggregateOperations entityOperations,
                                       NamedParameterJdbcOperations jdbcOperations,
                                       Dialect jdbcDialect) {
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
