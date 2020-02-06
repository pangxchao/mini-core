package com.mini.web.test.entity.extend;

import com.mini.core.holder.jdbc.Column;
import com.mini.core.holder.jdbc.Join;
import com.mini.core.holder.jdbc.Table;
import com.mini.web.test.entity.User;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Date;

import static com.mini.web.test.entity.Region.*;
import static com.mini.web.test.entity.User.USER_REGION_ID;
import static com.mini.web.test.util.FileGenerator.getPublicFullUrl;

/**
 * java
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
		if (StringUtils.isBlank(getHeadUrl())) {
			return "";
		}
		return getPublicFullUrl(getHeadUrl());
	}
	
	public static Builder builder() {
		return new Builder(new UserExt());
	}
	
	public static Builder builder(UserExt copy) {
		Builder builder = new Builder(new UserExt());
		builder.regionName(copy.getRegionName());
		builder.regionIdUri(copy.getRegionIdUri());
		builder.regionNameUri(copy.getRegionNameUri());
		builder.id(copy.getId());
		builder.name(copy.getName());
		builder.password(copy.getPassword());
		builder.phone(copy.getPhone());
		builder.phoneAuth(copy.getPhoneAuth());
		builder.fullName(copy.getFullName());
		builder.email(copy.getEmail());
		builder.emailAuth(copy.getEmailAuth());
		builder.headUrl(copy.getHeadUrl());
		builder.regionId(copy.getRegionId());
		builder.createTime(copy.getCreateTime());
		return builder;
	}
	
	public static class Builder extends User.Builder {
		private final UserExt userExt;
		
		protected Builder(UserExt userExt) {
			super(userExt);
			this.userExt = userExt;
		}
		
		public Builder regionName(String regionName) {
			userExt.setRegionName(regionName);
			return this;
		}
		
		public Builder regionIdUri(String regionIdUri) {
			userExt.setRegionIdUri(regionIdUri);
			return this;
		}
		
		public Builder regionNameUri(String regionNameUri) {
			userExt.setRegionNameUri(regionNameUri);
			return this;
		}
		
		public Builder id(long id) {
			userExt.setId(id);
			return this;
		}
		
		public Builder name(String name) {
			userExt.setName(name);
			return this;
		}
		
		public Builder password(String password) {
			userExt.setPassword(password);
			return this;
		}
		
		public Builder phone(String phone) {
			userExt.setPhone(phone);
			return this;
		}
		
		public Builder phoneAuth(int phoneAuth) {
			userExt.setPhoneAuth(phoneAuth);
			return this;
		}
		
		public Builder fullName(String fullName) {
			userExt.setFullName(fullName);
			return this;
		}
		
		public Builder email(String email) {
			userExt.setEmail(email);
			return this;
		}
		
		public Builder emailAuth(int emailAuth) {
			userExt.setEmailAuth(emailAuth);
			return this;
		}
		
		public Builder headUrl(String headUrl) {
			userExt.setHeadUrl(headUrl);
			return this;
		}
		
		public Builder regionId(Integer regionId) {
			userExt.setRegionId(regionId);
			return this;
		}
		
		public Builder createTime(Date createTime) {
			userExt.setCreateTime(createTime);
			return this;
		}
		
		@Nonnull
		public UserExt build() {
			return userExt;
		}
	}
}
