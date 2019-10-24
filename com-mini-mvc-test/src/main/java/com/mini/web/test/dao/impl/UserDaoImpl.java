package com.mini.web.test.dao.impl;

import com.mini.jdbc.JdbcTemplate;
import com.mini.web.test.dao.UserDao;
import com.mini.web.test.entity.extend.UserExtend.UserExtendMapper;
import com.mini.web.test.entity.mapper.UserMapper;

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
    private UserExtendMapper userExtendMapper;
    private UserMapper userMapper;

    @Inject
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Inject
    public void setUserExtendMapper(UserExtendMapper userExtendMapper) {
        this.userExtendMapper = userExtendMapper;
    }

    @Inject
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public JdbcTemplate writeTemplate() {
        return jdbcTemplate;
    }

    @Override
    public JdbcTemplate readTemplate() {
        return jdbcTemplate;
    }

    @Override
    public UserExtendMapper getUserExtendMapper() {
        return userExtendMapper;
    }

    @Override
    public UserMapper getUserMapper() {
        return userMapper;
    }
}
