package com.mini.code;

import com.mini.dao.IDao;

import java.io.Serializable;
import java.sql.Date;
import java.sql.*;
import java.util.*;

import static com.mini.code.Config.DB_NAME;

public final class Util {
    public static class FieldInfo implements Serializable {
        private static final long serialVersionUID = -1L;

        private Class<?> typeClass;
        private boolean nonUnique;
        private String columnName;
        private String fieldName;
        private String remarks;
        private String keyName;
        private boolean auto;
        private int index;

        /**
         * 字段对应 JAVA 对象的属性类型
         * @return the value of typeClass
         */
        public Class<?> getTypeClass() {
            return typeClass;
        }

        /**
         * 字段对应 JAVA 对象的属性类型
         * @param typeClass the value of typeClass
         */
        public FieldInfo setTypeClass(Class<?> typeClass) {
            this.typeClass = typeClass;
            return this;
        }

        /**
         * 字段为索引时，索引是否忽略重复
         * @return the value of nonUnique
         */
        public boolean isNonUnique() {
            return nonUnique;
        }

        /**
         * 字段为索引时，索引是否忽略重复
         * @param nonUnique the value of nonUnique
         */
        public FieldInfo setNonUnique(boolean nonUnique) {
            this.nonUnique = nonUnique;
            return this;
        }

        /**
         * 字段对应数据库的全名
         * @return the value of columnName
         */
        public String getColumnName() {
            return columnName;
        }

        /**
         * 字段对应数据库的全名
         * @param columnName the value of columnName
         */
        public FieldInfo setColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        /**
         * 字段对应数据库名称简写
         * @return the value of fieldName
         */
        public String getFieldName() {
            return fieldName;
        }

        /**
         * 字段对应数据库名称简写
         * @param fieldName the value of fieldName
         */
        public FieldInfo setFieldName(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }


        /**
         * 字段说明
         * @return the value of remarks
         */
        public String getRemarks() {
            return remarks;
        }

        /**
         * 字段说明
         * @param remarks the value of remarks
         */
        public FieldInfo setRemarks(String remarks) {
            this.remarks = remarks;
            return this;
        }

        /**
         * 字段为索引时的索引名称
         * @return the value of keyName
         */
        public String getKeyName() {
            return keyName;
        }

        /**
         * 字段为索引时的索引名称
         * @param keyName the value of keyName
         */
        public FieldInfo setKeyName(String keyName) {
            this.keyName = keyName;
            return this;
        }

        /**
         * 字段是否为自动增长字段
         * @return the value of auto
         */
        public boolean isAuto() {
            return auto;
        }

        /**
         * 字段是否为自动增长字段
         * @param auto the value of auto
         */
        public FieldInfo setAuto(boolean auto) {
            this.auto = auto;
            return this;
        }

        /**
         * 字段为索引时的索引位置
         * @return the value of index
         */
        public int getIndex() {
            return index;
        }

        /**
         * 字段为索引时的索引位置
         * @param index the value of index
         */
        public FieldInfo setIndex(int index) {
            this.index = index;
            return this;
        }
    }

    public static class KeyIndexInfo implements Serializable {
        private static final long serialVersionUID = -1L;

        private String keyName;
        private boolean nonUnique;
        private final List<FieldInfo> children = new ArrayList<>();

        /**
         * Gets the value of keyName.
         * @return the value of keyName
         */
        public String getKeyName() {
            return keyName;
        }

        /**
         * Sets the value of keyName.
         * @param keyName the value of keyName
         */
        public KeyIndexInfo setKeyName(String keyName) {
            this.keyName = keyName;
            return this;
        }

        /**
         * Gets the value of nonUnique.
         * @return the value of nonUnique
         */
        public boolean isNonUnique() {
            return nonUnique;
        }

