/**
 * Created the com.cfinal.gen.GENConfig.java
 *
 * @created 2017年7月26日 下午3:12:07
 * @version 1.0.0
 */
package sn.mini.java.gen;

import sn.mini.java.jdbc.IDao;
import sn.mini.java.jdbc.Paging;
import sn.mini.java.jdbc.implement.MysqlDao;
import sn.mini.java.util.digest.MD5Util;

import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 使用说明：<br/>
 * 1.如果需要使用cfinal框架和cfinal代码生成器需要注意数据库命名规则<br/>
 * &nbsp;&nbsp;1).所有表之间的字段名称不能重复<br/>
 * &nbsp;&nbsp;2).每个表的字段都有相同的前缀名称 用‘_’下划线分隔<br/>
 * 2.使用代码生成器生成的只有实体代码和简单的dao操作代码 <br/>
 * 3.生成时修改下面的常量参数，代码会直接写入常量配置的路径中<br/>
 * 4.导出数据字典和sql代码时，默认在项目的‘documents’目录中
 * 5. version updater text
 *
 * @author XChao
 */
public class GENConfig {
    // alter table knowledge_base CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

//  public static final String DB_URL = "jdbc:mysql://192.168.1.200:3306/mengyi_file_cloud?characterEncoding=utf-8"; // 数据库连接
//  public static final String DB_URL = "jdbc:mysql://192.168.1.200:3306/mengyi_common?characterEncoding=utf-8"; // 数据库连接
//  public static final String DB_URL = "jdbc:mysql://192.168.1.200:3306/mengyi_group?characterEncoding=utf-8"; // 数据库连接
//  public static final String DB_URL = "jdbc:mysql://192.168.1.200:3306/mengyi_maker?characterEncoding=utf-8"; // 数据库连接
//  public static final String DB_URL = "jdbc:mysql://192.168.1.200:3306/mengyi_knowledge?characterEncoding=utf-8"; // 数据库连接
    public static final String DB_URL = "jdbc:mysql://192.168.1.200:3306/mengyi_users?characterEncoding=utf-8"; // 数据库连接
//  public static final String DB_URL = "jdbc:mysql://192.168.1.200:3306/mengyi_update?characterEncoding=utf-8"; // 数据库连接

    public static final String DB_USERNAME = "root"; // 数据库用户名
    public static final String DB_PASSWORD = "Qwe123456!"; // 数据库密码

    public static final String SOURCES_NAME = "src/main/java"; // java源文件目录， 一般默认为src， 也有自定义的
    public static final String PROJECT_PATH = "D:/WorkGit/sn-mini/sn.mini.java.gen"; // 项目根绝对路径
    public static final String PACKAGE_NAME = "sn.mini.kotlin.gen"; // 项目名名称

    public static final Paging PAGING = new Paging(1, 100); // 生成sql时，每张表获取前100条记录为初始数据
    public static final String TABLE_DB_NAME = "user_session"; // 数据库表名称
    public static final String TABLE_JAVA_NAME = "UserSession"; // java实体类名称
    public static final String DB_PREFIX_NAME = "session_"; // 表前缀名称， 如： file_id 字段名称


    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static IDao getDao() throws SQLException, Exception {
        Class.forName("com.mysql.jdbc.Driver"); // mysql 连接
        return new MysqlDao(DriverManager.getConnection(DB_URL, //
                DB_USERNAME, DB_PASSWORD));
    }

    public static class A {
    }

    public static class B extends A {
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("11001测试节点10er35@s7$"));
    }
}
