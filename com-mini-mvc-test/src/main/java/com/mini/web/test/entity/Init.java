package com.mini.web.test.entity;

import com.mini.web.test.entity.base.BaseInit;

import java.io.Serializable;

/**
 * Init.java
 * @author xchao
 */
public class Init implements BaseInit, Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 表名称 common_init
     */
    public static final String TABLE = "common_init";

    /**
     * 参数键
     */
    public static final String ID = "init_id";

    /**
     * 参数值
     */
    public static final String VALUE = "init_value";

    /**
     * 参数说明
     */
    public static final String REMARKS = "init_remarks";

    private int id;

    private String value;

    private String remarks;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
