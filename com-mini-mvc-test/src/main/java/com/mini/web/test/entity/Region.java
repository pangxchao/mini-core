package com.mini.web.test.entity;

import com.mini.jdbc.SQLBuilder;
import java.io.Serializable;
import java.lang.String;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nonnull;

/**
 * Region.java 
 * @author xchao 
 */
public class Region implements Serializable {
  /**
   *  表名称 common_region 
   */
  public static final String TABLE = "common_region";

  /**
   * 地区码/地区ID 
   */
  public static final String ID = "region_id" ;

  /**
   * 地区名称 
   */
  public static final String NAME = "region_name" ;

  /**
   * 地区ID列表 
   */
  public static final String ID_URI = "region_id_uri" ;

  /**
   * 地区名称列表 
   */
  public static final String NAME_URI = "region_name_uri" ;

  /**
   * 上级地区ID 
   */
  public static final String REGION_ID = "region_region_id" ;

  private int id;

  private String name;

  private String idUri;

  private String nameUri;

  private int regionId;

  public Region() {
  }

  private Region(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setIdUri(builder.idUri);
    setNameUri(builder.nameUri);
    setRegionId(builder.regionId);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdUri() {
    return idUri;
  }

  public void setIdUri(String idUri) {
    this.idUri = idUri;
  }

  public String getNameUri() {
    return nameUri;
  }

  public void setNameUri(String nameUri) {
    this.nameUri = nameUri;
  }

  public int getRegionId() {
    return regionId;
  }

  public void setRegionId(int regionId) {
    this.regionId = regionId;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static Builder newBuilder(Region copy) {
    Builder builder = new Builder();
    builder.id = copy.getId();
    builder.name = copy.getName();
    builder.idUri = copy.getIdUri();
    builder.nameUri = copy.getNameUri();
    builder.regionId = copy.getRegionId();
    return builder;
  }

  public static Region mapper(ResultSet rs, int number) throws SQLException {
    Builder builder = Region.newBuilder();
    builder.id = rs.getInt(ID);
    builder.name = rs.getString(NAME);
    builder.idUri = rs.getString(ID_URI);
    builder.nameUri = rs.getString(NAME_URI);
    builder.regionId = rs.getInt(REGION_ID);
    return builder.build();
  }

  public static final class Builder {
    private int id;

    private String name;

    private String idUri;

    private String nameUri;

    private int regionId;

    private Builder() {
    }

    public final Builder id(int id) {
      this.id = id;
      return this;
    }

    public final Builder name(String name) {
      this.name = name;
      return this;
    }

    public final Builder idUri(String idUri) {
      this.idUri = idUri;
      return this;
    }

    public final Builder nameUri(String nameUri) {
      this.nameUri = nameUri;
      return this;
    }

    public final Builder regionId(int regionId) {
      this.regionId = regionId;
      return this;
    }

    @Nonnull
    public final Region build() {
      return new Region(this);
    }
  }

  public static class RegionBuilder extends SQLBuilder {
    protected RegionBuilder() {
      select(ID);
      select(NAME);
      select(ID_URI);
      select(NAME_URI);
      select(REGION_ID);
      select(TABLE);
    }
  }
}
