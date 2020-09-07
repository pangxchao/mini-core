package com.mini.core.test.vo;

import com.mini.core.jdbc.support.Table;
import com.mini.core.test.entity.RegionInfo;
import com.mini.core.util.tree.ITree;

import java.util.ArrayList;
import java.util.List;

@Table(RegionInfo.region_info)
public class RegionInfoVo extends RegionInfo implements ITree<RegionInfoVo> {
    private List<RegionInfoVo> children = new ArrayList<>();

    @Override
    public void setChildren(List<RegionInfoVo> children) {
        this.children = children;
    }

    @Override
    public List<RegionInfoVo> getChildren() {
        return this.children;
    }
}
