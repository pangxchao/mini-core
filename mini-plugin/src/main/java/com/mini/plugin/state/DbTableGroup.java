package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.hash;

public class DbTableGroup implements AbstractGroup<DbTable, DbTableGroup>, Serializable {
    private Map<String, DbTable> map;
    private String name = "";

    public DbTableGroup(String name, Map<String, DbTable> map) {
        this.name = name;
        this.map = map;
    }

    public DbTableGroup(String name) {
        this(name, null);
    }

    public DbTableGroup() {
    }

    @Override
    public synchronized void setName(String name) {
        this.name = name;
    }

    @Override
    public synchronized final String getName() {
        if (this.name == null) {
            name = "";
        }
        return name;
    }

    @NotNull
    @Override
    public final Map<String, DbTable> getMap() {
        if (DbTableGroup.this.map == null) {
            map = new LinkedHashMap<>();
        }
        return map;
    }

    @Override
    public void setMap(Map<String, DbTable> map) {
        this.map = map;
    }

    @NotNull
    @Override
    public  DbTableGroup copy() {
        final DbTableGroup group = new DbTableGroup();
        Map<String, DbTable> m = new LinkedHashMap<>();
        this.getMap().values().forEach(it -> {
            DbTable table = it.copy();
            m.put(it.getName(), table);
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
        DbTableGroup that = (DbTableGroup) o;
        return Objects.equals(map, that.map) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return hash(map, name);
    }
}
