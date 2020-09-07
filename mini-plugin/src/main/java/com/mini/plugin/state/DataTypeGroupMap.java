package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.hash;

public class DataTypeGroupMap implements AbstractMap<DataTypeGroup>, AbstractCopy<DataTypeGroupMap>, Serializable {
    private Map<String, DataTypeGroup> map;

    @Override
    public void setMap(Map<String, DataTypeGroup> map) {
        this.map = map;
    }

    @NotNull
    @Override
    public Map<String, DataTypeGroup> getMap() {
        if (DataTypeGroupMap.this.map == null) {
            map = new LinkedHashMap<>();
        }
        return map;
    }

    @Override
    public DataTypeGroup get(String key) {
        return getMap().get(key);
    }

    @NotNull
    @Override
    public DataTypeGroupMap copy() {
        final DataTypeGroupMap map = new DataTypeGroupMap();
        getMap().forEach((k, v) -> map.put(k, v.copy()));
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataTypeGroupMap that = (DataTypeGroupMap) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return hash(map);
    }
}
