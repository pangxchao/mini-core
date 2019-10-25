package com.mini.web.test.entity.mapper;

import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.web.test.entity.Region;
import java.lang.Override;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * RegionMapper.java 
 * @author xchao 
 */
@Singleton
@Named("regionMapper")
public final class RegionMapper implements IMapper<Region> {
  @Nonnull
  @Override
  public Region get(ResultSet rs, int number) throws SQLException {
    Region.Builder builder = Region.builder();
    // 地区码/地区ID
    builder.id(rs.getInt(Region.ID));
    // 地区名称
    builder.name(rs.getString(Region.NAME));
    // 地区ID列表
    builder.idUri(rs.getString(Region.ID_URI));
    // 地区名称列表
    builder.nameUri(rs.getString(Region.NAME_URI));
    // 上级地区ID
    builder.regionId(rs.getInt(Region.REGION_ID));
    return builder.builder();
  }

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
