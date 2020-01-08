package com.mini.web.test.entity.extend;

import com.mini.core.holder.jdbc.Column;
import com.mini.core.holder.jdbc.Join;
import com.mini.core.holder.jdbc.Table;
import com.mini.web.test.entity.User;
import com.mini.web.test.util.FileGenerator;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

import static com.mini.web.test.entity.Region.*;
import static com.mini.web.test.entity.User.USER_REGION_ID;

/**
 * User.java
 * @author xchao
 */
@Data
@Table("user_info")
@Join(column = USER_REGION_ID)
public class UserExt extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(REGION_NAME)
	private String regionName;
	
	@Column(REGION_ID_URI)
	private String regionIdUri;
	
	@Column(REGION_NAME_URI)
	private String regionNameUri;
	
	
	public String getFullHeadUrl() {
		if (StringUtils.isBlank(getHeadUrl())) { return ""; }
		return FileGenerator.getPublicFullUrl(getHeadUrl());
	}
	
	private UserExt(UserExt.Builder builder) {
		setId(builder.id);
		setName(builder.name);
		setPassword(builder.password);
		setPhone(builder.phone);
		setPhoneAuth(builder.phoneAuth);
		setFullName(builder.fullName);
		setEmail(builder.email);
		setEmailAuth(builder.emailAuth);
		setHeadUrl(builder.headUrl);
		setRegionId(builder.regionId);
		setCreateTime(builder.createTime);
		setRegionId(builder.regionId);
		setRegionName(builder.regionName);
		setRegionIdUri(builder.regionIdUri);
		setRegionNameUri(builder.regionNameUri);
	}
	
	public static Builder extBuilder() {
		return new Builder();
	}
	
	public static Builder extBuilder(UserExt copy) {
		Builder builder = new Builder();
		builder.id            = copy.getId();
		builder.name          = copy.getName();
		builder.password      = copy.getPassword();
		builder.phone         = copy.getPhone();
		builder.phoneAuth     = copy.getPhoneAuth();
		builder.fullName      = copy.getFullName();
		builder.email         = copy.getEmail();
		builder.emailAuth     = copy.getEmailAuth();
		builder.headUrl       = copy.getHeadUrl();
		builder.regionId      = copy.getRegionId();
		builder.createTime    = copy.getCreateTime();
		builder.regionId      = copy.getRegionId();
		builder.regionName    = copy.getRegionName();
		builder.regionIdUri   = copy.getRegionIdUri();
		builder.regionNameUri = copy.getRegionNameUri();
		return builder;
	}
	
	
	public static final class Builder {
		private long id;
		private String name;
		private String password;
		private String phone;
		private int phoneAuth;
		private String fullName;
		private String email;
		private int emailAuth;
		private String headUrl;
		private int regionId;
		private String regionName;
		private String regionIdUri;
		private String regionNameUri;
		private Date createTime;
		
		private Builder() {
		}
		
		public Builder id(long val) {
			id = val;
			return this;
		}
		
		public Builder name(String val) {
			name = val;
			return this;
		}
		
		public Builder password(String val) {
			password = val;
			return this;
		}
		
		public Builder phone(String val) {
			phone = val;
			return this;
		}
		
		public Builder phoneAuth(int val) {
			phoneAuth = val;
			return this;
		}
		
		public Builder fullName(String val) {
			fullName = val;
			return this;
		}
		
		public Builder email(String val) {
			email = val;
			return this;
		}
		
		public Builder emailAuth(int val) {
			emailAuth = val;
			return this;
		}
		
		public Builder headUrl(String val) {
			headUrl = val;
			return this;
		}
		
		public Builder regionId(int val) {
			regionId = val;
			return this;
		}
		
		public Builder regionName(String val) {
			regionName = val;
			return this;
		}
		
		public Builder regionIdUri(String val) {
			regionIdUri = val;
			return this;
		}
		
		public Builder regionNameUri(String val) {
			regionNameUri = val;
			return this;
		}
		
		public UserExt build() {
			return new UserExt(this);
		}
		
		public Builder createTime(Date val) {
			createTime = val;
			return this;
		}
	}
}
