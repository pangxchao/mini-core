package com.mini.plugin.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.database.model.DasColumn;
import com.mini.plugin.util.ClassUtil;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 数据库连接信息-字段
 *
 * @author xchao
 */
@SuppressWarnings("ALL")
public final class ColumnInfo extends AbstractClone<ColumnInfo> implements Serializable {
    private String databaseType;
    private String columnName;
    private String fieldName;
    private boolean createAt;
    private boolean updateAt;
    @JsonIgnore
    private DasColumn column;
    private boolean notNull;
    private String comment;
    private int delValue;
    private boolean auto;
    private boolean lock;
    private boolean del;
    private boolean id;

    public ColumnInfo setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
        return this;
    }

    public ColumnInfo setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public ColumnInfo setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ColumnInfo setCreateAt(boolean createAt) {
        this.createAt = createAt;
        return this;
    }

    public ColumnInfo setUpdateAt(boolean updateAt) {
        this.updateAt = updateAt;
        return this;
    }

    public ColumnInfo setColumn(DasColumn column) {
        this.column = column;
        return this;
    }

    public ColumnInfo setNotNull(boolean notNull) {
        this.notNull = notNull;
        return this;
    }

    public ColumnInfo setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public ColumnInfo setDelValue(int delValue) {
        this.delValue = delValue;
        return this;
    }

    public ColumnInfo setAuto(boolean auto) {
        this.auto = auto;
        return this;
    }

    public ColumnInfo setLock(boolean lock) {
        this.lock = lock;
        return this;
    }

    public ColumnInfo setDel(boolean del) {
        this.del = del;
        return this;
    }

    public ColumnInfo setId(boolean id) {
        this.id = id;
        return this;
    }

    @NotNull
    public final synchronized String getDatabaseType() {
        if (StringUtil.isEmpty(databaseType)) {
            databaseType = "VARCHAR";
        }
        return databaseType.toUpperCase();
    }

    @NotNull
    public final synchronized String getColumnName() {
        if (StringUtil.isEmpty(columnName)) {
            columnName = "ColumnName";
        }
        return columnName;
    }

    @NotNull
    public final synchronized String getFieldName() {
        if (StringUtil.isEmpty(fieldName)) {
            fieldName = "fieldName";
        }
        return fieldName;
    }

    @NotNull
    public final synchronized String getComment() {
        if (StringUtil.isEmpty(comment)) {
            comment = "Comment";
        }
        return comment;
    }

    public boolean isCreateAt() {
        return createAt;
    }

    public boolean isUpdateAt() {
        return updateAt;
    }

    @Nullable
    public DasColumn getColumn() {
        return column;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public int getDelValue() {
        return delValue;
    }

    public boolean isAuto() {
        return auto;
    }

    public boolean isLock() {
        return lock;
    }

    public boolean isDel() {
        return del;
    }

    public boolean isId() {
        return id;
    }

    @NotNull
    @Override
    public final synchronized ColumnInfo clone() {
        final ColumnInfo info = new ColumnInfo();
        info.setDatabaseType(databaseType);
        info.setColumnName(columnName);
        info.setFieldName(fieldName);
        info.setCreateAt(createAt);
        info.setUpdateAt(updateAt);
        info.setDelValue(delValue);
        info.setComment(comment);
        info.setNotNull(notNull);
        info.setColumn(column);
        info.setAuto(auto);
        info.setLock(lock);
        info.setDel(del);
        info.setId(id);
        return info;
    }


    @SuppressWarnings("unchecked")
    public DataType getDataType() {
        return Optional.of(Settings.getInstance())
                .map(Settings::getDataTypeGroup)
                .map(group -> group.getDataType(getDatabaseType()))
                .orElseThrow();
    }

    @SuppressWarnings("unchecked")
    public Class<?> getType() {
        return Optional.of(Settings.getInstance())
                .map(Settings::getDataTypeGroup)
                .map(group -> group.getDataType(getDatabaseType()))
                .map(m -> isNotNull() ? m.getJavaType() : m.getNullJavaType())
                .map(ClassUtil::forName)
                .orElse((Class) Object.class);
    }
}
