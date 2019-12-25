package com.mini.core.web.test.dao.base;

import com.mini.core.jdbc.JdbcInterface;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.model.Paging;
import com.mini.core.web.test.entity.Region;

import java.util.List;

/**
 * RegionBaseDao.java
 * @author xchao
 */
public interface RegionBaseDao extends JdbcInterface {
	/**
	 * 添加实体信息
	 * @param region 实体信息
	 * @return 执行结果
	 */
	default int insert(Region region) {
		return execute(new SQLBuilder() {{
			insertInto(Region.TABLE);
			// 地区码/地区ID
			values(Region.ID);
			params(region.getId());
			// 地区名称
			values(Region.NAME);
			params(region.getName());
			// 地区ID列表
			values(Region.ID_URI);
			params(region.getIdUri());
			// 地区名称列表
			values(Region.NAME_URI);
			params(region.getNameUri());
			// 上级地区ID
			values(Region.REGION_ID);
			params(region.getRegionId());
		}});
	}

	/**
	 * 添加实体信息
	 * @param region 实体信息
	 * @return 执行结果
	 */
	default int replace(Region region) {
		return execute(new SQLBuilder() {{
			replaceInto(Region.TABLE);
			// 地区码/地区ID
			values(Region.ID);
			params(region.getId());
			// 地区名称
			values(Region.NAME);
			params(region.getName());
			// 地区ID列表
			values(Region.ID_URI);
			params(region.getIdUri());
			// 地区名称列表
			values(Region.NAME_URI);
			params(region.getNameUri());
			// 上级地区ID
			values(Region.REGION_ID);
			params(region.getRegionId());
		}});
	}

	/**
	 * 修改实体信息
	 * @param region 实体信息
	 * @return 执行结果
	 */
	default int update(Region region) {
		return execute(new SQLBuilder() {{
			update(Region.TABLE);
			// 地区码/地区ID
			set("%s = ?", Region.ID);
			params(region.getId());
			// 地区名称
			set("%s = ?", Region.NAME);
			params(region.getName());
			// 地区ID列表
			set("%s = ?", Region.ID_URI);
			params(region.getIdUri());
			// 地区名称列表
			set("%s = ?", Region.NAME_URI);
			params(region.getNameUri());
			// 上级地区ID
			set("%s = ?", Region.REGION_ID);
			params(region.getRegionId());
			// 地区码/地区ID
			where("%s = ?", Region.ID);
			params(region.getId());
		}});
	}

	/**
	 * 删除实体信息
	 * @param region 实体信息
	 * @return 执行结果
	 */
	default int delete(Region region) {
		return execute(new SQLBuilder() {{
			delete().from(Region.TABLE);
			// 地区码/地区ID
			where("%s = ?", Region.ID);
			params(region.getId());
		}});
	}

	/**
	 * 删除实体信息
	 * @param id 地区码/地区ID
	 * @return 执行结果
	 */
	default int deleteById(int id) {
		return execute(new SQLBuilder() {{
			delete().from(Region.TABLE);
			// 地区码/地区ID
			where("%s = ?", Region.ID);
			params(id);
		}});
	}

	/**
	 * 根据ID查询实体信息
	 * @param id 地区码/地区ID
	 * @return 实体信息
	 */
	default Region queryById(int id) {
		return queryObject(new Region.RegionBuilder() {{
			// 地区码/地区ID
			where("%s = ?", Region.ID);
			params(id);
		}}, Region::mapper);
	}

	/**
	 * 查询所有实体信息
	 * @return 实体信息列表
	 */
	default List<Region> queryAll() {
		return queryList(new Region.RegionBuilder() {{
			//
		}}, Region::mapper);
	}

	/**
	 * 查询所有实体信息
	 * @param paging 分页工具
	 * @return 实体信息列表
	 */
	default List<Region> queryAll(Paging paging) {
		return queryList(paging, new Region.RegionBuilder() {{
			//
		}}, Region::mapper);
	}
}
