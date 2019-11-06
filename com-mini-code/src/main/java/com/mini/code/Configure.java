package com.mini.code;

import com.mini.code.util.Util;
import com.mini.jdbc.JdbcTemplate;
import com.mini.util.StringUtil;
import com.squareup.javapoet.ClassName;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.EventListener;
import java.util.List;

import static com.squareup.javapoet.ClassName.get;
import static java.lang.String.format;

public abstract class Configure implements EventListener {
    private List<Util.FieldInfo> PKFieldList;
    private List<Util.FieldInfo> FKFieldList;
    private List<Util.FieldInfo> fieldList;
    String SERVER_NAME = "192.168.1.200";
    String PASSWORD = "Qwe123456!";

    public boolean generatorSerialVersionUID() {
        return false;
    }

    /**
     * 获取项目基础路径
     * @return 项目基础路径
     */
    public abstract String getClassPath();

    /**
     * 获取文档基础路径
     * @return 文档基础路径
     */
    public abstract String getDocumentPath();

    /**
     * 获取基础包名
     * @return 基础包名
     */
    public abstract String getPackageName();

    /**
     * 获取数据库的库名
     * @return 数据库的库名
     */
    public abstract String getDatabaseName();

    /**
     * 获取数据库连接模板
     * @return 数据库连接模板
     */
    public abstract JdbcTemplate getJdbcTemplate() throws SQLException;

    /**
     * 数据表与实体类名
     * @return [BeanItem...]
     */
    public abstract BeanItem[] getDatabaseBeans();

    /**
     * 验证配置中指定文件是否存在
     * @param packageName 指定文件包名
     * @param name        指定文件名
     * @return true-文件已经存在
     */
    public final synchronized boolean exists(String packageName, String name) {
        Path outputDirectory = new File(this.getClassPath()).toPath();
        for (String component : StringUtil.split(packageName, "\\.")) {
            outputDirectory = outputDirectory.resolve(component);
        }
        outputDirectory = outputDirectory.resolve(name + ".java");
        return outputDirectory.toFile().exists();
    }

    /**
     * 数据库与实体关联信息
     * @author xchao
     */
    public static final class BeanItem {
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
    public static final class ClassInfo {
        // Dao 相关的包名秘与类名、类对象
        public final String daoPackage, daoBasePackage, daoImplPackage;
        public final ClassName daoClass, daoBaseClass, daoImplClass;
        public final String daoName, daoBaseName, daoImplName;

        // 实体信息相关包与类名、类对象
        public final String beanPackage, builderPackage, sqlPackage;
        public final ClassName beanClass, builderClass, sqlClass;
        public final String beanName, builderName, sqlName;

        public final List<Util.FieldInfo> FKFileList, PKFieldList, fieldList;

        public ClassInfo(Configure configure, BeanItem bean) throws SQLException {

            // 实体信息相关包与类名、类对象初始化 - Bean 对象
            beanPackage = format("%s.entity", configure.getPackageName());
            beanName    = bean.className;
            beanClass   = get(beanPackage, beanName);

            // 实体信息相关包与类名、类对象初始化 - Builder 对象
            builderPackage = format("%s.entity", configure.getPackageName());
            builderName    = "Builder";
            builderClass   = get(builderPackage, beanName, builderName);

            // Mapper/SQL相关包名与类名、类对象 - SQL 对象
            sqlPackage = format("%s.entity", configure.getPackageName());
            sqlName    = format("%sBuilder", bean.className);
            sqlClass   = get(sqlPackage, beanName, sqlName);

            // Dao 相关的包名秘与类名、类对象 - DaoBase 对象
            daoBasePackage = format("%s.dao.base", configure.getPackageName());
            daoBaseName    = format("Base%sDao", bean.className);
            daoBaseClass   = get(daoBasePackage, daoBaseName);

            // Dao 相关的包名秘与类名、类对象 - Dao 对象
            daoPackage = format("%s.dao", configure.getPackageName());
            daoName    = format("%sDao", bean.className);
            daoClass   = get(daoPackage, daoName);

            // Dao 相关的包名秘与类名、类对象 - Impl 对象
            daoImplPackage = format("%s.dao.impl", configure.getPackageName());
            daoImplName    = format("%sDaoImpl", bean.className);
            daoImplClass   = get(daoImplPackage, daoImplName);

            // 获取当前表外键信息
            FKFileList = Util.getImportedKeys(configure.getJdbcTemplate(), //
                    configure.getDatabaseName(), bean.tableName, bean.prefix);

            // 获取当前表主键信息
            PKFieldList = Util.getPrimaryKey(configure.getJdbcTemplate(), //
                    configure.getDatabaseName(), bean.tableName, bean.prefix);  //

            // 获取当前表的所有字段信息
            fieldList = Util.getColumns(configure.getJdbcTemplate(), //
                    configure.getDatabaseName(), bean.tableName, bean.prefix); //
        }
    }
}
