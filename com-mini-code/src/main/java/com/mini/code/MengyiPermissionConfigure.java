package com.mini.code;

import com.mini.code.impl.*;
import com.mini.code.impl.web.CodeController;
import com.mini.code.impl.web.CodeControllerPage;
import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.JdbcTemplateMysql;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.SQLException;

import static com.mini.code.impl.CodeDaoImpl.generator;
import static java.lang.String.format;

public class MengyiPermissionConfigure implements Configure {
    String PATH = "D:/workspace-git/UserAuth_Web/com-mengyi";

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
        return "com.mengyi.permission";
    }

    @Override
    public String getWebRootPath() {
        return format("%s/src/main/webapp/WEB-INF/permission", PATH);
    }

    @Override
    public String getDatabaseName() {
        return "mengyi";
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
                new BeanItem("PermissionRole", "permission_role", "role_"),
                new BeanItem("PermissionMenu", "permission_menu", "menu_"),
                new BeanItem("PermissionGroup", "permission_group", "group_"),
                new BeanItem("PermissionRoleUser", "permission_role_user", "role_user_"),
                new BeanItem("PermissionResource", "permission_resource", "resource_"),
                new BeanItem("PermissionRoleResource", "permission_role_resource", "role_resource_")
        };
    }

    public static void main(String[] args) throws Exception {
        Configure configure = new MengyiPermissionConfigure();
        for (BeanItem bean : configure.getDatabaseBeans()) {
            // 生成 Bean Mapper 代码生成
            CodeBase.generator(configure, bean, true);
            CodeBean.generator(configure, bean, true);
            CodeMapper.generator(configure, bean, true);

            // 生成 DAO Base Dao 与Dao Impl代码
            CodeDaoBase.generator(configure, bean, true);
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
