package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniJdbcRepository;
import com.mini.core.test.entity.UserRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mini.core.test.entity.UserRole.ROLE_ID;
import static com.mini.core.test.entity.UserRole.USER_ID;
import static java.util.Optional.ofNullable;


@Repository("userRoleRepository")
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Object>, MiniJdbcRepository {
    default Optional<UserRole> findById(long userId, long roleId) {
        return ofNullable(selectOne(UserRole.class, it -> {
            it.whereEq(USER_ID, userId);
            it.whereEq(ROLE_ID, roleId);
        }));
    }

    default boolean existsById(long userId, long roleId) {
        return findById(userId, roleId).isPresent();
    }

    default void deleteById(long userId, long roleId) {
        this.delete(UserRole.USER_ROLE, it -> {
            it.whereEq(USER_ID, userId);
            it.whereEq(ROLE_ID, roleId);
        });
    }

    List<UserRole> findByUserId(long userId);

}
