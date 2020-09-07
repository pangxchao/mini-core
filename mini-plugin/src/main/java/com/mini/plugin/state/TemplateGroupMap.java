package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.hash;

public class TemplateGroupMap implements AbstractMap<TemplateGroup>, AbstractCopy<TemplateGroupMap> {
    private Map<String, TemplateGroup> map;

    @Override
    public void setMap(Map<String, TemplateGroup> map) {
        this.map = map;
    }

    @NotNull
    @Override
    public Map<String, TemplateGroup> getMap() {
        if (TemplateGroupMap.this.map == null) {
            map = new LinkedHashMap<>();
        }
        return map;
    }

    @Override
    public TemplateGroup get(String key) {
        return getMap().get(key);
    }

    @NotNull
    @Override
    public TemplateGroupMap copy() {
        final TemplateGroupMap map = new TemplateGroupMap();
        getMap().forEach((k, v) -> map.put(k, v.copy()));
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateGroupMap that = (TemplateGroupMap) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return hash(map);
    }
}
