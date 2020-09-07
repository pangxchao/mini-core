package com.mini.core.test.dao.base;

import com.mini.core.test.entity.RegionInfo;

import com.mini.core.jdbc.builder.*;
import com.mini.core.jdbc.*;

public interface RegionInfoBaseDao extends JdbcInterface {

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
            it.DELETE().FROM(RegionInfo.region_info);
            it.WHERE(RegionInfo.region_id + " = ?");
            it.ARGS(id);
        }));
    }

    default RegionInfo queryById(long id) {
        return this.queryObject(new SQLBuilder(it -> {
            it.SELECT_FROM_JOIN(RegionInfo.class);
            it.WHERE(RegionInfo.region_id + " = ?");
            it.ARGS(id);
        }), RegionInfo.class);
    }

} 
