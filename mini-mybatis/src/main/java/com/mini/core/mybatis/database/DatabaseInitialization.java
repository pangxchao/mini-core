package com.mini.core.mybatis.database;

import java.util.List;

/**
 * 数据库初始化
 *
 * @author pangchao
 */
public abstract class DatabaseInitialization {

    /**
     * 数据库初始化程序
     *
     * @param databaseTableList 初始化表实现
     */
    public final void initialization(final List<DatabaseTable> databaseTableList) {
        int current = getCurrentVersion(), targetVersion = getTargetVersion();

        // 初始化数据库
        DatabaseInitialization.this.initialization(current, targetVersion);
        for (final DatabaseTable databaseTable : databaseTableList) {
            databaseTable.initialization(current, targetVersion);
        }

        // 升级数据库版本
        DatabaseInitialization.this.upgrade(current, targetVersion);
        for (DatabaseTable databaseTable : databaseTableList) {
            databaseTable.upgrade(current, targetVersion);
        }

        // 保存目标版本号到数据库
        saveTargetVersion(targetVersion);
    }

    /**
     * 数据库初始化创建表结构
     * <p>
     * 创建表时可以创建一些表的索引
     * </p>
     *
     * @param currentVersion 当前数据库版本
     * @param targetVersion  升级到目标版本
     */
    public abstract void initialization(int currentVersion, int targetVersion);

    /**
     * 数据库升级
     *
     * @param currentVersion 当前数据库版本
     * @param targetVersion  升级到目标版本
     */
    public abstract void upgrade(int currentVersion, int targetVersion);

    /**
     * 保存目标版本号到数据库
     *
     * @param targetVersion 目标版本号
     */
    public abstract void saveTargetVersion(int targetVersion);

    /**
     * 获取数据库的初始版本
     *
     * @return 数据库初始版本
     */
    public abstract int getCurrentVersion();

    /**
     * 获取数据库要升级的目标版本
     *
     * @return 数据库升级的目标版本
     */
    public abstract int getTargetVersion();


}
