package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import static java.lang.String.format;

public final class Creations {
    /**
     * 生成代码
     * @param configure 数据库与实体配置信息
     */
    public static void generator(Configure configure) throws Exception {
        File file = new File(configure.getDocumentPath());
        if (!file.exists() && file.mkdirs()) {
            System.out.println("创建文件夹成功");
        }
        String fileName = StringUtil.replace(configure.getPackageName(), '.', '-');
        try (OutputStreamWriter writer = new FileWriter(new File(file, fileName + "-create.sql"))) {
            writer.write(format("CREATE DATABASE IF NOT EXISTS %s ", configure.getDatabaseName()));
            writer.write("DEFAULT CHARACTER SET utf8mb4; \n");
            writer.write(format("USE %s; \n", configure.getDatabaseName()));
            writer.write("\n");
            for (Configure.BeanItem beanItem : configure.getDatabaseBeans()) {
                String sql = format("SHOW CREATE TABLE %s", beanItem.tableName);
                String content = configure.getJdbcTemplate().query(sql, rs -> {
                    return rs.next() ? rs.getString(2) : ""; //
                });
                writer.write(StringUtil.replace(content, "`", ""));
                writer.write("\n");
                writer.write("\n");
            }
        }
    }

}
