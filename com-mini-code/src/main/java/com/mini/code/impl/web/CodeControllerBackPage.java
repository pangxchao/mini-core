package com.mini.code.impl.web;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.io.Files.createParentDirs;
import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static com.mini.util.StringUtil.firstLowerCase;
import static com.mini.util.StringUtil.toJavaName;
import static freemarker.template.Configuration.VERSION_2_3_28;
import static java.lang.String.format;

public class CodeControllerBackPage {
    private static final Configuration config = new Configuration(VERSION_2_3_28);

    private static boolean index_exists(Configure configure, String beanName) throws RuntimeException, IOException {
        String indexPath = format("back/%s/index.ftl", firstLowerCase(beanName));
        File file = new File(configure.getWebRootPath(), indexPath);

        // 创建父目录
        createParentDirs(file);
        return file.isFile() && file.exists();
    }

    private static boolean pages_exists(Configure configure, String beanName) throws RuntimeException, IOException {
        String indexPath = format("back/%s/pages.ftl", firstLowerCase(beanName));
        File file = new File(configure.getWebRootPath(), indexPath);
        // 创建父目录
        createParentDirs(file);
        return file.isFile() && file.exists();
    }

    private static boolean insert_exists(Configure configure, String beanName) throws RuntimeException, IOException {
        String indexPath = format("back/%s/insert.ftl", firstLowerCase(beanName));
        File file = new File(configure.getWebRootPath(), indexPath);
        // 创建父目录
        createParentDirs(file);
        return file.isFile() && file.exists();
    }

    private static boolean update_exists(Configure configure, String beanName) throws RuntimeException, IOException {
        String indexPath = format("back/%s/update.ftl", firstLowerCase(beanName));
        File file = new File(configure.getWebRootPath(), indexPath);
        // 创建父目录
        createParentDirs(file);
        return file.isFile() && file.exists();
    }

    /**
     * 生成index.ftl文件
     * @param configure   数据库与实体配置信息
     * @param info        所有类的信息
     * @param tableName   数据库表名
     * @param prefix      字段名前缀
     * @param fieldList   所有字段信息
     * @param pkFieldList 主键字段信息
     */
    protected static void index(Configure configure, ClassInfo info, String tableName, String prefix, //
            List<Util.FieldInfo> fieldList, List<Util.FieldInfo> pkFieldList) throws Exception {
        String indexPath = String.format("back/%s/index.ftl", firstLowerCase(info.beanName));
        try (Writer out = new FileWriter(new File(configure.getWebRootPath(), indexPath))) {
            ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
            data.put("name", firstLowerCase(info.beanName));

            // 生成页面
            getTemplate("index").process(data, out);
        }

        System.out.println("====================================");
        System.out.println("Back Page Index : " + info.beanName + "\r\n");
    }

    /**
     * 生成pages.ftl文件
     * @param configure   数据库与实体配置信息
     * @param info        所有类的信息
     * @param tableName   数据库表名
     * @param prefix      字段名前缀
     * @param fieldList   所有字段信息
     * @param pkFieldList 主键字段信息
     */
    protected static void pages(Configure configure, ClassInfo info, String tableName, String prefix, //
            List<Util.FieldInfo> fieldList, List<Util.FieldInfo> pkFieldList) throws Exception {
        String indexPath = String.format("back/%s/pages.ftl", firstLowerCase(info.beanName));
        try (Writer out = new FileWriter(new File(configure.getWebRootPath(), indexPath))) {
            ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
            data.put("name", firstLowerCase(info.beanName));
            data.put("fieldList", fieldList.stream().map(f -> {
                Map<String, Object> m = new HashMap<>();
                String name = toJavaName(f.getFieldName(), false);
                m.put("name", firstLowerCase(name));
                m.put("remarks", f.getRemarks());
                return m;
            }).toArray());

            // 生成页面
            getTemplate("pages").process(data, out);
        }

        System.out.println("====================================");
        System.out.println("Back Page Pages : " + info.beanName + "\r\n");
    }

