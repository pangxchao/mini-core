package com.mini.code;

import com.mini.jdbc.JdbcTemplate;
import com.mini.util.StringUtil;
import com.squareup.javapoet.ClassName;

import java.sql.SQLException;

import static com.squareup.javapoet.ClassName.get;
import static java.lang.String.format;

public interface Configure {
    String SERVER_NAME = "192.168.1.200";
    String PASSWORD = "Qwe123456!";

    default boolean generatorSerialVersionUID() {
        return false;
    }

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
     * 获取WEB页面的绝对路径
     * @return WEB页面的绝对路径
     */
    String getWebRootPath();

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

    /**
     * 数据库与实体关联信息
     * @author xchao
     */
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

    /**
     * 生成类相关信息
     * @author xchao
     */
    final class ClassInfo {
        // 实体信息相关包与类名、类对象
        public final String beanPackage, builderPackage, abstractBuilderPackage;
        public final ClassName beanClass, builderClass, abstractBuilderClass;
        public final String beanName, builderName, abstractBuilderName;

        // Dao 相关的包名秘与类名、类对象
        public final String daoPackage, daoBasePackage, daoImplPackage;
        public final ClassName daoClass, daoBaseClass, daoImplClass;
        public final String daoName, daoBaseName, daoImplName;

        // Mapper/SQL相关包名与类名、类对象
        public final String mapperPackage, sqlPackage;
        public final ClassName mapperClass, sqlClass;
        public final String mapperName, sqlName;

        // Web Controller 类相关包、类与类对象
        public final ClassName controllerClass;
        public final String controllerPackage;
        public final String controllerName;

        public ClassInfo(Configure configure, String className) throws RuntimeException {
            // 实体信息相关包与类名、类对象初始化 - Bean 对象
            beanPackage = format("%s.entity", configure.getPackageName());
            beanName    = className;
            beanClass   = get(beanPackage, beanName);

            // 实体信息相关包与类名、类对象初始化 - AbstractBuilder 对象
            abstractBuilderPackage = format("%s.entity", configure.getPackageName());
            abstractBuilderName    = "AbstractBuilder";
            abstractBuilderClass   = get(abstractBuilderPackage, beanName, abstractBuilderName);

            // 实体信息相关包与类名、类对象初始化 - Builder 对象
            builderPackage = format("%s.entity", configure.getPackageName());
            builderName    = "Builder";
            builderClass   = get(builderPackage, beanName, builderName);

            // Mapper/SQL相关包名与类名、类对象 - Mapper 对象
            mapperPackage = format("%s.entity.mapper", configure.getPackageName());
            mapperName    = format("%sMapper", className);
            mapperClass   = get(mapperPackage, mapperName);

            // Mapper/SQL相关包名与类名、类对象 - SQL 对象
            sqlPackage = format("%s.entity.mapper", configure.getPackageName());
            sqlName    = format("%sBuilder", className);
            sqlClass   = get(sqlPackage, mapperName, sqlName);

            // Dao 相关的包名秘与类名、类对象 - DaoBase 对象
            daoBasePackage = format("%s.dao.base", configure.getPackageName());
            daoBaseName    = format("Base%sDao", className);
            daoBaseClass   = get(daoBasePackage, daoBaseName);

            // Dao 相关的包名秘与类名、类对象 - Dao 对象
            daoPackage = format("%s.dao", configure.getPackageName());
            daoName    = format("%sDao", className);
            daoClass   = get(daoPackage, daoName);

            // Dao 相关的包名秘与类名、类对象 - Impl 对象
            daoImplPackage = format("%s.dao.impl", configure.getPackageName());
            daoImplName    = format("%sDaoImpl", className);
            daoImplClass   = get(daoImplPackage, daoImplName);

            // Web Controller 类相关包、类与类对象
            controllerPackage = format("%s.controller.back", configure.getPackageName());
            controllerName    = StringUtil.format("%sController", className);
            controllerClass   = get(controllerPackage, controllerName);
        }
    }
}
