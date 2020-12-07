package com.mini.core.jpa;

import com.mini.core.data.MiniBaseRepositoryImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.relational.core.dialect.Dialect;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

public class MiniJpaRepositoryImpl extends MiniBaseRepositoryImpl implements MiniJpaRepository {
    @NotNull
    private final EntityManager em;

    public MiniJpaRepositoryImpl(@NotNull DataSource dataSource, @NotNull Dialect dialect,
                                 @NotNull EntityManager entityManager) {
        super(dataSource, dialect);
        em = entityManager;
    }

    @Override
    public final <T> T insert(@NotNull T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public final <T> T update(@NotNull T entity) {
        return em.merge(entity);
    }

}
