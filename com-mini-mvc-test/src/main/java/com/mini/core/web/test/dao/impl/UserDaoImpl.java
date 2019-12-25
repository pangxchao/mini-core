package com.mini.core.web.test.dao.impl;

import com.mini.core.jdbc.AbstractDao;
import com.mini.core.jdbc.JdbcTemplate;
import com.mini.core.web.test.dao.UserDao;
import com.mini.core.web.test.dao.UserDao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * UserDaoImpl.java
 * @author xchao
 */
@Singleton
@Named("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao {
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
