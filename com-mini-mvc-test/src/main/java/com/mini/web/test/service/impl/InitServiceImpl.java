package com.mini.web.test.service.impl;

import com.mini.web.test.dao.InitDao;
import com.mini.web.test.service.InitService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * --- InitServiceImpl.java
 * @author xchao
 */
@Singleton
@Named("initService")
public class InitServiceImpl implements InitService {
    private InitDao initDao;

    @Inject
    public void setInitDao(InitDao initDao) {
        this.initDao = initDao;
    }

    @Override
    public InitDao getInitDao() {
        return initDao;
    }
}
