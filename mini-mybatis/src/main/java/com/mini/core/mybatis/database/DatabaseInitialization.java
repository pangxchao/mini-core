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
            DatabaseInitialization.this.upgrade(0, 1);
            final int current = getCurrentVersion();
            final int version = getTargetVersion();
            // 升级数据库版本
            DatabaseInitialization.this.upgrade(1, version);
            for (final var databaseTable : databaseTableList) {
                databaseTable.upgrade(current, version);
            }
            // 保存目标版本号到数据库
            saveTargetVersion(version);
        }
        // 恢复外键检查
        finally {
            var checks = "SET FOREIGN_KEY_CHECKS = 1;";
            getJdbcTemplate().execute(checks);
        }
    }

    /**
     * 数据库升级
     *
     * @param currentVersion 当前数据库版本
     * @param targetVersion  升级目标版本
     */
    public abstract void upgrade(int currentVersion, int targetVersion);

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
     * 该方法会在配置表升级到版本“1”后执行
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
