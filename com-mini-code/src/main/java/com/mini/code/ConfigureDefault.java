package com.mini.code;

import com.mini.code.impl.CodeBean;
import com.mini.code.impl.CodeDao;
import com.mini.code.impl.CodeDaoBase;
import com.mini.code.impl.Dictionaries;
import com.mini.code.impl.web.CodeController;
import com.mini.code.impl.web.CodeControllerPage;
import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.JdbcTemplateMysql;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.SQLException;

import static com.mini.code.impl.CodeDaoImpl.generator;
import static java.lang.String.format;

public class ConfigureDefault implements Configure {
    String PATH = "D:/workspace-git/mini-core/com-mini-mvc-test";

    @Override
    public String getClassPath() {
        return format("%s/src/main/java", PATH);
    }

    @Override
    public String getDocumentPath() {
        return format("%s/document", PATH);
    }

    @Override
    public String getPackageName() {
        return "com.mini.web.test";
    }

    @Override
    public String getWebRootPath() {
        return format("%s/src/main/webapp/WEB-INF", PATH);
    }

    @Override
    public String getDatabaseName() {
        return "mini_test";
    }

    @Override
    public JdbcTemplate getJdbcTemplate() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName(getDatabaseName());
        dataSource.setServerName(SERVER_NAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setUseSSL(false);
        dataSource.setUser("root");
        return new JdbcTemplateMysql(dataSource);
    }

    @Override
    public BeanItem[] getDatabaseBeans() {
        return new com.mini.code.Configure.BeanItem[]{
                new BeanItem("Region", "common_region", "region_"),
                new BeanItem("User", "user_info", "user_"),
        };
    }

    public static void main(String[] args) throws Exception {
        Configure configure = new ConfigureDefault();
        for (BeanItem bean : configure.getDatabaseBeans()) {
            // 生成 Bean Mapper Base 代码生成
            CodeBean.generator(configure, bean, true);
            CodeDaoBase.generator(configure, bean, true);

            // 生成 DAO  与Dao Impl代码
            CodeDao.generator(configure, bean, false);
            generator(configure, bean, false);

            // 生成控制器与页面相关代码
            CodeController.generator(configure, bean, false);
            CodeControllerPage.generator(configure, bean, false);
        }
        // 生成数据库文档
        Dictionaries.run(configure);
    }
}
