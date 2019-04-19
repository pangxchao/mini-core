package com.mini.spring.model;

import java.util.ArrayList;
import java.util.Collection;

public class ListJsonModel extends ArrayList<Object> {
    private static final long serialVersionUID = -7538258131773798743L;

    public ListJsonModel addData(Object value) {
        super.add(value);
        return this;
    }

    public ListJsonModel addAllData(Collection<?> c) {
        super.addAll(c);
        return this;
    }
}
