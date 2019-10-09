package com.mini.web.test.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.web.test.entity.Region;

/**
 * RegionMapper.java
 * @author xchao
 */
@Singleton
@Named("regionMapper")
public class RegionMapper implements IMapper<Region> {
	@Override
	public Region get(ResultSet rs, int number) throws SQLException {
		Region region = new Region();
		// 地区码/地区ID
		region.setId(rs.getInt(Region.ID));
		// 地区名称
		region.setName(rs.getString(Region.NAME));
		// 地区ID列表
		region.setIdUri(rs.getString(Region.ID_URI));
		// 地区名称列表
		region.setNameUri(rs.getString(Region.NAME_URI));
		// 上级地区ID
		region.setRegionId(rs.getInt(Region.REGION_ID));
		return region;
	}

	/**
	 * RegionBuilder.java
	 * @author xchao
	 */
	public static class RegionBuilder extends SQLBuilder {
		public RegionBuilder() {
			// 地区码/地区ID
			select(Region.ID);
			// 地区名称
			select(Region.NAME);
			// 地区ID列表
			select(Region.ID_URI);
			// 地区名称列表
			select(Region.NAME_URI);
			// 上级地区ID
			select(Region.REGION_ID);
			// 表名称
			from(Region.TABLE);
		}
	}
}
