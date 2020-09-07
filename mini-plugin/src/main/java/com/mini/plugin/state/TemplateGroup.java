package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.hash;

public class TemplateGroup implements AbstractGroup<Template, TemplateGroup>, Serializable {
    private Map<String, Template> map;
    private String name = "";

    public TemplateGroup(String name, Map<String, Template> map) {
        this.name = name;
        this.map = map;
    }

    public TemplateGroup(String name) {
        this(name, null);
    }

    public TemplateGroup() {
    }

    @NotNull
    @Override
    public synchronized final String getName() {
        if (this.name == null) {
            name = "";
        }
        return name;
    }

    @Override
    public synchronized void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public final Map<String, Template> getMap() {
        if (TemplateGroup.this.map == null) {
            map = new LinkedHashMap<>();
        }
        return map;
    }

    @Override
    public void setMap(Map<String, Template> map) {
        this.map = map;
    }

    @NotNull
    @Override
    public TemplateGroup copy() {
        final TemplateGroup group = new TemplateGroup();
        Map<String, Template> m = new LinkedHashMap<>();
        this.getMap().values().forEach(it -> {
            Template template = it.copy();
            m.put(it.getName(), template);
        });
        group.setName(this.getName());
        group.setMap(m);
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateGroup that = (TemplateGroup) o;
        return Objects.equals(map, that.map) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return hash(map, name);
    }
}
