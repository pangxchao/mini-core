package com.mini.core.test.controller;

import com.mini.core.mvc.model.JsonModel;
import com.mini.core.test.dao.RegionInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("region")
public class RegionController {
    private final RegionInfoDao regionInfoDao;

    @Autowired
    public RegionController(RegionInfoDao regionInfoDao) {
        this.regionInfoDao = regionInfoDao;
    }

    @RequestMapping("tree")
    public JsonModel tree() {
        return new JsonModel().setData(regionInfoDao.tree());
    }
}
