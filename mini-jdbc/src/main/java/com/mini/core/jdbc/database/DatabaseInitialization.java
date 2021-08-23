package com.mini.core.jdbc.database;

import com.mini.core.jdbc.MiniJdbcTemplate;

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
     * 获取数据库操升级表列表信息
     *
     * @return 数据库操升级表列表信息
     */
    protected abstract List<DatabaseTable> getTableList();

    /**
     * 获取数据库操作对象
     *
     * @return 数据库操作对象
     */
    protected abstract MiniJdbcTemplate getJdbcTemplate();

    /**
     * 获取Value字段名称
     *
     * @return Value字段名称
     */
    protected String getConfigValueColumnName() {
        return "version_value";
    }

    /**
     * 获取ID字段名称
     *
     * @return ID字段名称
     */
    protected String getConfigIdColumnName() {
        return "version_id";
    }

    /**
     * 数据库版本配置表
     *
     * @return 数据库版本配置表
     */
    protected String getConfigTableName() {
        return "z_db_version";
    }

    /**
     * 数据库初始化程序
     *
     * @param newVersion 升级到目标版本
     */
    public final void initialization(final int newVersion) {
        try {
            // 暂时禁用外键检查
            var checks = "SET FOREIGN_KEY_CHECKS = 0;";
            this.getJdbcTemplate().execute(checks);
            // 如果表不存在时则创建表
            final String tName = getConfigTableName();
            if (!getJdbcTemplate().hasTable(tName)) {
                this.createConfigTable();
            }
            // 升级其它数据库版本到新版本
            final int oldVersion = getOldVersion();
            for (DatabaseTable it : getTableList()) {
                it.upgrade(oldVersion, newVersion);
            }
            // 保存新版本到数据库
            this.saveNewVersion(newVersion);
        }
        // 恢复外键检查
        finally {
            var checks = "SET FOREIGN_KEY_CHECKS = 1;";
            this.getJdbcTemplate().execute(checks);
        }
    }

    /**
     * 数据库版本信息表创建表语句
     * <p>
     * 默认为Mysql实现
     * </p>
     */
    protected void createConfigTable() {
        this.getJdbcTemplate().execute("\n" +
                "CREATE TABLE IF NOT EXISTS " + getConfigTableName() + "( \n " +
                "   " + getConfigIdColumnName() + " INT NOT NULL PRIMARY KEY COMMENT '版本ID',\n" +
                "   " + getConfigValueColumnName() + " INT NOT NULL COMMENT '数据库版本号' \n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;\n"
        );
    }

    /**
     * 保存目标版本号到数据库
     * <p>
     * 该方法会在数据库升级完成后执行
     * </P>
     *
     * @param newVersion 目标版本号
     */
    protected void saveNewVersion(int newVersion) {
        String string = format("REPLACE INTO %s(%s, %s) VALUES(?, ?)", getConfigTableName(),
                getConfigIdColumnName(), getConfigValueColumnName());
        this.getJdbcTemplate().update(string, new Object[]{ID, newVersion});
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
        String string = format("SELECT %s FROM %s WHERE %s = ?", getConfigValueColumnName(),
                getConfigTableName(), getConfigIdColumnName());
        Integer value = this.getJdbcTemplate().queryInt(string, new Object[]{ID});
        return value == null ? 0 : value;
    }
}
