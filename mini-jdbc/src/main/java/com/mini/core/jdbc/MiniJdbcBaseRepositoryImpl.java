package com.mini.core.jdbc;

import com.mini.core.data.MiniBaseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository;
import org.springframework.data.mapping.PersistentEntity;


public class MiniJdbcBaseRepositoryImpl<E, ID> extends SimpleJdbcRepository<E, ID> implements MiniBaseRepository<E, ID> {
    @NotNull
    private final JdbcAggregateOperations entityOperations;

    public MiniJdbcBaseRepositoryImpl(@NotNull JdbcAggregateOperations entityOperations,
                                      @NotNull PersistentEntity<E, ?> entity) {
        super(entityOperations, entity);
        this.entityOperations = entityOperations;
    }

    @Override
    public final <S extends E> S insertOrUpdate(@NotNull S entity) {
        return this.save(entity);
    }

    @Override
    public final <S extends E> S insert(@NotNull S entity) {
        return entityOperations.insert(entity);
    }

    @Override
    public final <S extends E> S update(@NotNull S entity) {
        return entityOperations.update(entity);
    }

    @Override
    public final Page<E> queryAll(@NotNull Pageable pageable) {
        return findAll(pageable);
    }

    @Override
    public final Iterable<E> queryAll(@NotNull Sort sort) {
        return findAll(sort);
    }

    @Override
    public final E queryById(@NotNull ID id) {
        return findById(id).orElse(null);
    }

    @Override
    public final Iterable<E> queryAll() {
        return findAll();
    }
}
