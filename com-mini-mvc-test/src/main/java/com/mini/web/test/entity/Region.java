package com.mini.web.test.entity;

import java.io.Serializable;
import java.lang.Override;
import java.lang.String;

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

  public static Builder builder() {
    return new Builder();
  }

  protected abstract static class AbstractBuilder<T> {
    private final Region region = new Region();

    protected abstract T getThis();

    public Region builder() {
      return this.region;
    }

    public final T id(int id) {
      region.setId(id);
      return getThis();
    }

    public final T name(String name) {
      region.setName(name);
      return getThis();
    }

    public final T idUri(String idUri) {
      region.setIdUri(idUri);
      return getThis();
    }

    public final T nameUri(String nameUri) {
      region.setNameUri(nameUri);
      return getThis();
    }

    public final T regionId(int regionId) {
      region.setRegionId(regionId);
      return getThis();
    }
  }

  public static final class Builder extends AbstractBuilder<Builder> {
    @Override
    protected Builder getThis() {
      return this;
    }
  }
}
