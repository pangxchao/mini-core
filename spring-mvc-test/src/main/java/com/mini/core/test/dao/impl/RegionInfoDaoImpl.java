package com.mini.core.test.dao.impl;

import com.mini.core.test.dao.RegionInfoDao;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mini.core.jdbc.*;

@Repository("regionInfoDao")
public class RegionInfoDaoImpl extends AbstractDao implements RegionInfoDao {

    @NotNull
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RegionInfoDaoImpl(@NotNull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NotNull
    @Override
    public JdbcTemplate writeTemplate() {
        return this.jdbcTemplate;
    }

    @NotNull
    @Override
    public JdbcTemplate readTemplate() {
        return this.jdbcTemplate;
    }
} 
 
