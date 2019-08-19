package com.mini.code;

import com.mini.code.impl.*;
import com.mini.code.impl.web.CodeControllerBack;
import com.mini.code.impl.web.CodeControllerBackPage;
import com.mini.code.impl.web.CodeControllerFront;
import com.mini.code.impl.web.CodeControllerMobile;
import com.mini.jdbc.JdbcTemplate;
import com.mini.jdbc.JdbcTemplateMysql;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.SQLException;

import static java.lang.String.format;

public class MengyiUsersConfigure implements Configure {
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
        return "com.mengyi.users";
    }

    @Override
    public String getWebRootPath() {
        return format("%s/src/main/webapp/WEB-INF/users", PATH);
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
                new BeanItem("User", "user_info", "user_"),
                new BeanItem("Card", "user_card", "card_"),
                new BeanItem("Order", "user_order", "order_"),
                new BeanItem("UserDevice", "user_device", "device_"),
                new BeanItem("LoginLog", "user_login_log", "login_"),
                new BeanItem("UserCloud", "user_cloud", "user_cloud_"),
                new BeanItem("UserSession", "user_session", "session_"),
                new BeanItem("VipDetail", "user_vip_detail", "vip_detail_")
        };
    }

    public static void main(String[] args) throws Exception {
        Configure configure = new MengyiUsersConfigure();
        for (BeanItem bean : configure.getDatabaseBeans()) {
            // 生成 Bean Mapper 代码生成
            // 该代码生成完成后一般不需要修改
            // 数据库表修改时，重新生成覆盖即可
            beanGenerator(configure, bean);

            // 生成 Dao 与 Service Base接口相关代码
            // 该代码生成数据库的基础查询操作和实现
            // 数据库表修改时，重新生成覆盖即可
            baseGenerator(configure, bean);

            // 生成 Dao 与 Service 接口代码生成
            // 该代码创建一个用户自定义方法的接口
            // 第一次生成完成后下次不再生成
            interGenerator(configure, bean);

            // 生成 Dao 与 Service Impl 代码生成
            // 该代码生成一次基础的注入配置信息
            // 第一次生成完成后下次不再生成
            implGenerator(configure, bean);

            // 生成Web相关代码
            // 该代码用于初始时创建相关文件
            // 第一次生成之后下次不再生成
            webGenerator(configure, bean);
        }
        // 生成数据库文档
        Dictionaries.run(configure);
    }

    // Bean Mapper 代码生成
    private static void beanGenerator(Configure configure, BeanItem bean) throws Exception {
        CodeBase.generator(configure, bean, true);
        CodeBean.generator(configure, bean, true);
        CodeMapper.generator(configure, bean, true);
    }

    // 生成  Dao 与 Service  Base 相关代码
    private static void baseGenerator(Configure configure, BeanItem bean) throws Exception {
        CodeDaoBase.generator(configure, bean, true);
        CodeServiceBase.generator(configure, bean, true);
    }

    // 生成 Dao 与 Service 接口 代码生成
    private static void interGenerator(Configure configure, BeanItem bean) throws Exception {
        CodeDao.generator(configure, bean, false);
        CodeService.generator(configure, bean, false);
    }

    // 生成 Dao 与 Service Impl 代码生成
    private static void implGenerator(Configure configure, BeanItem bean) throws Exception {
        CodeDaoImpl.generator(configure, bean, false);
        CodeServiceImpl.generator(configure, bean, false);
    }

    // 生成Web相关代码
    private static void webGenerator(Configure configure, BeanItem bean) throws Exception {
        CodeControllerBack.generator(configure, bean, false);
        CodeControllerFront.generator(configure, bean, false);
        CodeControllerMobile.generator(configure, bean, false);
        CodeControllerBackPage.generator(configure, bean, false);
    }
}
