package com.mini.core.mybatis.database;

/**
 * 数据库版本升级
 *
 * @author pangchao
 */
public interface DatabaseTable {

    /**
     * 数据库初始化创建表结构
     */
    void createTable();

    /**
     * 数据库升级
     *
     * @param currentVersion 当前数据库版本
     */
    void upgrade(int currentVersion);

}
