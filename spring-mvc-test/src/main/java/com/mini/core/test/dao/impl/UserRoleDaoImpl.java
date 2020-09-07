package com.mini.core.test.dao.impl;

import com.mini.core.test.dao.UserRoleDao;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mini.core.jdbc.*;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends AbstractDao implements UserRoleDao {

    @NotNull
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRoleDaoImpl(@NotNull JdbcTemplate jdbcTemplate) {
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
 
