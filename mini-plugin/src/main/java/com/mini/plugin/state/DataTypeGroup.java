package com.mini.plugin.state;

import com.intellij.util.xmlb.annotations.Transient;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.hash;

public class DataTypeGroup implements AbstractGroup<DataType, DataTypeGroup>, Serializable {
    private Map<String, DataType> map;
    private String name = "";

    public DataTypeGroup(String name, Map<String, DataType> map) {
        this.name = name;
        this.map = map;
    }

    public DataTypeGroup(String name) {
        this(name, null);
    }

    public DataTypeGroup() {
    }

    @Override
    @Transient
    public synchronized void setName(String name) {
        this.name = name;
    }

    @Override
    @Transient
    public synchronized final String getName() {
        if (this.name == null) {
            name = "";
        }
        return name;
    }

    @NotNull
    @Override
    public final Map<String, DataType> getMap() {
        if (DataTypeGroup.this.map == null) {
            map = new LinkedHashMap<>();
        }
        return map;
    }

    @Override
    public void setMap(Map<String, DataType> map) {
        this.map = map;
    }

    @NotNull
    @Override
    public DataTypeGroup copy() {
        final DataTypeGroup group = new DataTypeGroup();
        Map<String, DataType> m = new LinkedHashMap<>();
        this.getMap().values().forEach(it -> {
            DataType dataType = it.copy();
            m.put(it.getName(), dataType);
        });
        group.setName(getName());
        group.setMap(m);
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataTypeGroup that = (DataTypeGroup) o;
        return Objects.equals(map, that.map) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return hash(map, name);
    }
}
