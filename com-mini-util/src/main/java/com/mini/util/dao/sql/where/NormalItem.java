package com.mini.util.dao.sql.where;

public final class NormalItem implements SQLWhereItem {
    private String name;
    private String value = "?";
    private String connector = " = ";

    public NormalItem setName(String name) {
        if (name == null) return this;
        this.name = name;
        return this;
    }

    public NormalItem setValue(String value) {
        if (value == null) return this;
        this.value = value;
        return this;
    }

    public NormalItem setConnector(String connector) {
        if (connector == null) return this;
        this.connector = connector;
        return this;
    }

    @Override
    public String content() {
        return name + connector + value;
    }
}
