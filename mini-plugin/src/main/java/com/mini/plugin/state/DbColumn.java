package com.mini.plugin.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.database.model.DasColumn;
import com.intellij.database.util.DasUtil;
import com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import static com.mini.plugin.config.Settings.instance;
import static com.mini.plugin.extension.StringKt.toJavaName;
import static java.util.Objects.hash;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public class DbColumn implements AbstractData<DbColumn>, Serializable {
    @Transient
    @JsonIgnore
    private com.intellij.database.model.DasColumn column;
    private String databaseType;
    private String columnName;
    private String fieldName;
    private String comment;
    private boolean isCreateAt;
    private boolean isUpdateAt;
    private boolean isId;
    private boolean isAuto;
    private boolean isNotNull;
    private boolean isLock;
    private boolean isDel;
    private int delValue = 1;

    public DbColumn() {
    }

    public DbColumn(@NotNull com.intellij.database.model.DasColumn column) {
        databaseType = column.getDataType().typeName.toUpperCase();
        fieldName = toJavaName(column.getName(), false);
        isId = DasUtil.isPrimary(column);
        isAuto = DasUtil.isAuto(column);
        isNotNull = column.isNotNull();
        comment = column.getComment();
        columnName = column.getName();
        this.column = column;
    }

    @Nullable
    @Transient
    @JsonIgnore
    public DasColumn getColumn() {
        return column;
    }

    public void setColumn(DasColumn column) {
        this.column = column;
    }

    @NotNull
    public String getDatabaseType() {
        return defaultIfBlank(databaseType, "VARCHAR");
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    @NotNull
    public String getColumnName() {
        return defaultIfBlank(columnName, "column_name");
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @NotNull
    public String getFieldName() {
        return defaultIfBlank(fieldName, "fieldName");
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @NotNull
    public String getComment() {
        return defaultIfBlank(comment, "");
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isCreateAt() {
        return isCreateAt;
    }

    public void setCreateAt(boolean createAt) {
        isCreateAt = createAt;
    }

    public boolean isUpdateAt() {
        return isUpdateAt;
    }

    public void setUpdateAt(boolean updateAt) {
        isUpdateAt = updateAt;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public void setAuto(boolean auto) {
        isAuto = auto;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public int getDelValue() {
        return delValue;
    }

    public void setDelValue(int delValue) {
        this.delValue = delValue;
    }

    @Override
    public void setName(String name) {
        setColumnName(name);
    }

    @NotNull
    @Override
    @Transient
    @JsonIgnore
    public String getName() {
        return getColumnName();
    }

    @Override
    public synchronized final @NotNull DbColumn copy() {
        final DbColumn dbColumn = new DbColumn();
        dbColumn.databaseType = databaseType;
        dbColumn.isUpdateAt = isUpdateAt;
        dbColumn.isCreateAt = isCreateAt;
        dbColumn.columnName = columnName;
        dbColumn.fieldName = fieldName;
        dbColumn.isNotNull = isNotNull;
        dbColumn.delValue = delValue;
        dbColumn.comment = comment;
        dbColumn.isLock = isLock;
        dbColumn.isAuto = isAuto;
        dbColumn.column = column;
        dbColumn.isId = isId;
        return dbColumn;
    }

    public void reset(@NotNull DbColumn column) {
        this.isCreateAt = column.isCreateAt;
        this.isUpdateAt = column.isUpdateAt;
        this.isNotNull = column.isNotNull;
        this.fieldName = column.fieldName;
        this.delValue = column.delValue;
        this.comment = column.comment;
        this.isAuto = column.isAuto;
        this.isLock = column.isLock;
        this.isId = column.isId;
    }

    @NotNull
    @Transient
    @JsonIgnore
    private DataType getDataType() {
        return Optional.of(instance.getDataTypeGroupMap())
                .map(it -> it.get(instance.getDataTypeGroupName()))
                .map(it -> it.get(getDatabaseType()))
                .orElse(new DataType());
    }

    private String getSourceType() {
        String type = getDataType().getJavaType();
        String[] types = type.split("[.]");
        return types[types.length - 1];
    }

    @Transient
    @JsonIgnore
    public String getKotlinType() {
        String type = getSourceType();
        return "Integer".equals(type)
                ? "Int" : type;
    }

    @Transient
    @JsonIgnore
    public String getJavaType() {
        String type = getSourceType();
        return "Int".equals(type) ?
                "Integer" : type;
    }

    @Transient
    @JsonIgnore
    public String getTypeImport() {
        return Optional.of(getDataType().getJavaType())
                .filter(it -> it.indexOf('.') > 0)
                .orElse("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DbColumn that = (DbColumn) o;
        return isCreateAt == that.isCreateAt &&
                isUpdateAt == that.isUpdateAt &&
                isId == that.isId &&
                isAuto == that.isAuto &&
                isNotNull == that.isNotNull &&
                isLock == that.isLock &&
                delValue == that.delValue &&
                Objects.equals(column, that.column) &&
                Objects.equals(databaseType, that.databaseType) &&
                Objects.equals(columnName, that.columnName) &&
                Objects.equals(fieldName, that.fieldName) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return hash(column, databaseType, columnName, fieldName, comment, isCreateAt, isUpdateAt, isId, isAuto, isNotNull, isLock, delValue);
    }
}
