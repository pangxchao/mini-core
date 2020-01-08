package com.mini.web.test.dao.base;

import com.mini.core.jdbc.JdbcInterface;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.BeanMapper;
import com.mini.core.jdbc.model.Paging;
import com.mini.web.test.entity.Region;

import java.util.List;

/**
 * RegionBaseDao.java
 * @author xchao
 */
public interface RegionBaseDao extends JdbcInterface {
	/**
	 * 删除实体信息
	 * @param id 地区码/地区ID
	 * @return 执行结果
	 */
	default int deleteById(int id) {
		return execute(new SQLBuilder() {{
			delete().from(Region.TABLE);
			// 地区码/地区ID
			where("%s = ?", Region.REGION_ID);
			params(id);
		}});
	}
	
	/**
	 * 根据ID查询实体信息
	 * @param id 地区码/地区ID
	 * @return 实体信息
	 */
	default Region queryById(int id) {
		return queryObject(new SQLBuilder(Region.class) {{
			// 地区码/地区ID
			where("%s = ?", Region.REGION_ID);
			params(id);
		}}, BeanMapper.create(Region.class));
	}
	
	/**
	 * 查询所有实体信息
	 * @return 实体信息列表
	 */
	default List<Region> queryAll() {
		return queryList(new SQLBuilder(Region.class) {{
			//
		}}, BeanMapper.create(Region.class));
	}
	
	/**
	 * 查询所有实体信息
	 * @param page  分页-页码数
	 * @param limit 分页- 每页条数
	 * @return 实体信息列表
	 */
	default Paging<Region> queryAll(int page, int limit) {
		return queryPaging(page, limit, new SQLBuilder(Region.class) {{
			//
		}}, BeanMapper.create(Region.class));
	}
}
