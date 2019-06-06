package com.mini.code;

import com.mini.util.dao.IDao;
import com.mini.util.dao.implement.MysqlDao;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.DriverManager;

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
public class Config {
    // 模板文件根路径
    public static final String TEMPLATE_PATH = "D:/workspace-git/sn-mini/com-mini-code/src/main/resources";
    // CLASSPATH 根路径
    public static final String CLASS_PATH = "D:/workspace-git/sn-mini/com-mini-code/src/main/java";
    // 输出文档根路径
    public static final String DOCUMENT_PATH = "D:/workspace-git/sn-mini/com-mini-code/doc";
    // 输出文件所在包名称
    public static final String PACKAGE_NAME = "com.mini.out";

    // 数据库连接
    public static final String URL = "jdbc:mysql://192.168.1.200:3306/mengyi?useSSL=false";
    // 数据库密码
    public static final String PASSWORD = "Qwe123456!";
    // 数据库用户名
    public static final String USERNAME = "root";

    // 数据库表名称
    public static final String DB_NAME = "file_parameter";
    // 实体类名称
    public static final String JAVA_NAME = "InitInfo";
    // 表前缀名称
    public static final String PRE_NAME = "parameter_";

    /**
     * 获取数据库连接
     * @return 数据库连接
     */
    public static IDao getDao() throws Exception {
        Class.forName("com.mysql.jdbc.Driver"); // mysql 连接
        return new MysqlDao(DriverManager.getConnection(URL, USERNAME, PASSWORD));
    }

    public static void test(String a, String b, String c) {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method method = Config.class.getMethod("test", String.class, String.class, String.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getType());
            System.out.println(parameter.getName());
        }


    }
}
