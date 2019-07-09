package com.mini.code;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.String.format;

/**
 * <p>使用说明：</p>
 * <p>1.如果需要使用 sn.mini 框架和 sn.mini 代码生成器需要注意数据库命名规则 </p>
 * <p>&nbsp;&nbsp;1).所有表之间的字段名称不能重复 </p>
 * <p>&nbsp;&nbsp;2).每个表的字段都有相同的前缀名称 用‘_’下划线分隔 </p>
 * <p>2.使用代码生成器生成的只有实体代码和简单的dao操作代码  </p>
 * <p>3.生成时修改下面的常量参数，代码会直接写入常量配置的路径中 </p>
 * <p>4.导出数据字典和sql代码时，默认在项目的‘documents’目录中 </p>
 * <p>5. version updater text
 * @author XChao
 */
@Component
public final class Config {
    private static final String SERVER_NAME = "192.168.1.200";
    private static final String PASSWORD = "Qwe123456!";


    /** 获取配置信息 */
    public static Configure getConfigure() {
        //return new MengyiCommonConfigure();
        return new MengyiCloudConfigure();
    }

    public static class MengyiCommonConfigure implements Configure {
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
            return "com.mengyi.common";
        }

        @Override
        public String getDatabaseName() {
            return "mengyi";
        }

        @Override
        public Connection getConnection() throws SQLException {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setDatabaseName(getDatabaseName());
            dataSource.setServerName(SERVER_NAME);
            dataSource.setPassword(PASSWORD);
            dataSource.setUseSSL(false);
            dataSource.setUser("root");
            return dataSource.getConnection();
        }

        @Override
        public String[][] getDatabaseBeans() {
            return new String[][]{
                    {"Init", "common_parameter", "parameter_"},
                    {"Cloud", "common_cloud", "cloud_"},
                    {"Spread", "common_spread", "spread_"},
                    {"CodePhone", "common_code_phone", "code_phone_"},
                    {"CodeEmail", "common_code_email", "code_email_"},
                    {"DeleteIn", "common_delete_in", "delete_in_"},
                    {"DeleteOut", "common_delete_out", "delete_out_"},
                    {"SearchWords", "common_search_words", "search_words_"},
                    {"Region", "common_region", "region_"},
                    {"Unit", "common_unit", "unit_"},
                    {"SendPhone", "common_send_phone", "send_phone_"},
                    {"SendEmail", "common_send_email", "send_email_"},
                    {"TipOff", "common_tip_off", "tip_off_"},
            };
        }
    }

    /**
     * Mengyi_Cloud 数据库配置信息
     * @author xchao
     */
    public static class MengyiCloudConfigure implements Configure {
        String PATH = "D:/workspace-git/UserAuth_Web/com-mengyi-cloud";

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
            return "com.mengyi.cloud";
        }

        @Override
        public String getDatabaseName() {
            return "mengyi_cloud";
        }

        @Override
        public Connection getConnection() throws SQLException {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setDatabaseName(getDatabaseName());
            dataSource.setServerName(SERVER_NAME);
            dataSource.setPassword(PASSWORD);
            dataSource.setUseSSL(false);
            dataSource.setUser("root");
            return dataSource.getConnection();
        }

        @Override
        public String[][] getDatabaseBeans() {
            return new String[][]{
                    {"Init", "file_parameter", "parameter_"},
                    {"Detailed", "file_detailed", "detailed_"},
                    {"DeleteQueue", "file_delete_queue", "delete_queue"},
            };
        }
    }
}
