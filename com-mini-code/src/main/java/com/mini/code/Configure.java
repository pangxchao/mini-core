package com.mini.code;

import com.mini.jdbc.JdbcTemplate;

import java.sql.SQLException;

public interface Configure {
    String SERVER_NAME = "192.168.1.200";
    String PASSWORD = "Qwe123456!";

    /**
     * 获取项目基础路径
     * @return 项目基础路径
     */
    String getClassPath();

    /**
     * 获取文档基础路径
     * @return 文档基础路径
     */
    String getDocumentPath();

    /**
     * 获取基础包名
     * @return 基础包名
     */
    String getPackageName();

    /**
     * 获取数据库的库名
     * @return 数据库的库名
     */
    String getDatabaseName();

    /**
     * 获取数据库连接模板
     * @return 数据库连接模板
     */
    JdbcTemplate getJdbcTemplate() throws SQLException;

    /**
     * 数据表与实体类名
     * @return [BeanItem...]
     */
    BeanItem[] getDatabaseBeans();

    class BeanItem {
        public final String className;
        public final String tableName;
        public final String prefix;

        public BeanItem(String className, String tableName, String prefix) {
            this.className = className;
            this.tableName = tableName;
            this.prefix    = prefix;
        }
    }
}
