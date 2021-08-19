package com.mini.core.mybatis.database;

import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void initialization(final List<DatabaseTable> databaseTableList) {
        // 配置表初始化并获取当前数据库版本和升级目标版本
        DatabaseInitialization.this.createTable();
        final int current = getCurrentVersion();

        // 初始化数据库
        if (current < 1 && current < getTargetVersion()) {
            for (var databaseTable : databaseTableList) {
                databaseTable.createTable();
            }
        }

        // 升级数据库版本
        if (current < DatabaseInitialization.this.getTargetVersion()) {
            DatabaseInitialization.this.upgrade(current);
            for (var databaseTable : databaseTableList) {
                databaseTable.upgrade(current);
            }
        }

        // 保存目标版本号到数据库
        saveTargetVersion(getTargetVersion());
    }

    /**
     * 数据库初始化创建表结构
     * <ul>
     * <li>创建表时可以创建一些表的索引</li>
     * <li>因为顺序关系，外键关系需要在升级版本中创建</li>
     * </ul>
     */
    public abstract void createTable();

    /**
     * 数据库升级
     *
     * @param currentVersion 当前数据库版本
     */
    public abstract void upgrade(int currentVersion);

    /**
     * 保存目标版本号到数据库
     * <p>
     * 该方法会在数据库升级完成后执行
     * </P>
     *
     * @param targetVersion 目标版本号
     */
    public abstract void saveTargetVersion(int targetVersion);

    /**
     * 获取数据库的初始版本
     * <p>
     * 该方法会在创建表后执行，基础建表语句里面的字段信息
     * </P>
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
