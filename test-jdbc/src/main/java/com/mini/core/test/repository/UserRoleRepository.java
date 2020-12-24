package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniJdbcRepository;
import com.mini.core.test.entity.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.mini.core.test.entity.UserRole.*;
import static java.util.Optional.ofNullable;

@Repository("userRoleRepository")
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, UserRole.ID>, MiniJdbcRepository {

    @Nonnull
    @Override
    default Iterable<UserRole> findAllById(@Nonnull Iterable<UserRole.ID> idList) {
        return this.select(UserRole.class, selectFragment -> {
            for (final UserRole.ID id : idList) {
                selectFragment.or().where(w -> {
                    w.eq(USER_ID, id.getUserId());
                    w.eq(ROLE_ID, id.getRoleId());
                });
            }
        });
    }

    @Nonnull
    @Override
    default Optional<UserRole> findById(@Nonnull UserRole.ID id) {
        return ofNullable(selectOne(UserRole.class, it -> {
            it.whereEq(USER_ID, id.getUserId());
            it.whereEq(ROLE_ID, id.getRoleId());
        }));
    }

    @Nonnull
    default Optional<UserRole> findById(Long userId, Long roleId) {
        return ofNullable(selectOne(UserRole.class, it -> {
            it.whereEq(USER_ID, userId);
            it.whereEq(ROLE_ID, roleId);
        }));
    }

    @Override
    default boolean existsById(@Nonnull UserRole.ID id) {
        return findById(id).isPresent();
    }

    default boolean existsById(Long userId, Long roleId) {
        return findById(userId, roleId).isPresent();
    }

    @Override
    default void deleteById(@Nonnull UserRole.ID id) {
        this.delete(USER_ROLE, it -> {
            it.whereEq(USER_ID, id.getUserId());
            it.whereEq(ROLE_ID, id.getRoleId());
        });
    }

    default int deleteById(Long userId, Long roleId) {
        return this.delete(USER_ROLE, it -> {
            it.whereEq(USER_ID, userId);
            it.whereEq(ROLE_ID, roleId);
        });
    }

    UserRole findByUserId(long userId);

} 
