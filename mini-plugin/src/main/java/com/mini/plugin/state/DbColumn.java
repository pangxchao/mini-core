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
import static com.mini.plugin.extension.StringKt.firstUpperCase;
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
    private boolean id;
    private boolean auto;
    private boolean notNull;
    private boolean createdDate;
    private boolean createdBy;
    private boolean modifiedDate;
    private boolean modifiedBy;
    private boolean version;

    public DbColumn() {
    }

    public DbColumn(@NotNull com.intellij.database.model.DasColumn column) {
        databaseType = column.getDataType().typeName.toUpperCase();
        fieldName = toJavaName(column.getName(), false);
        id = DasUtil.isPrimary(column);
        auto = DasUtil.isAuto(column);
        notNull = column.isNotNull();
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

    public boolean isCreatedDate() {
        return createdDate;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setCreatedDate(boolean createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(boolean createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(boolean modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(boolean modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
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
        dbColumn.column = column;
        dbColumn.databaseType = databaseType;
        dbColumn.columnName = columnName;
        dbColumn.fieldName = fieldName;
        dbColumn.comment = comment;
        dbColumn.id = id;
        dbColumn.auto = auto;
        dbColumn.notNull = notNull;
        dbColumn.createdDate = createdDate;
        dbColumn.createdBy = createdBy;
        dbColumn.modifiedDate = modifiedDate;
        dbColumn.modifiedBy = modifiedBy;
        dbColumn.version = version;
        return dbColumn;
    }

    public void reset(@NotNull DbColumn column) {
        this.comment = column.comment;
        this.fieldName = column.fieldName;
        this.id = column.id;
        this.auto = column.auto;
        this.notNull = column.notNull;
        this.createdDate = column.createdDate;
        this.createdBy = column.createdBy;
        this.modifiedDate = column.modifiedDate;
        this.modifiedBy = column.modifiedBy;
        this.version = column.version;
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

    @Transient
    @JsonIgnore
    public String getJavaGetterName() {
        if ("Boolean".equals(getJavaType()) || "boolean".equals(getJavaType())) {
            if (getFieldName().startsWith("is")) {
                return getFieldName();
            }
            return "is" + firstUpperCase(getFieldName());
        }
        return "get" + firstUpperCase(getFieldName());
    }

    @Transient
    @JsonIgnore
    public String getJavaSetterName() {
        return "set" + firstUpperCase(getFieldName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DbColumn column1 = (DbColumn) o;
        return id == column1.id &&
                auto == column1.auto &&
                notNull == column1.notNull &&
                createdDate == column1.createdDate &&
                createdBy == column1.createdBy &&
                modifiedDate == column1.modifiedDate &&
                modifiedBy == column1.modifiedBy &&
                version == column1.version &&
                Objects.equals(column, column1.column) &&
                Objects.equals(databaseType, column1.databaseType) &&
                Objects.equals(columnName, column1.columnName) &&
                Objects.equals(fieldName, column1.fieldName) &&
                Objects.equals(comment, column1.comment);
    }

    @Override
    public int hashCode() {
        return hash(column, databaseType, columnName, fieldName, comment, id, auto, notNull, createdDate, createdBy, modifiedDate, modifiedBy, version);
    }
}
