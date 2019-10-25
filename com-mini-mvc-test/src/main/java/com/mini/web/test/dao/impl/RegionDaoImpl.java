package com.mini.web.test.dao.impl;

import com.mini.jdbc.JdbcTemplate;
import com.mini.web.test.dao.RegionDao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * RegionDaoImpl.java
 * @author xchao
 */
@Singleton
@Named("regionDao")
public class RegionDaoImpl implements RegionDao {
    private JdbcTemplate jdbcTemplate;

    @Inject
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JdbcTemplate writeTemplate() {
        return jdbcTemplate;
    }

    @Override
    public JdbcTemplate readTemplate() {
        return jdbcTemplate;
    }
}
