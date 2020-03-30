package com.mini.web.test.entity;

import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.annotation.Comment;
import com.mini.core.jdbc.annotation.Id;
import com.mini.core.jdbc.annotation.Table;
import com.mini.core.web.annotation.Param;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * Region.java
 * @author xchao
 */
@Data
@Param
@Table("common_region")
@SuperBuilder(toBuilder = true)
public class Region implements Serializable {
	@Comment("表名称common_region")
	public static final String TABLE = "common_region";
	
	@Comment("地区码/地区ID")
	public static final String REGION_ID = "region_id";
	
	@Comment("地区名称")
	public static final String REGION_NAME = "region_name";
	
	@Comment("地区ID列表")
	public static final String REGION_ID_URI = "region_id_uri";
	
	@Comment("地区名称列表")
	public static final String REGION_NAME_URI = "region_name_uri";
	
	@Comment("上级地区ID")
	public static final String REGION_REGION_ID = "region_region_id";
	
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
	
	@Tolerate
	public Region() {}
}
