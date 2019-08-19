package com.mini.web.test.entity.base;

import java.io.Serializable;

/**
 * BaseInit.java
 * @author xchao
 */
public interface BaseInit extends Serializable {
    /**
     * 参数键.
     * @return The value of id
     */
    default int getId() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * 参数键.
     * @param id The value of id
     */
    default void setId(int id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * 参数值.
     * @return The value of value
     */
    default String getValue() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * 参数值.
     * @param value The value of value
     */
    default void setValue(String value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * 参数说明.
     * @return The value of remarks
     */
    default String getRemarks() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * 参数说明.
     * @param remarks The value of remarks
     */
    default void setRemarks(String remarks) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
