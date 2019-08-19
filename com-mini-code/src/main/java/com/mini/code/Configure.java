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
        // 生成的所有类的包名
        public final String daoBasePackage;
        public final String daoImplPackage;
        public final String mapperPackage;
        public final String basePackage;
        public final String beanPackage;
        public final String daoPackage;

        // 生成的所有类的类名
        public final String daoBaseName;
        public final String daoImplName;
        public final String mapperName;
        public final String baseName;
        public final String beanName;
        public final String daoName;

        // 生成的所有的类的类对象
        public final ClassName daoBaseClass;
        public final ClassName daoImplClass;
        public final ClassName mapperClass;
        public final ClassName baseClass;
        public final ClassName beanClass;
        public final ClassName daoClass;

        // Web Controller 类相关包、类与类对象
        public final ClassName controllerClass;
        public final String controllerPackage;
        public final String controllerName;

        public ClassInfo(Configure configure, String className) throws RuntimeException {
            // 生成的所有类的包名
            mapperPackage  = format("%s.entity.mapper", configure.getPackageName());
            basePackage    = format("%s.entity.base", configure.getPackageName());
            daoBasePackage = format("%s.dao.base", configure.getPackageName());
            daoImplPackage = format("%s.dao.impl", configure.getPackageName());
            beanPackage    = format("%s.entity", configure.getPackageName());
            daoPackage     = format("%s.dao", configure.getPackageName());

            // 生成的所有类的类名
            daoBaseName = format("Base%sDao", className);
            daoImplName = format("%sDaoImpl", className);
            mapperName  = format("%sMapper", className);
            baseName    = format("Base%s", className);
            daoName     = format("%sDao", className);
            beanName    = className;

            // 生成所有的类对象
            daoBaseClass = get(daoBasePackage, daoBaseName);
            daoImplClass = get(daoImplPackage, daoImplName);
            mapperClass  = get(mapperPackage, mapperName);
            baseClass    = get(basePackage, baseName);
            beanClass    = get(beanPackage, beanName);
            daoClass     = get(daoPackage, daoName);

            // Web Controller 类相关包、类与类对象
            controllerPackage = format("%s.controller.back", configure.getPackageName());
            controllerName    = StringUtil.format("%sController", className);
            controllerClass   = get(controllerPackage, controllerName);
        }
    }
}
