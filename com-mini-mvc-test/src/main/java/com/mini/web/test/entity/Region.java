package com.mini.web.test.entity;

import com.mini.core.holder.jdbc.Column;
import com.mini.core.holder.jdbc.Comment;
import com.mini.core.holder.jdbc.Id;
import com.mini.core.holder.jdbc.Table;
import com.mini.core.holder.web.Param;
import lombok.Data;

import javax.annotation.Nonnull;
import java.io.Serializable;

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
	
	public static Builder builder() {
		return new Builder(new Region());
	}
	
	public static Builder builder(Region copy) {
		return Region.builder()
			.id(copy.getId())
			.name(copy.getName())
			.idUri(copy.getIdUri())
			.nameUri(copy.getNameUri())
			.regionId(copy.getRegionId());
	}
	
	public static class Builder {
		private final Region Region;
		
		protected Builder(Region Region) {
			this.Region = Region;
		}
		
		public Builder id(int id) {
			Region.setId(id);
			return this;
		}
		
		public Builder name(String name) {
			Region.setName(name);
			return this;
		}
		
		public Builder idUri(String idUri) {
			Region.setIdUri(idUri);
			return this;
		}
		
		public Builder nameUri(String nameUri) {
			Region.setNameUri(nameUri);
			return this;
		}
		
		public Builder regionId(Integer regionId) {
			Region.setRegionId(regionId);
			return this;
		}
		
		@Nonnull
		public Region build() {
			return Region;
		}
	}
}
