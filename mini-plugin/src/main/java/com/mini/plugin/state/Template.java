package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.hash;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

public class Template implements AbstractData<Template>, Serializable {
    private String content;
    private String name;


    public Template(String name, String content) {
        this.content = content;
        this.name = name;
    }

    public Template(String name) {
        this(name, "");
    }

    public Template() {
    }

    @NotNull
    public String getContent() {
        return defaultIfBlank(content, "");
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Override
    public String getName() {
        return defaultIfBlank(name, "Name");
    }

    @NotNull
    @Override
    public synchronized final Template copy() {
        Template template = new Template();
        template.content = content;
        template.name = name;
        return template;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Template that = (Template) o;
        return Objects.equals(content, that.content) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return hash(content, name);
    }
}

