package com.mini.core.test.dao;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.test.dao.base.RegionInfoBaseDao;
import com.mini.core.test.vo.RegionInfoVo;

import java.util.List;

import static com.mini.core.test.entity.RegionInfo.region_info;
import static com.mini.core.util.TreeUtil.buildTree;

public interface RegionInfoDao extends RegionInfoBaseDao  {

    default List<RegionInfoVo> tree() {
        return buildTree(queryList(new SQLBuilder(it -> {
            it.SELECT("*").FROM(region_info);
            System.out.println(this);
        }), RegionInfoVo.class));
    }
} 
