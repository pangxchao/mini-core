package com.mini.web.test.dao.impl;

import com.mini.jdbc.JdbcTemplate;
import com.mini.web.test.dao.InitDao;
import com.mini.web.test.entity.mapper.InitMapper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * --- InitDaoImpl.java
 * @author xchao
 */
@Singleton
@Named("initDao")
public class InitDaoImpl implements InitDao {
    private JdbcTemplate jdbcTemplate;

    private InitMapper initMapper;

    @Inject
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Inject
    public void setInitMapper(InitMapper initMapper) {
        this.initMapper = initMapper;
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
    public InitMapper getInitMapper() {
        return initMapper;
    }
}
