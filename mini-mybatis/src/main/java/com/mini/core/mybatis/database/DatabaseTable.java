package com.mini.core.mybatis.database;

import org.springframework.beans.factory.InitializingBean;

/**
 * 数据库版本升级
 *
 * @author pangchao
 */
public interface DatabaseTable extends InitializingBean {

    /**
     * 数据库升级
     *
     * @param currentVersion 当前数据库版本
     * @param targetVersion  升级到目标版本
     */
    void upgrade(int currentVersion, int targetVersion);

    @Override
    default void afterPropertiesSet() {

    }
}
