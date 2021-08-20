package com.mini.core.jdbc.database;

/**
 * 数据库版本升级
 *
 * @author pangchao
 */
public interface DatabaseTable {

    /**
     * 数据库升级
     *
     * @param oldVersion 数据库数据旧版本
     * @param newVersion 升级目标新版本
     */
    void upgrade(int oldVersion, int newVersion);

}
