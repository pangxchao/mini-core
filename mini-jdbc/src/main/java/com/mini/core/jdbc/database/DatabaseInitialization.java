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
    protected final List<DatabaseTable> databaseTableList;
    protected final MiniJdbcTemplate miniJdbcTemplate;
    private static final int ID = 1;

    public DatabaseInitialization(List<DatabaseTable> databaseTableList, MiniJdbcTemplate miniJdbcTemplate) {
        this.databaseTableList = databaseTableList;
        this.miniJdbcTemplate = miniJdbcTemplate;
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
     */
    @Transactional
    public void initialization(final int newVersion) {
        try {
            // 暂时禁用外键检查
            var checks = "SET FOREIGN_KEY_CHECKS = 0;";
            this.miniJdbcTemplate.execute(checks);
            // 如果表不存在时则创建表
            final String tName = this.getConfigTableName();
            if (!this.miniJdbcTemplate.hasTable(tName)) {
                this.createConfigTable();
            }
            // 升级其它数据库版本到新版本
            final int oldVersion = this.getOldVersion();
            for (DatabaseTable it : databaseTableList) {
                it.upgrade(oldVersion, newVersion);
            }
            // 保存新版本到数据库
            this.saveNewVersion(newVersion);
        }
        // 恢复外键检查
        finally {
            var checks = "SET FOREIGN_KEY_CHECKS = 1;";
            this.miniJdbcTemplate.execute(checks);
        }
    }

    /**
     * 数据库版本信息表创建表语句
     * <p>
     * 默认为Mysql实现
     * </p>
     */
    protected void createConfigTable() {
        miniJdbcTemplate.execute("\n" +
                "CREATE TABLE IF NOT EXISTS " + getConfigTableName() + "( \n " +
                "   " + getIdColumnName() + " INT NOT NULL PRIMARY KEY COMMENT '版本ID',\n" +
                "   " + getValueColumnName() + " INT NOT NULL COMMENT '数据库版本号' \n" +
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
        String string = format("REPLACE INTO %s(%s, %s) VALUES(?, ?)", getConfigTableName(), getIdColumnName(), getValueColumnName());
        this.miniJdbcTemplate.update(string, new Object[]{ID, newVersion});
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
        Integer value = this.miniJdbcTemplate.queryInt(string, new Object[]{ID});
        return value == null ? 0 : value;
    }
}
