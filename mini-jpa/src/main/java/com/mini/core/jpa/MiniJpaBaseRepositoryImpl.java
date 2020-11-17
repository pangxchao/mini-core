package com.mini.core.jpa;

import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;

public class MiniJpaBaseRepositoryImpl implements MiniJpaBaseRepository {
    @NotNull
    private final EntityManager em;

    public MiniJpaBaseRepositoryImpl(@NotNull EntityManager entityManager) {
        em = entityManager;
    }

    @Override
    public final <T> T insertOrUpdate(@NotNull T entity) {
        em.refresh(entity);
        return entity;
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