    /**
     * 生成insert.ftl文件
     * @param configure   数据库与实体配置信息
     * @param info        所有类的信息
     * @param tableName   数据库表名
     * @param prefix      字段名前缀
     * @param fieldList   所有字段信息
     * @param pkFieldList 主键字段信息
     */
    protected static void insert(Configure configure, ClassInfo info, String tableName, String prefix, //
            List<Util.FieldInfo> fieldList, List<Util.FieldInfo> pkFieldList) throws Exception {
        String indexPath = String.format("back/%s/insert.ftl", firstLowerCase(info.beanName));
        try (Writer out = new FileWriter(new File(configure.getWebRootPath(), indexPath))) {
            ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
            data.put("name", firstLowerCase(info.beanName));
            data.put("fieldList", fieldList.stream().map(f -> {
                Map<String, Object> m = new HashMap<>();
                String name = toJavaName(f.getFieldName(), false);
                m.put("name", firstLowerCase(name));
                m.put("remarks", f.getRemarks());
                return m;
            }).toArray());

            // 生成页面
            getTemplate("insert").process(data, out);
        }

        System.out.println("====================================");
        System.out.println("Back Page Insert : " + info.beanName + "\r\n");
    }

    /**
     * 生成update.ftl文件
     * @param configure   数据库与实体配置信息
     * @param info        所有类的信息
     * @param tableName   数据库表名
     * @param prefix      字段名前缀
     * @param fieldList   所有字段信息
     * @param pkFieldList 主键字段信息
     */
    protected static void update(Configure configure, ClassInfo info, String tableName, String prefix, //
            List<Util.FieldInfo> fieldList, List<Util.FieldInfo> pkFieldList) throws Exception {
        String indexPath = String.format("back/%s/update.ftl", firstLowerCase(info.beanName));
        try (Writer out = new FileWriter(new File(configure.getWebRootPath(), indexPath))) {
            ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<>();
            data.put("name", firstLowerCase(info.beanName));
            data.put("fieldList", fieldList.stream().map(f -> {
                Map<String, Object> m = new HashMap<>();
                String name = toJavaName(f.getFieldName(), false);
                m.put("name", firstLowerCase(name));
                m.put("remarks", f.getRemarks());
                return m;
            }).toArray());

            // 生成页面
            getTemplate("update").process(data, out);
        }

        System.out.println("====================================");
        System.out.println("Back Page Update : " + info.beanName + "\r\n");
    }


    /**
     * 获取  Template 对象
     * @param viewPath 模板路径
     * @return Template 对象
     */
    protected static Template getTemplate(String viewPath) throws IOException {
        return config.getTemplate(viewPath + ".ftl");
    }


    /**
     * 生成java代码
     * @param configure 数据库与实体配置信息
     * @param bean      数据库表与实体关联配置
     * @param isCover   是否覆盖已存在的文件
     */
    public static void generator(Configure configure, Configure.BeanItem bean, boolean isCover) throws Exception {
        List<Util.FieldInfo> pkFieldList = getPrimaryKey(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix);  //
        List<Util.FieldInfo> fieldList = getColumns(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix); //
        config.setClassForTemplateLoading(CodeControllerBackPage.class, "/back/");
        ClassInfo info = new ClassInfo(configure, bean.className);

        // 生成index.ftl文件
        if (isCover || !CodeControllerBackPage.index_exists(configure, info.beanName)) {
            index(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }

        // 生成pages.ftl文件
        if (isCover || !CodeControllerBackPage.pages_exists(configure, info.beanName)) {
            pages(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }

        // 生成insert.ftl文件
        if (isCover || !CodeControllerBackPage.insert_exists(configure, info.beanName)) {
            insert(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }

        // 生成update.ftl文件
        if (isCover || !CodeControllerBackPage.update_exists(configure, info.beanName)) {
            update(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }
    }
}
