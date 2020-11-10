package com.mini.core.data.jpa;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class MiniJpaBaseRepositoryImpl<E, ID> extends SimpleJpaRepository<E, ID> implements MiniJpaBaseRepository<E, ID> {
    @NotNull
    private final EntityManager em;

    public MiniJpaBaseRepositoryImpl(@NotNull JpaEntityInformation<E, ?> entityInformation,
                                     @NotNull EntityManager entityManager) {
        super(entityInformation, entityManager);
        em = entityManager;
    }

    @Override
    public final <S extends E> S insertOrUpdate(@NotNull S entity) {
        return super.save(entity);
    }

    @Override
    public final <S extends E> S insert(@NotNull S entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public final <S extends E> S update(@NotNull S entity) {
        return em.merge(entity);
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
