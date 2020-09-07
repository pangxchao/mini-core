package com.mini.core.test.dao.base;

import com.mini.core.test.entity.UserInfo;

import com.mini.core.jdbc.builder.*;
import com.mini.core.jdbc.*;

public interface UserInfoBaseDao extends JdbcInterface {

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
            it.DELETE().FROM(UserInfo.user_info);
            it.WHERE(UserInfo.user_id + " = ?");
            it.ARGS(id);
        }));
    }

    default UserInfo queryById(long id) {
        return this.queryObject(new SQLBuilder(it -> {
            it.SELECT_FROM_JOIN(UserInfo.class);
            it.WHERE(UserInfo.user_id + " = ?");
            it.ARGS(id);
        }), UserInfo.class);
    }

} 
