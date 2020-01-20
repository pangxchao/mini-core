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
	
	public static UserExt.Builder builder() {
		return new Builder(new UserExt());
	}
	
	public static Builder builder(UserExt copy) {
		return UserExt.builder()
			.id(copy.getId())
			.name(copy.getName())
			.password(copy.getPassword())
			.phone(copy.getPhone())
			.phoneAuth(copy.getPhoneAuth())
			.fullName(copy.getFullName())
			.email(copy.getEmail())
			.emailAuth(copy.getEmailAuth())
			.headUrl(copy.getHeadUrl())
			.regionId(copy.getRegionId())
			.createTime(copy.getCreateTime())
			.regionName(copy.getRegionName())
			.regionIdUri(copy.getRegionIdUri())
			.regionNameUri(copy.getRegionNameUri());
	}
	
	public static class Builder extends User.Builder {
		private final UserExt user;
		
		protected Builder(UserExt user) {
			super(user);
			this.user = user;
		}
		
		public Builder id(long id) {
			user.setId(id);
			return this;
		}
		
		public Builder name(String name) {
			user.setName(name);
			return this;
		}
		
		public Builder password(String password) {
			user.setPassword(password);
			return this;
		}
		
		public Builder phone(String phone) {
			user.setPhone(phone);
			return this;
		}
		
		public Builder phoneAuth(int phoneAuth) {
			user.setPhoneAuth(phoneAuth);
			return this;
		}
		
		public Builder fullName(String fullName) {
			user.setFullName(fullName);
			return this;
		}
		
		public Builder email(String email) {
			user.setEmail(email);
			return this;
		}
		
		public Builder emailAuth(int emailAuth) {
			user.setEmailAuth(emailAuth);
			return this;
		}
		
		public Builder headUrl(String headUrl) {
			user.setHeadUrl(headUrl);
			return this;
		}
		
		public Builder regionId(Integer regionId) {
			user.setRegionId(regionId);
			return this;
		}
		
		public Builder createTime(Date createTime) {
			user.setCreateTime(createTime);
			return this;
		}
		
		public Builder regionName(String regionName) {
			user.setRegionName(regionName);
			return this;
		}
		
		public Builder regionIdUri(String regionIdUri) {
			user.setRegionIdUri(regionIdUri);
			return this;
		}
		
		public Builder regionNameUri(String regionNameUri) {
			user.setRegionNameUri(regionNameUri);
			return this;
		}
		
		@Nonnull
		public UserExt build() {
			return user;
		}
	}
	
	public static void main(String[] args) throws NoSuchMethodException {
		System.out.println(UserExt.class.getMethod("builder"));
	}
}
