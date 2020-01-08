package com.mini.web.test.entity;

import com.mini.core.holder.jdbc.Column;
import com.mini.core.holder.jdbc.Comment;
import com.mini.core.holder.jdbc.Id;
import com.mini.core.holder.jdbc.Table;
import com.mini.core.holder.web.Param;
import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import javax.annotation.Nonnull;
import lombok.Data;

/**
 * Region.java 
 * @author xchao 
 */
@Data
@Param
@Table("common_region")
public class Region implements Serializable {
  @Comment("表名称common_region")
  public static final String TABLE = "common_region";

  @Comment("地区码/地区ID")
  public static final String REGION_ID = "region_id" ;

  @Comment("地区名称")
  public static final String REGION_NAME = "region_name" ;

  @Comment("地区ID列表")
  public static final String REGION_ID_URI = "region_id_uri" ;

  @Comment("地区名称列表")
  public static final String REGION_NAME_URI = "region_name_uri" ;

  @Comment("上级地区ID")
  public static final String REGION_REGION_ID = "region_region_id" ;

  @Id
  @Column(REGION_ID)
  private int id;

  @Column(REGION_NAME)
  private String name;

  @Column(REGION_ID_URI)
  private String idUri;

  @Column(REGION_NAME_URI)
  private String nameUri;

  @Column(REGION_REGION_ID)
  private Integer regionId;

  public Region() {
  }

  private Region(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setIdUri(builder.idUri);
    setNameUri(builder.nameUri);
    setRegionId(builder.regionId);
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

  public static final class Builder {
    private int id;

    private String name;

    private String idUri;

    private String nameUri;

    private Integer regionId;

    private Builder() {
    }

    public final Builder setId(int id) {
      this.id = id;
      return this;
    }

    public final Builder setName(String name) {
      this.name = name;
      return this;
    }

    public final Builder setIdUri(String idUri) {
      this.idUri = idUri;
      return this;
    }

    public final Builder setNameUri(String nameUri) {
      this.nameUri = nameUri;
      return this;
    }

    public final Builder setRegionId(Integer regionId) {
      this.regionId = regionId;
      return this;
    }

    @Nonnull
    public final Region build() {
      return new Region(this);
    }
  }
}
