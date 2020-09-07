package com.mini.core.test.dao.base;

import com.mini.core.test.entity.RoleInfo;

import com.mini.core.jdbc.builder.*;
import com.mini.core.jdbc.*;

public interface RoleInfoBaseDao extends JdbcInterface {

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

    default int deleteById(long id) {
        return this.execute(new SQLBuilder(it -> {
            it.DELETE().FROM(RoleInfo.role_info);
            it.WHERE(RoleInfo.role_id + " = ?");
            it.ARGS(id);
        }));
    }

    default RoleInfo queryById(long id) {
        return this.queryObject(new SQLBuilder(it -> {
            it.SELECT_FROM_JOIN(RoleInfo.class);
            it.WHERE(RoleInfo.role_id + " = ?");
            it.ARGS(id);
        }), RoleInfo.class);
    }

} 
