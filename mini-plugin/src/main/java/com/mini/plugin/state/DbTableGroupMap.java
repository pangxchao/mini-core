package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.hash;

public class DbTableGroupMap implements AbstractMap<DbTableGroup>, AbstractCopy<DbTableGroupMap> {
    private Map<String, DbTableGroup> map;

    @Override
    public void setMap(Map<String, DbTableGroup> map) {
        this.map = map;
    }

    @NotNull
    @Override
    public Map<String, DbTableGroup> getMap() {
        if (DbTableGroupMap.this.map == null) {
            map = new LinkedHashMap<>();
        }
        return map;
    }

    @Nullable
    @Override
    public DbTableGroup get(String key) {
        return getMap().get(key);
    }

    @NotNull
    @Override
    public DbTableGroupMap copy() {
        final DbTableGroupMap map = new DbTableGroupMap();
        getMap().forEach((k, v) -> map.put(k, v.copy()));
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DbTableGroupMap that = (DbTableGroupMap) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return hash(map);
    }
}
