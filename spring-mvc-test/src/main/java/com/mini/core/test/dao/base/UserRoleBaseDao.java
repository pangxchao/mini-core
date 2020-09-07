package com.mini.core.test.dao.base;

import com.mini.core.test.entity.UserRole;

import com.mini.core.jdbc.builder.*;
import com.mini.core.jdbc.*;

public interface UserRoleBaseDao extends JdbcInterface {

    default <T> int insertOnUpdate(T instance) {
        return this.execute(new SQLBuilder(it -> {
            it.ON_DUPLICATE_KEY_UPDATE(instance);
        }));
    }

    default <T> int replace(T instance) {
        return this.execute(new SQLBuilder(it -> {
            it.INSERT_INTO(instance);
        }));
    }

    default <T> int insert(T instance) {
        return this.execute(new SQLBuilder(it -> {
            it.INSERT_INTO(instance);
        }));
    }

    default <T> int delete(T instance) {
        return this.execute(new SQLBuilder(it -> {
            it.DELETE(instance);
        }));
    }

    default <T> int update(T instance) {
        return this.execute(new SQLBuilder(it -> {
            it.UPDATE(instance);
        }));
    }

    default int deleteById(long userId, long roleId) {
        return this.execute(new SQLBuilder(it -> {
            it.DELETE().FROM(UserRole.user_role);
            it.WHERE(UserRole.user_id + " = ?");
            it.ARGS(userId);
            it.WHERE(UserRole.role_id + " = ?");
            it.ARGS(roleId);
        }));
    }

    default UserRole queryById(long userId, long roleId) {
        return this.queryObject(new SQLBuilder(it -> {
            it.SELECT_FROM_JOIN(UserRole.class);
            it.WHERE(UserRole.user_id + " = ?");
            it.ARGS(userId);
            it.WHERE(UserRole.role_id + " = ?");
            it.ARGS(roleId);
        }), UserRole.class);
    }

} 
