package com.mini.core.test.repository;

import com.mini.core.jdbc.MiniRepository;
import com.mini.core.test.entity.UserRole;
import org.springframework.data.repository.*;

import javax.annotation.*;
import java.util.*;

import static java.util.Optional.ofNullable;
import static com.mini.core.test.entity.UserRole.*;

@org.springframework.stereotype.Repository("userRoleRepository")
public interface UserRoleRepository extends Repository<UserRole, Object>, MiniRepository {

    @Nonnull
    default Optional<UserRole> findById(Long userId, Long roleId) {
        return ofNullable(selectOne(UserRole.class, it -> {
            it.whereEq(USER_ID, userId);
            it.whereEq(ROLE_ID, roleId);
        }));
    }

    default boolean existsById(Long userId, Long roleId) {
        return findById(userId, roleId).isPresent();
    }

    default int deleteById(Long userId, Long roleId) {
        return this.delete(USER_ROLE, it -> {
            it.whereEq(USER_ID, userId);
            it.whereEq(ROLE_ID, roleId);
        });
    }

    UserRole findByUserId(long userId);

} 
