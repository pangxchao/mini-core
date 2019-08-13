package com.mini.web.test.entity;

import com.mini.web.test.entity.base.BaseRegion;
import java.io.Serializable;
import java.lang.Override;
import java.lang.String;

/**
 * Region.java 
 * @author xchao 
 */
public class Region implements BaseRegion, Serializable {
  private static final long serialVersionUID = -1L;

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

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getIdUri() {
    return idUri;
  }

  @Override
  public void setIdUri(String idUri) {
    this.idUri = idUri;
  }

  @Override
  public String getNameUri() {
    return nameUri;
  }

  @Override
  public void setNameUri(String nameUri) {
    this.nameUri = nameUri;
  }

  @Override
  public int getRegionId() {
    return regionId;
  }

  @Override
  public void setRegionId(int regionId) {
    this.regionId = regionId;
  }
}
