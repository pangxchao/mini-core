package com.mini.web.test.dao.impl;

import com.mini.jdbc.JdbcTemplate;
import com.mini.web.test.dao.RegionDao;
import com.mini.web.test.entity.mapper.RegionMapper;

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

    private RegionMapper regionMapper;

    @Inject
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Inject
    public void setRegionMapper(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
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
    public RegionMapper getRegionMapper() {
        return regionMapper;
    }
}
