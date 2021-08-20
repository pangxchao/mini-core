package com.mini.core.jdbc.database;

import com.mini.core.jdbc.MiniJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;

/**
 * 数据库初始化
 * <p>
 * 配置信息初始化，初始化时需要根据方法调用时机来确保配置信息表字段的存在
 * </p>
 *
 * @author pangchao
 */
public abstract class DatabaseInitialization {
    private static final int ID = 1;

    /**
     * 获取数据库操作类
     *
     * @return 数据库操作类
     */
    protected abstract MiniJdbcTemplate getJdbcTemplate();

    /**
     * 数据库版本配置表
     *
     * @return 数据库版本配置表
     */
    protected String getConfigTableName() {
        return "z_db_version";
    }

    /**
     * 获取Value字段名称
     *
     * @return Value字段名称
     */
    protected String getValueColumnName() {
        return "version_value";
    }

    /**
     * 获取ID字段名称
     *
     * @return ID字段名称
     */
    protected String getIdColumnName() {
        return "version_id";
    }

    /**
     * 数据库初始化程序
     *
     * @param databaseTableList 初始化表实现
     */

    @Transactional
    public void initialization(final List<DatabaseTable> databaseTableList) {
        String tableName = DatabaseInitialization.this.getConfigTableName();
        String valueName = DatabaseInitialization.this.getValueColumnName();
        String idName = DatabaseInitialization.this.getIdColumnName();

        // 如果表不存在时则创建表
        if (!this.getJdbcTemplate().hasTable(tableName)) {
            createConfigTable(tableName, idName, valueName);
        }

        try {
            // 暂时禁用外键检查
            var checks = "SET FOREIGN_KEY_CHECKS = 0;";
            getJdbcTemplate().execute(checks);
            final int oldVersion = getOldVersion();
            final int newVersion = getNewVersion();
            // 升级其它数据库版本到新版本
            for (final var databaseTable : databaseTableList) {
                databaseTable.upgrade(oldVersion, newVersion);
            }
            // 保存新版本
            saveNewVersion(newVersion);
        }
        // 恢复外键检查
        finally {
            var checks = "SET FOREIGN_KEY_CHECKS = 1;";
            getJdbcTemplate().execute(checks);
        }
    }

    /**
     * 数据库版本信息表创建表语句
     * <p>
     * 默认为Mysql实现
     * </p>
     *
     * @param tableName 版本表名称
     * @param idName    版本ID字段名称
     * @param valueName 版本号字段名称
     */
    protected void createConfigTable(String tableName, String idName, String valueName) {
        getJdbcTemplate().execute("\nCREATE TABLE IF NOT EXISTS " + tableName + "( \n " +
                "   " + idName + " INT NOT NULL PRIMARY KEY COMMENT '版本ID',\n" +
                "   " + valueName + " INT NOT NULL COMMENT '数据库版本号', \n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;\n"
        );
    }

    /**
     * 保存目标版本号到数据库
     * <p>
     * 该方法会在数据库升级完成后执行
     * </P>
     *
     * @param targetVersion 目标版本号
     */
    protected void saveNewVersion(int targetVersion) {
        String string = format("REPLACE INTO %s(%s, %s) VALUES(?, ?)", getConfigTableName(), getIdColumnName(), getValueColumnName());
        getJdbcTemplate().update(string, new Object[]{ID, targetVersion});
    }

    /**
     * 获取数据库的初始版本
     * <p>
     * 该方法会在配置表升级到版本“1”后执行
     * </P>
     *
     * @return 数据库初始版本
     */
    protected int getOldVersion() {
        String string = format("SELECT %s FROM %s WHERE %s = ?", getValueColumnName(), getConfigTableName(), getIdColumnName());
        Integer value = getJdbcTemplate().queryInt(string, new Object[]{ID});
        return value == null ? 0 : value;
    }

    /**
     * 获取数据库要升级的目标版本
     *
     * @return 数据库升级的目标版本
     */
    protected abstract int getNewVersion();


}
