package com.mini.web.test.entity.mapper;

import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.web.test.entity.Region;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static void init(SQLBuilder builder) {
        // 地区码/地区ID
        builder.select(Region.ID);
        // 地区名称
        builder.select(Region.NAME);
        // 地区ID列表
        builder.select(Region.ID_URI);
        // 地区名称列表
        builder.select(Region.NAME_URI);
        // 上级地区ID
        builder.select(Region.REGION_ID);
        // 表名称
        builder.from(Region.TABLE);
    }
}
