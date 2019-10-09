package com.mini.web.test.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.mini.web.test.entity.Region;
import com.mini.web.test.service.base.BaseRegionService;
import com.mini.web.test.service.impl.RegionServiceImpl;

/**
 * RegionService.java
 * @author xchao
 */
@ImplementedBy(RegionServiceImpl.class)
public interface RegionService extends BaseRegionService {

	/**
	 * 根据父ID查询地区信息
	 * @param parentId 父ID 0-顶层
	 * @return 地区信息列表
	 */
	default List<Region> queryByParent(long parentId) {
		return getRegionDao().queryByParent(parentId);
	}
}
