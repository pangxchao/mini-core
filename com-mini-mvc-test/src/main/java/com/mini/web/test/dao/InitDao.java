package com.mini.web.test.dao;

import com.google.inject.ImplementedBy;
import com.mini.web.test.dao.base.BaseInitDao;
import com.mini.web.test.dao.impl.InitDaoImpl;

/**
 * --- InitDao.java
 * @author xchao
 */
@ImplementedBy(InitDaoImpl.class)
public interface InitDao extends BaseInitDao {
}
