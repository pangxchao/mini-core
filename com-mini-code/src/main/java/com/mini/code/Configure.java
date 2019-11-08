package com.mini.code;

import com.mini.code.util.Util;
import com.mini.jdbc.JdbcTemplate;
import com.mini.util.StringUtil;
import com.squareup.javapoet.ClassName;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static com.squareup.javapoet.ClassName.get;

public abstract class Configure implements EventListener {
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
    protected abstract String getPackageName();

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
    public abstract List<BeanItem> getDatabaseBeans();

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

    public static final class BeansBuilder implements EventListener {
        final List<BeanItem> beans = new ArrayList<>();

        private BeansBuilder() {}

        public static BeansBuilder newBuilder() {
            return new BeansBuilder();
        }

        public BeansBuilder addBeans(BeanItem bean) {
            this.beans.add(bean);
            return this;
        }

        public List<BeanItem> buid() {
            return beans;
        }
    }


    /**
     * 数据库与实体关联信息
     * @author xchao
     */
    public static final class BeanItem {
        private String packageName;
        public String className;
        public String tableName;
        public String prefix;

        private BeanItem(Builder builder) {
            packageName = builder.packageName;
            className   = builder.className;
            tableName   = builder.tableName;
            prefix      = builder.prefix;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder newBuilder(BeanItem copy) {
            Builder builder = new Builder();
            builder.packageName = copy.getPackageName();
            builder.className   = copy.getClassName();
            builder.tableName   = copy.getTableName();
            builder.prefix      = copy.getPrefix();
            return builder;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getClassName() {
            return className;
        }

        public String getTableName() {
            return tableName;
        }

        public String getPrefix() {
            return prefix;
        }


        public static final class Builder {
            private String packageName;
            private String className;
            private String tableName;
            private String prefix;

            private Builder() {}

            public Builder setPackageName(String val) {
                packageName = val;
                return this;
            }

            public Builder setClassName(String val) {
                className = val;
                return this;
            }

            public Builder setTableName(String val) {
                tableName = val;
                return this;
            }

            public Builder setPrefix(String val) {
                prefix = val;
                return this;
            }

            public BeanItem build() {
                return new BeanItem(this);
            }
        }
    }

    /**
     * 生成类相关信息
     * @author xchao
     */
    public static final class ClassInfo implements EventListener {
        private final List<Util.FieldInfo> FKFileList;
        private final List<Util.FieldInfo> PKFieldList;
        private final List<Util.FieldInfo> fieldList;
        private final BeanItem bean;
        private String packageName;


        public ClassInfo(Configure configure, BeanItem bean) throws SQLException {
            StringBuilder packageName = new StringBuilder();
            packageName.append(configure.getPackageName());
            this.packageName = configure.getPackageName();
            if (!StringUtil.isBlank(bean.packageName)) {
                if (!bean.packageName.startsWith(".")) {
                    packageName.append(".");
                }
                packageName.append(bean.packageName);
            }
            this.packageName = packageName.toString();
            this.bean        = bean;

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

        // Bean Name
        public final String getBeanName() {
            return bean.className;
        }

        // Bean Package
        public final String getBeanPackage() {
            return packageName + ".entity";
        }

        // Bean Class
        public final ClassName getBeanClass() {
            return get(getBeanPackage(), getBeanName());
        }

        // Builder Name
        public final String getBuilderName() {
            return "Builder";
        }

        // Builder Package
        public final String getBuilderPackage() {
            return packageName + ".entity";
        }

        // Builder Class
        public final ClassName getBuilderClass() {
            return get(getBuilderPackage(), getBeanName(), getBuilderName());
        }

        // SQL Name
        public final String getSQLName() {
            return bean.className + "Builder";
        }

        // SQL Package
        public final String getSQLPackage() {
            return packageName + ".entity";
        }

        // SQL Class
        public final ClassName getSQLClass() {
            return get(getSQLPackage(), getBeanName(), getSQLName());
        }

        // Dao Name
        public final String getDaoName() {
            return bean.className + "Dao";
        }

        // Dao Package
        public final String getDaoPackage() {
            return packageName + ".dao";
        }

        // Dao Class
        public final ClassName getDaoClass() {
            return get(getDaoPackage(), getDaoName());
        }

        // Base Dao Name
        public final String getBaseDaoName() {
            return bean.className + "BaseDao";
        }

        // Base Dao Package
        public final String getBaseDaoPackage() {
            return packageName + ".dao.base";
        }

        // Base Dao Class
        public final ClassName getBaseDaoClass() {
            return get(getBaseDaoPackage(), getBaseDaoName());
        }

        // Dao Impl Name
        public final String getDaoImplName() {
            return bean.className + "DaoImpl";
        }

        // Dao Impl Package
        public final String getDaoImplPackage() {
            return packageName + ".dao.impl";
        }

        // Dao Impl Class
        public final ClassName getDaoImplClass() {
            return get(getDaoImplPackage(), getDaoImplName());
        }

        public List<Util.FieldInfo> getFKFileList() {
            return FKFileList;
        }

        public List<Util.FieldInfo> getPKFieldList() {
            return PKFieldList;
        }

        public List<Util.FieldInfo> getFieldList() {
            return fieldList;
        }
    }
}
