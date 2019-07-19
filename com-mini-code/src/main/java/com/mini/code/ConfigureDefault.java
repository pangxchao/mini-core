package com.mini.code;

import com.mini.code.impl.*;
import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.JdbcTemplateMysql;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.SQLException;

import static java.lang.String.format;

public class ConfigureDefault implements Configure {
    String PATH = "D:/workspace-git/UserAuth_Web/com-mini-mvc-test";

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
    public String getDatabaseName() {
        return "mini-test";
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
        return new BeanItem[]{
                new BeanItem("Init", "room_parameter", "parameter_"),
                new BeanItem("Resource", "room_resource", "resource_"),
                new BeanItem("Reserve", "room_reserve", "reserve_"),
        };
    }

    public static void main(String[] args) throws Exception {
        Configure configure = new ConfigureDefault();
        for (BeanItem bean : configure.getDatabaseBeans()) {
            // Bean 代码生成
            CodeBase.generator(configure, bean);
            CodeBean.generator(configure, bean);

            // Mapper 代码生成
            CodeMapper.generator(configure, bean);

            // Dao 代码生成
            CodeDao.generator(configure, bean);
            CodeDaoImpl.generator(configure, bean);

            // Service 代码生成
            CodeService.generator(configure, bean);
            CodeServiceImpl.generator(configure, bean);

        }
        // 生成数据库文档
        Dictionaries.run(configure);
    }
}
