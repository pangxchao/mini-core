package com.mini.core.jdbc.database;

import com.mini.core.jdbc.MiniJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static java.lang.String.format;

/**
 * 数据库初始化
 * <p>
 * 配置信息初始化，初始化时需要根据方法调用时机来确保配置信息表字段的存在
 * </p>
 *
 * @author pangchao
 */
@Component
public abstract class DatabaseInitialization {
    private TransactionTemplate transactionTemplate;
    private List<UpgradeTable> databaseTableList;
    private MiniJdbcTemplate miniJdbcTemplate;
    private static final int ID = 1;

    @Autowired
    public final void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Autowired
    public final void setDatabaseTableList(List<UpgradeTable> databaseTableList) {
        this.databaseTableList = databaseTableList;
    }

    @Autowired
    public final void setMiniJdbcTemplate(MiniJdbcTemplate miniJdbcTemplate) {
        this.miniJdbcTemplate = miniJdbcTemplate;
    }

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
     * <p>
     * 该方法内部会为每个版本开启一个事务调用时外面不需要事务
     * </p>
     *
     * @param newVersion 升级到目标版本
     */
    public final void initialization(final int newVersion) {
        Objects.requireNonNull(transactionTemplate);
        Objects.requireNonNull(databaseTableList);
        Objects.requireNonNull(miniJdbcTemplate);
        try {
            // 暂时禁用外键检查
            var checks = "SET FOREIGN_KEY_CHECKS = 0;";
            this.miniJdbcTemplate.execute(checks);
            // 如果表不存在时则创建表
            final String tName = getConfigTableName();
            if (!miniJdbcTemplate.hasTable(tName)) {
                this.createConfigTable();
            }
            // 升级其它数据库版本到新版本
            for (int version = getOldVersion() + 1; version <= newVersion; version++) {
                // 升级结构
                final int finalVersion = version;
                this.databaseTableList.forEach(databaseTable -> databaseTable.upgrade(new UpgradeCallback() {
                    public boolean upgradeStructureToVersion(int version, Consumer<MiniJdbcTemplate> consumer) {
                        if (finalVersion != version) return false;
                        consumer.accept(miniJdbcTemplate);
                        return true;
                    }
                }));

                // 升级数据
                transactionTemplate.execute(transactionStatus -> {
                    this.databaseTableList.forEach(databaseTable -> databaseTable.upgrade(new UpgradeCallback() {
                        public boolean upgradeDataToVersion(int version, Consumer<MiniJdbcTemplate> consumer) {
                            if (finalVersion != version) return false;
                            consumer.accept(miniJdbcTemplate);
                            return true;
                        }
                    }));
                    // 保存升级后的版本
                    saveNewVersion(finalVersion);
                    return null;
                });
            }
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
        this.miniJdbcTemplate.execute("\n" +
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
        String string = format("REPLACE INTO %s(%s, %s) VALUES(?, ?)", getConfigTableName(), getConfigIdColumnName(), getConfigValueColumnName());
        this.miniJdbcTemplate.update(string, ID, newVersion);
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
        String string = format("SELECT %s FROM %s WHERE %s = ?", getConfigValueColumnName(), getConfigTableName(), getConfigIdColumnName());
        return this.miniJdbcTemplate.queryInt(string, ID).orElse(0);
    }
}
