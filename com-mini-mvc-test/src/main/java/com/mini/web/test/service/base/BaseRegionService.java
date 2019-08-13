package com.mini.web.test.service.base;

import com.mini.jdbc.util.Paging;
import com.mini.web.test.dao.RegionDao;
import com.mini.web.test.entity.Region;
import java.util.List;

/**
 * BaseRegionService.java 
 * @author xchao 
 */
public interface BaseRegionService {
  /**
   * 获取RegionDao对象 
   * @return RegionDao对象 
   */
  RegionDao getRegionDao();

  /**
   * 添加实体信息 
   * @param region 实体信息 
   * @return 执行结果 
   */
  default int insert(Region region) {
    return getRegionDao().insert(region);
  }

  /**
   * 修改实体信息 
   * @param region 实体信息 
   * @return 执行结果 
   */
  default int update(Region region) {
    return getRegionDao().update(region);
  }

  /**
   * 删除实体信息 
   * @param region 实体信息 
   * @return 执行结果 
   */
  default int delete(Region region) {
    return getRegionDao().delete(region);
  }

  /**
   * 根据ID删除实体信息 
   * @param id 地区码/地区ID 
   * @return 执行结果 
   */
  default int deleteById(int id) {
    return getRegionDao().deleteById(id);
  }

  /**
   * 根据ID查询实体信息 
   * @param id 地区码/地区ID 
   * @return 实体信息 
   */
  default Region queryById(int id) {
    return getRegionDao().queryById(id);
  }

  /**
   * 查询所有实体信息 
   * @return 实体信息列表 
   */
  default List<Region> queryAll() {
    return getRegionDao().queryAll();
  }

  /**
   * 查询所有实体信息 
   * @param paging 分布工具
   * @return 实体信息列表 
   */
  default List<Region> queryAll(Paging paging) {
    return getRegionDao().queryAll(paging);
  }
}
