package com.mini.web.test.service.impl;

import com.mini.web.test.dao.RegionDao;
import com.mini.web.test.service.RegionService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * RegionServiceImpl.java
 * @author xchao
 */
@Singleton
@Named("regionService")
public class RegionServiceImpl implements RegionService {
    private RegionDao regionDao;

    @Inject
    public void setRegionDao(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Override
    public RegionDao getRegionDao() {
        return regionDao;
    }
}
