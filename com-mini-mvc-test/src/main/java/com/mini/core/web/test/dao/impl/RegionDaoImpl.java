package com.mini.core.web.test.dao.impl;

import com.mini.core.jdbc.AbstractDao;
import com.mini.core.jdbc.JdbcTemplate;
import com.mini.core.web.test.dao.RegionDao;
import com.mini.core.web.test.dao.RegionDao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * RegionDaoImpl.java
 * @author xchao
 */
@Singleton
@Named("regionDao")
public class RegionDaoImpl extends AbstractDao implements RegionDao {
	@Inject
	private JdbcTemplate jdbcTemplate;


	@Override
	protected JdbcTemplate writeTemplate() {
		return jdbcTemplate;
	}

	@Override
	protected JdbcTemplate readTemplate() {
		return jdbcTemplate;
	}
}
