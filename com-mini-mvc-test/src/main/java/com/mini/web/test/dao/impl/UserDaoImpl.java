package com.mini.web.test.dao.impl;

import com.mini.jdbc.JdbcTemplate;
import com.mini.web.test.dao.UserDao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * UserDaoImpl.java
 * @author xchao
 */
@Singleton
@Named("userDao")
public class UserDaoImpl implements UserDao {
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
