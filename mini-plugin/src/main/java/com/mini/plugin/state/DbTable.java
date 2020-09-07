package com.mini.plugin.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

import static com.mini.plugin.extension.StringKt.toJavaName;
import static java.util.Arrays.deepEquals;
import static java.util.Objects.hash;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public class DbTable implements AbstractData<DbTable>, Serializable {
    private LinkedHashMap<String, DbColumn> columnMap;
    @Transient
    @JsonIgnore
    private com.intellij.database.psi.DbTable table;
    private String packageName;
    private String namePrefix;
    private String entityName;
    private String tableName;
    private String comment;

    public DbTable() {
    }

    public DbTable(@NotNull com.intellij.database.psi.DbTable table) {
        this.entityName = toJavaName(table.getName(), true);
        this.comment = table.getComment();
        this.tableName = table.getName();
        this.table = table;
    }

    @Nullable
    @Transient
    public com.intellij.database.psi.DbTable getTable() {
        return table;
    }

    public void setTable(com.intellij.database.psi.DbTable table) {
        this.table = table;
    }

    @NotNull
    public synchronized LinkedHashMap<String, DbColumn> getColumnMap() {
        if (DbTable.this.columnMap == null) {
            columnMap = new LinkedHashMap<>();
        }
        return columnMap;
    }

    public void setColumnMap(LinkedHashMap<String, DbColumn> columnMap) {
        this.columnMap = columnMap;
    }

    @NotNull
    public Collection<DbColumn> getColumnList() {
        return getColumnMap().values();
    }

    public void addColumn(@NotNull DbColumn column) {
        getColumnMap().put(column.getName(), column);
    }

    @NotNull
    public String getPackageName() {
        return defaultIfBlank(packageName, "");
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @NotNull
    public String getNamePrefix() {
        return defaultIfBlank(namePrefix, "");
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @NotNull
    public String getEntityName() {
        return defaultIfBlank(entityName, "EntityName");
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @NotNull
    public String getTableName() {
        return defaultIfBlank(tableName, "table_name");
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @NotNull
    public String getComment() {
        return defaultIfBlank(comment, "");
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @NotNull
    @Override
    public synchronized DbTable copy() {
        DbTable dbTable = new DbTable();
        dbTable.columnMap = new LinkedHashMap<String, DbColumn>() {{
            getColumnMap().values().forEach(it -> put(it.getColumnName(), it.copy()));
        }};
        dbTable.packageName = packageName;
        dbTable.namePrefix = namePrefix;
        dbTable.entityName = entityName;
        dbTable.tableName = tableName;
        dbTable.table = this.table;
        return dbTable;
    }

    @Override
    public void setName(String name) {
        setTableName(name);
    }

    @NotNull
    @Override
    public String getName() {
        return getTableName();
    }

    @Nullable
    public final DbColumn getDbColumn(String name) {
        return getColumnList().stream().filter(it -> {
            return it.getName().equals(name); //
        }).findAny().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DbTable dbTable = (DbTable) o;
        return Objects.equals(table, dbTable.table) &&
                Objects.equals(packageName, dbTable.packageName) &&
                Objects.equals(namePrefix, dbTable.namePrefix) &&
                Objects.equals(entityName, dbTable.entityName) &&
                Objects.equals(tableName, dbTable.tableName) &&
                deepEquals(getColumnMap().values().toArray(new DbColumn[0]),
                        dbTable.getColumnMap().values().toArray(new DbColumn[0]));
    }

    @Override
    public int hashCode() {
        return hash(table, columnMap, packageName, namePrefix, entityName, tableName);
    }
}

