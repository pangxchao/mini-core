package com.mini.code.util;

import com.mini.callback.DatabaseMetaDataCallback;
import com.mini.code.Configure;
import com.mini.jdbc.JdbcTemplate;
import com.mini.util.StringUtil;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.*;

import static com.mini.util.StringUtil.firstUpperCase;

public final class Util {
    public static class FieldInfo implements Serializable {
        private Class<?> typeClass;
        private boolean nonUnique;
        private String columnName;
        private String fieldName;
        private boolean nullable;
        private String remarks;
        private String keyName;
        private boolean auto;
        private int index;

        public Class<?> getTypeClass() {
            if (!this.nullable) {
                return typeClass;
            }
            if (typeClass == long.class) {
                return Long.class;
            }
            if (typeClass == int.class) {
                return Integer.class;
            }
            return typeClass;
        }

        public String getMapperGetName() {
            if (this.nullable && typeClass == long.class) {
                return "OLong";
            }
            return firstUpperCase(typeClass.getSimpleName());
        }

        public boolean isNonUnique() {
            return nonUnique;
        }

        public String getColumnName() {
            return columnName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public boolean isNullable() {
            return nullable;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getKeyName() {
            return keyName;
        }

        public boolean isAuto() {
            return auto;
        }

        public int getIndex() {
            return index;
        }
    }

    public static class KeyIndexInfo implements Serializable, EventListener {
        private final List<FieldInfo> children = new ArrayList<>();
        private boolean nonUnique;
        private String keyName;

        public String getKeyName() {
            return keyName;
        }

        public boolean isNonUnique() {
            return nonUnique;
        }

        public List<FieldInfo> getChildren() {
            return children;
        }
    }

    private static final Map<String, Class<?>> TYPES = new HashMap<>() {{
        put("MEDIUMTEXT", String.class);
        put("VARCHAR", String.class);
        put("CHAR", String.class);
        put("BINARY", String.class);
        put("TEXT", String.class);

        put("BIGINT", long.class);
        put("INT", int.class);
        put("SMALLINT", int.class);
        put("TINYINT", int.class);

        put("BOOL", boolean.class);
        put("BOOLEAN", boolean.class);

        put("DOUBLE", double.class);
        put("FLOAT", float.class);
        put("DECIMAL", double.class);

        put("DATE", Date.class);
        put("TIME", Date.class);
        put("DATETIME", Date.class);
        put("TIMESTAMP", Date.class);

        put("BLOB", Blob.class);
    }};

    /**
     * Gets the value of REGEX.
     * @return the value of REGEX
     */
    public static String getREGEX(String prefix) {
        return "((" + prefix + ")(_)*)";
    }

    /**
     * 获取数据类型
     * @param name 类型名称
     * @return Types 对象
     */
    public static Class<?> getTypes(String name) {
        return TYPES.getOrDefault(name, Object.class);
    }

    /**
     * 将数据库的名称转换成 JAVA 的名称
     * @param name 数据库名称
     * @return JAVA 名称
     */
    public static String toFieldName(String name, String prefix) {
        return name.replaceFirst(getREGEX(prefix), "");
    }

    /**
     * 获取指定表的创建表的SQL语句
     * @param jdbcTemplate 数据库连接
     * @return 创建表语句
     */
    public static String getCreateTable(JdbcTemplate jdbcTemplate, String tableName) {
        return jdbcTemplate.query("SHOW CREATE TABLE " + tableName, rs -> rs.next() ? rs.getString(2) : "");
    }

    /**
     * 获取所有字段的信息
     * @param jdbcTemplate 数据库连接
     * @return 字段信息
     */
    public static List<FieldInfo> getColumns(JdbcTemplate jdbcTemplate, String databaseName, String tableName, String prefix) {
        return jdbcTemplate.execute((DatabaseMetaDataCallback<List<FieldInfo>>) metaData -> {
            try (ResultSet rs = metaData.getColumns(databaseName, null, tableName, null)) {
                List<FieldInfo> columnList = new ArrayList<>();
                while (rs != null && rs.next()) {
                    FieldInfo info = new FieldInfo();
                    // 字段名称
                    String columnName = rs.getString("COLUMN_NAME");
                    String fieldName = toFieldName(columnName, prefix);
                    info.columnName = columnName;
                    info.fieldName  = fieldName;

                    // 字段类型
                    String typeName = rs.getString("TYPE_NAME");
                    info.typeClass = getTypes(typeName);

                    // 是否为自增字段 YES/NO
                    String auto = rs.getString("IS_AUTOINCREMENT");
                    info.auto = "YES".equalsIgnoreCase(auto);


                    // 字段是否可以为 Null
                    String nullable = rs.getString("IS_NULLABLE");
                    info.nullable = "YES".equalsIgnoreCase(nullable);

                    // 字段刘明
                    info.remarks = rs.getString("REMARKS");

                    // 将字段加入列表
                    columnList.add(info);
                }
                return columnList;
            }
        });
    }

    private static FieldInfo getColumn(JdbcTemplate jdbcTemplate, String databaseName, String tableName, String columnName, String prefix) {
        return jdbcTemplate.execute((DatabaseMetaDataCallback<FieldInfo>) metaData -> {
            try (ResultSet rs = metaData.getColumns(databaseName, null, tableName, columnName)) {
                if (rs != null && rs.next()) {
                    FieldInfo info = new FieldInfo();
                    // 字段名称
                    String fieldName = toFieldName(columnName, prefix);
                    info.columnName = columnName;
                    info.fieldName  = fieldName;

                    // 字段类型
                    String typeName = rs.getString("TYPE_NAME");
                    info.typeClass = getTypes(typeName);

                    // 是否为自增字段 YES/NO
                    String auto = rs.getString("IS_AUTOINCREMENT");
                    info.auto = "YES".equalsIgnoreCase(auto);


                    // 字段是否可以为 Null
                    String nullable = rs.getString("IS_NULLABLE");
                    info.nullable = "YES".equalsIgnoreCase(nullable);

                    // 字段刘明
                    info.remarks = rs.getString("REMARKS");

                    return info;
                }
                return null;
            }
        });
    }

    /**
     * 获取所有主键信息
     * @param jdbcTemplate 数据库连接
     * @param tableName    表名称
     * @return 主键信息
     */
    public static List<FieldInfo> getPrimaryKey(JdbcTemplate jdbcTemplate, String databaseName, String tableName, String prefix) {
        return jdbcTemplate.execute((DatabaseMetaDataCallback<List<FieldInfo>>) metaData -> {
            try (ResultSet rs = metaData.getPrimaryKeys(databaseName, null, tableName)) {
                List<FieldInfo> columnList = new ArrayList<>();
                while (rs != null && rs.next()) {
                    // 字段信息
                    String columnName = rs.getString("COLUMN_NAME");
                    FieldInfo field = getColumn(jdbcTemplate, databaseName, tableName, columnName, prefix);
                    if (field == null) continue;
                    // 字段索引名称
                    field.keyName = rs.getString("PK_NAME");
                    // 字段索引位置
                    field.index = rs.getInt("KEY_SEQ");

                    columnList.add(field);
                }
                columnList.sort(Comparator.comparingInt(v -> v.index));
                return columnList;
            }
        });
    }

    /**
     * 获取该表指向其它表的所有外键
     * @param jdbcTemplate 数据库连接
     * @return 该表指向其它表的所有外键
     */
    public static List<FieldInfo> getImportedKeys(JdbcTemplate jdbcTemplate, String databaseName, String tableName, String prefix) {
        return jdbcTemplate.execute((DatabaseMetaDataCallback<List<FieldInfo>>) metaData -> {
            try (ResultSet rs = metaData.getImportedKeys(databaseName, null, tableName)) {
                List<FieldInfo> columnList = new ArrayList<>();
                while (rs != null && rs.next()) {
                    // 字段信息
                    String columnName = rs.getString("FKCOLUMN_NAME");
                    FieldInfo field = getColumn(jdbcTemplate, databaseName, tableName, columnName, prefix);
                    if (field == null) continue;
                    // 字段索引名称
                    field.keyName = rs.getString("FK_NAME");
                    // 字段索引位置
                    field.index = rs.getInt("KEY_SEQ");

                    columnList.add(field);
                }
                columnList.sort(Comparator.comparingInt(v -> v.index));
                return columnList;
            }
        });
    }

    /**
     * 获取所有的索引
     * @param jdbcTemplate 数据库连接
     * @return 索引信息
     */
    public static List<FieldInfo> getIndexInfo(JdbcTemplate jdbcTemplate, String databaseName, String tableName, String prefix) {
        return jdbcTemplate.execute((DatabaseMetaDataCallback<List<FieldInfo>>) metaData -> {
            try (ResultSet rs = metaData.getIndexInfo(databaseName, null, tableName, false, false)) {
                List<FieldInfo> columnList = new ArrayList<>();
                while (rs != null && rs.next()) {
                    // 字段信息
                    String columnName = rs.getString("COLUMN_NAME");
                    FieldInfo field = getColumn(jdbcTemplate, databaseName, tableName, columnName, prefix);
                    if (field == null) continue;
                    // 索引是否检查重复
                    field.nonUnique = rs.getBoolean("NON_UNIQUE");
                    // 字段索引名称
                    field.keyName = rs.getString("PK_NAME");
                    // 字段索引位置
                    field.index = rs.getInt("KEY_SEQ");
                    columnList.add(field);
                }
                columnList.sort(Comparator.comparing((FieldInfo v) -> v.keyName).thenComparingInt(v -> v.index));
                return columnList;
            }
        });

    }

    /**
     * 获取所有索引信息（包括主键和外键）
     * @param jdbcTemplate 数据库连接
     * @return 所有索引信息
     */
    public static List<KeyIndexInfo> getIndexList(JdbcTemplate jdbcTemplate, String databaseName, String tableName, String prefix) {
        List<KeyIndexInfo> columnList = new ArrayList<>();
        for (FieldInfo field : getIndexInfo(jdbcTemplate, databaseName, tableName, prefix)) {
            KeyIndexInfo keyInfo = null;
            if (columnList.size() > 0) {
                KeyIndexInfo k = columnList.get(columnList.size() - 1);
                if (k.getKeyName() != null && k.getKeyName().equals(field.getKeyName())) {
                    keyInfo = k;
                }
            }

            if (keyInfo == null) {
                keyInfo = new KeyIndexInfo();
                columnList.add(keyInfo);
            }

            keyInfo.nonUnique = field.isNonUnique();
            keyInfo.keyName   = field.getKeyName();
            keyInfo.children.add(field);
        }
        return columnList;
    }

    /**
     * 验证配置中指定文件是否存在
     * @param configure   指定配置文件
     * @param packageName 指定文件包名
     * @param name        指定文件名
     * @return true-文件已经存在
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean exists(Configure configure, String packageName, String name) throws RuntimeException {
        Path outputDirectory = new File(configure.getClassPath()).toPath();
        for (String component : StringUtil.split(packageName, "\\.")) {
            outputDirectory = outputDirectory.resolve(component);
        }
        outputDirectory = outputDirectory.resolve(name + ".java");
        return outputDirectory.toFile().exists();
    }
}
