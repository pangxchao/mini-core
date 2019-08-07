package com.mini.code;

import com.mini.jdbc.JdbcTemplate;
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
        public final String serviceBasePackage;
        public final String serviceImplPackage;
        public final String daoBasePackage;
        public final String daoImplPackage;
        public final String servicePackage;
        public final String mapperPackage;
        public final String basePackage;
        public final String beanPackage;
        public final String daoPackage;

        // 生成的所有类的类名
        public final String serviceBaseName;
        public final String serviceImplName;
        public final String daoBaseName;
        public final String daoImplName;
        public final String serviceName;
        public final String mapperName;
        public final String baseName;
        public final String beanName;
        public final String daoName;

        // 生成的所有的类的类对象
        public final ClassName serviceBaseClass;
        public final ClassName serviceImplClass;
        public final ClassName daoBaseClass;
        public final ClassName daoImplClass;
        public final ClassName serviceClass;
        public final ClassName mapperClass;
        public final ClassName baseClass;
        public final ClassName beanClass;
        public final ClassName daoClass;

        // Web Controller 类相关包名
        public final String mobileControllerPackage;
        public final String frontControllerPackage;
        public final String backControllerPackage;

        // Web Controller 类相关类名
        public final String mobileControllerName;
        public final String frontControllerName;
        public final String backControllerName;

        // Web Controller 类相关类对象
        public final ClassName mobileControllerClass;
        public final ClassName frontControllerClass;
        public final ClassName backControllerClass;

        public ClassInfo(Configure configure, String className) throws RuntimeException {
            // 生成的所有类的包名
            serviceBasePackage = format("%s.service.base", configure.getPackageName());
            serviceImplPackage = format("%s.service.impl", configure.getPackageName());
            mapperPackage      = format("%s.entity.mapper", configure.getPackageName());
            basePackage        = format("%s.entity.base", configure.getPackageName());
            daoBasePackage     = format("%s.dao.base", configure.getPackageName());
            daoImplPackage     = format("%s.dao.impl", configure.getPackageName());
            servicePackage     = format("%s.service", configure.getPackageName());
            beanPackage        = format("%s.entity", configure.getPackageName());
            daoPackage         = format("%s.dao", configure.getPackageName());

            // 生成的所有类的类名
            serviceBaseName = format("Base%sService", className);
            serviceImplName = format("%sServiceImpl", className);
            daoBaseName     = format("Base%sDao", className);
            daoImplName     = format("%sDaoImpl", className);
            serviceName     = format("%sService", className);
            mapperName      = format("%sMapper", className);
            baseName        = format("Base%s", className);
            daoName         = format("%sDao", className);
            beanName        = className;

            // 生成所有的类对象
            serviceBaseClass = get(serviceBasePackage, serviceBaseName);
            serviceImplClass = get(serviceImplPackage, serviceImplName);
            daoBaseClass     = get(daoBasePackage, daoBaseName);
            daoImplClass     = get(daoImplPackage, daoImplName);
            serviceClass     = get(servicePackage, serviceName);
            mapperClass      = get(mapperPackage, mapperName);
            baseClass        = get(basePackage, baseName);
            beanClass        = get(beanPackage, beanName);
            daoClass         = get(daoPackage, daoName);

            // Web Controller 类相关包名
            mobileControllerPackage = format("%s.controller.mobile", configure.getPackageName());
            frontControllerPackage  = format("%s.controller.front", configure.getPackageName());
            backControllerPackage   = format("%s.controller.back", configure.getPackageName());

            // Web Controller 类相关类名
            mobileControllerName = format("%sController", className);
            frontControllerName  = format("%sController", className);
            backControllerName   = format("%sController", className);

            // Web Controller 类相关类对象
            mobileControllerClass = get(mobileControllerPackage, mobileControllerName);
            frontControllerClass  = get(frontControllerPackage, frontControllerName);
            backControllerClass   = get(backControllerPackage, backControllerName);
        }
    }
}
