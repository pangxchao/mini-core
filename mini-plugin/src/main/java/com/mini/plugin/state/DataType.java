package com.mini.plugin.state;

import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static java.util.Objects.hash;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public class DataType implements AbstractData<DataType>, Serializable {
    private String databaseType = "VARCHAR";
    private String javaType = "String";

    public DataType() {
    }

    public DataType(String databaseType, String javaType) {
        this.databaseType = databaseType;
        this.javaType = javaType;
    }

    @NotNull
    public final String getDatabaseType() {
        return defaultIfBlank(databaseType, "VARCHAR");
    }

    public final void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    @NotNull
    public final String getJavaType() {
        return defaultIfBlank(javaType, "String");
    }

    public final void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    @Override
    public final void setName(String name) {
        setDatabaseType(name);
    }

    @NotNull
    @Override
    public final String getName() {
        return getDatabaseType();
    }

    @NotNull
    @Override
    public synchronized final DataType copy() {
        final DataType value = new DataType();
        value.databaseType = databaseType;
        value.javaType = javaType;
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataType that = (DataType) o;
        return Objects.equal(databaseType, that.databaseType) &&
                Objects.equal(javaType, that.javaType);
    }

    @Override
    public int hashCode() {
        return hash(databaseType, javaType);
    }
}
