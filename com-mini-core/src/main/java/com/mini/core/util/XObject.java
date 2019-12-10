package com.mini.core.util;

public final class XObject extends XAbstract<Object, XObject> {
    private XObject(Object value) {
        super(value);
    }

    @Override
    protected XObject getThis() {
        return this;
    }
}