        /**
         * Sets the value of nonUnique.
         * @param nonUnique the value of nonUnique
         * @return KeyIndexInfo
         */
        public KeyIndexInfo setNonUnique(boolean nonUnique) {
            this.nonUnique = nonUnique;
            return this;
        }

        /**
         * Gets the value of children.
         * @return the value of children
         */
        public List<FieldInfo> getChildren() {
            return children;
        }

        /**
         * 添加一个字段信息
         * @param field 字段信息
         * @return 对象
         */
        public KeyIndexInfo addChild(FieldInfo field) {
            this.children.add(field);
            return this;
        }


    }

    private static final String REGEX = "((" + Config.PRE_NAME + ")(_)*)";
    private static final Map<String, Class<?>> TYPES = new HashMap<>() {
        private static final long serialVersionUID = -1L;

        {
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
            put("TIME", Time.class);
            put("DATETIME", Timestamp.class);
            put("TIMESTAMP", Timestamp.class);

            put("BLOB", Blob.class);
        }
    };

    /**
     * Gets the value of REGEX.
     * @return the value of REGEX
     */
    public static String getREGEX() {
        return REGEX;
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
    public static String toFieldName(String name) {
        return name.replaceFirst(REGEX, "");
    }

    /**
     * 获取指定表的创建表的SQL语句
     * @param dao 数据库连接
     * @return 创建表语句
     * @throws SQLException 读取错误
     */
    public static String getCreateTable(IDao dao) throws SQLException {
        return dao.queryOne("SHOW CREATE TABLE " + DB_NAME, (rs, number) -> rs.getString(2));
    }


    /**
     * 获取所有字段的信息
     * @param dao 数据库连接
     * @return 字段信息
     * @throws SQLException 读取错误
     */
    public static List<FieldInfo> getColumns(IDao dao) throws SQLException {
        try (ResultSet rs = dao.getMetaData().getColumns(null, null, DB_NAME, null)) {
            List<FieldInfo> columnList = new ArrayList<>();
            while (rs != null && rs.next()) {
                FieldInfo info = new FieldInfo();
                // 字段名称
                String COLUMN_NAME = rs.getString("COLUMN_NAME");
                String FIELD_NAME = toFieldName(COLUMN_NAME);
                info.setColumnName(COLUMN_NAME);
                info.setFieldName(FIELD_NAME);

                // 字段类型
                String TYPE_NAME = rs.getString("TYPE_NAME");
                Class<?> TYPE_CLASS = getTypes(TYPE_NAME);
                info.setTypeClass(TYPE_CLASS);

                // 是否为自增字段 YES/NO
                String AUTO = rs.getString("IS_AUTOINCREMENT");
                info.setAuto("YES".equals(AUTO));

                // 字段刘明
                String REMARKS = rs.getString("REMARKS");
                info.setRemarks(REMARKS);

                // 将字段加入列表
                columnList.add(info);
            }
            return columnList;
        }
    }

    private static FieldInfo getColumn(IDao dao, String columnName) throws SQLException {
        try (ResultSet rs = dao.getMetaData().getColumns(null, null, DB_NAME, columnName)) {
            if (rs != null && rs.next()) {
                FieldInfo info = new FieldInfo();

                // 字段名称
                String COLUMN_NAME = rs.getString("COLUMN_NAME");
                String FIELD_NAME = toFieldName(COLUMN_NAME);
                info.setColumnName(COLUMN_NAME);
                info.setFieldName(FIELD_NAME);

                // 字段类型
                String TYPE_NAME = rs.getString("TYPE_NAME");
                Class<?> TYPE_CLASS = getTypes(TYPE_NAME);
                info.setTypeClass(TYPE_CLASS);

                // 是否为自增字段 YES/NO
                String AUTO = rs.getString("IS_AUTOINCREMENT");
                info.setAuto("YES".equals(AUTO));

                // 字段刘明
                String REMARKS = rs.getString("REMARKS");
                info.setRemarks(REMARKS);

                return info;
            }
            return null;
        }
    }

    /**
     * 获取所有主键信息
     * @param dao 数据库连接
     * @return 主键信息
     * @throws SQLException 读取错误
     */
    public static List<FieldInfo> getPrimaryKey(IDao dao) throws SQLException {
        try (ResultSet rs = dao.getMetaData().getPrimaryKeys(null, null, DB_NAME)) {
            List<FieldInfo> columnList = new ArrayList<>();
            while (rs != null && rs.next()) {
                // 字段信息
                String COLUMN_NAME = rs.getString("COLUMN_NAME");
                FieldInfo field = getColumn(dao, COLUMN_NAME);
                if (field == null) continue;

                // 字段索引名称
                String KEY_NAME = rs.getString("PK_NAME");
                field.setKeyName(KEY_NAME);

                // 字段索引位置
                int index = rs.getInt("KEY_SEQ");
                field.setIndex(index);

                columnList.add(field);
            }
            columnList.sort(Comparator.comparingInt(v -> v.index));
            return columnList;
        }
    }

    /**
     * 获取该表指向其它表的所有外键
     * @param dao 数据库连接
     * @return 该表指向其它表的所有外键
     * @throws SQLException 读取错误
     */
    public static List<FieldInfo> getImportedKeys(IDao dao) throws SQLException {
        try (ResultSet rs = dao.getMetaData().getImportedKeys(null, null, DB_NAME)) {
            List<FieldInfo> columnList = new ArrayList<>();
            while (rs != null && rs.next()) {
                // 字段信息
                String COLUMN_NAME = rs.getString("COLUMN_NAME");
                FieldInfo field = getColumn(dao, COLUMN_NAME);
                if (field == null) continue;

                // 字段索引名称
                String KEY_NAME = rs.getString("PK_NAME");
                field.setKeyName(KEY_NAME);

                // 字段索引位置
                int index = rs.getInt("KEY_SEQ");
                field.setIndex(index);

                columnList.add(field);
            }
            columnList.sort(Comparator.comparingInt(v -> v.index));
            return columnList;
        }
    }

    /**
     * 获取所有的索引
     * @param dao 数据库连接
     * @return 索引信息
     * @throws SQLException 读取错误
     */
    public static List<FieldInfo> getIndexInfo(IDao dao) throws SQLException {
        try (ResultSet rs = dao.getMetaData().getIndexInfo(null, null, DB_NAME, false, false)) {
            List<FieldInfo> columnList = new ArrayList<>();
            while (rs != null && rs.next()) {
                // 字段信息
                String COLUMN_NAME = rs.getString("COLUMN_NAME");
                FieldInfo field = getColumn(dao, COLUMN_NAME);
                if (field == null) continue;

                // 字段索引名称
                String KEY_NAME = rs.getString("PK_NAME");
                field.setKeyName(KEY_NAME);

                // 字段索引位置
                int index = rs.getInt("KEY_SEQ");
                field.setIndex(index);

                // 索引是否检查重复
                boolean NON_UNIQUE = rs.getBoolean("NON_UNIQUE");
                field.setNonUnique(NON_UNIQUE);

                columnList.add(field);
            }
            columnList.sort(Comparator.comparing((FieldInfo v) -> v.keyName).thenComparingInt(v -> v.index));
            return columnList;
        }
    }


    /**
     * 获取所有索引信息（包括主键和外键）
     * @param dao 数据库连接
     * @return 所有索引信息
     * @throws SQLException 读取错误
     */
    //@SuppressWarnings("unchecked")
    public static List<KeyIndexInfo> getIndexList(IDao dao) throws SQLException {
        List<KeyIndexInfo> columnList = new ArrayList<>();
        for (FieldInfo field : getIndexInfo(dao)) {
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

            keyInfo.addChild(field);
            keyInfo.setKeyName(field.getKeyName());
            keyInfo.setNonUnique(field.isNonUnique());
        }
        return columnList;
    }
}
