package com.mini.core.mybatis.database;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据库初始化
 * <p>
 * 配置信息初始化，初始化时需要根据方法调用时机来确保配置信息表字段的存在
 * </p>
 *
 * @author pangchao
 */
public abstract class DatabaseInitialization {

    /**
     * 获取数据库操作类
     *
     * @return 数据库操作类
     */
    protected abstract JdbcTemplate getJdbcTemplate();

    /**
     * 数据库初始化程序
     *
     * @param databaseTableList 初始化表实现
     */

    @Transactional
    public void initialization(final List<DatabaseTable> databaseTableList) {
        try {
            // 暂时禁用外键检查
            var checks = "SET FOREIGN_KEY_CHECKS = 0;";
            getJdbcTemplate().execute(checks);
            // 配置表初始化并获取当前数据库版本和升级目标版本
            DatabaseInitialization.this.createTable();
            final int oldVersion = getOldVersion();
            final int newVersion = getNewVersion();
            // 升级其它数据库版本到新版本
            for (final var databaseTable : databaseTableList) {
                databaseTable.upgrade(oldVersion, newVersion);
            }
            // 升级配置表并保存新版本到配置信息
            upgrade(oldVersion, newVersion);
            saveNewVersion(newVersion);
        }
        // 恢复外键检查
        finally {
            var checks = "SET FOREIGN_KEY_CHECKS = 1;";
            getJdbcTemplate().execute(checks);
        }
    }

    /**
     * 创建数据库
     * <p>
     * 配置表每次启动都会执行数据库创建，所以创建时需要检查表是否存在
     * </P>
     */
    public abstract void createTable();

    /**
     * 数据库升级
     *
     * @param oldVersion 数据库数据旧版本
     * @param newVersion 升级目标新版本
     */
    public abstract void upgrade(int oldVersion, int newVersion);

    /**
     * 保存目标版本号到数据库
     * <p>
     * 该方法会在数据库升级完成后执行
     * </P>
     *
     * @param targetVersion 目标版本号
     */
    public abstract void saveNewVersion(int targetVersion);

    /**
     * 获取数据库的初始版本
     * <p>
     * 该方法会在配置表升级到版本“1”后执行
     * </P>
     *
     * @return 数据库初始版本
     */
    public abstract int getOldVersion();

    /**
     * 获取数据库要升级的目标版本
     *
     * @return 数据库升级的目标版本
     */
    public abstract int getNewVersion();


}
