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
            getJdbcTemplate().execute("SET FOREIGN_KEY_CHECKS = 0");

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
        } finally {
            // 恢复外键检查
            getJdbcTemplate().execute("SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    /**
     * 数据库初始化创建表结构
     */
    protected abstract void createTable();

    /**
     * 数据库升级
     *
     * @param currentVersion 当前数据库版本
     */
    protected abstract void upgrade(int currentVersion);

    /**
     * 保存目标版本号到数据库
     * <p>
     * 该方法会在数据库升级完成后执行
     * </P>
     *
     * @param targetVersion 目标版本号
     */
    protected abstract void saveTargetVersion(int targetVersion);

    /**
     * 获取数据库的初始版本
     * <p>
     * 该方法会在创建表后执行，基础建表语句里面的字段信息
     * </P>
     *
     * @return 数据库初始版本
     */
    protected abstract int getCurrentVersion();

    /**
     * 获取数据库要升级的目标版本
     *
     * @return 数据库升级的目标版本
     */
    protected abstract int getTargetVersion();


}
