package com.mini.web.test.entity;

import com.mini.core.holder.jdbc.Column;
import com.mini.core.holder.jdbc.Comment;
import com.mini.core.holder.jdbc.Id;
import com.mini.core.holder.jdbc.Table;
import com.mini.core.holder.web.Param;
import com.mini.core.util.DateFormatUtil;
import lombok.Data;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Date;

/**
 * User.java
 * @author xchao
 */
@Data
@Param
@Table("user_info")
public class User implements Serializable {
	@Comment("表名称user_info")
	public static final String TABLE = "user_info";
	
	@Comment("用户ID")
	public static final String USER_ID = "user_id";
	
	@Comment("用户名")
	public static final String USER_NAME = "user_name";
	
	@Comment("MD5(密码)")
	public static final String USER_PASSWORD = "user_password";
	
	@Comment("用户手机号")
	public static final String USER_PHONE = "user_phone";
	
	@Comment("0-未认证，1-已谁")
	public static final String USER_PHONE_AUTH = "user_phone_auth";
	
	@Comment("用户姓名")
	public static final String USER_FULL_NAME = "user_full_name";
	
	@Comment("用户邮箱地址")
	public static final String USER_EMAIL = "user_email";
	
	@Comment("0-未认证，1-已认证")
	public static final String USER_EMAIL_AUTH = "user_email_auth";
	
	@Comment("用户头像地址")
	public static final String USER_HEAD_URL = "user_head_url";
	
	@Comment("用户所属地区ID")
	public static final String USER_REGION_ID = "user_region_id";
	
	@Comment("用户注册时间")
	public static final String USER_CREATE_TIME = "user_create_time";
	
	@Id
	@Column(USER_ID)
	private long id;
	
	@Column(USER_NAME)
	private String name;
	
	@Column(USER_PASSWORD)
	private String password;
	
	@Column(USER_PHONE)
	private String phone;
	
	@Column(USER_PHONE_AUTH)
	private int phoneAuth;
	
	@Column(USER_FULL_NAME)
	private String fullName;
	
	@Column(USER_EMAIL)
	private String email;
	
	@Column(USER_EMAIL_AUTH)
	private int emailAuth;
	
	@Column(USER_HEAD_URL)
	private String headUrl;
	
	@Column(USER_REGION_ID)
	private Integer regionId;
	
	@Column(USER_CREATE_TIME)
	private Date createTime;
	
	public final String getCreateTime_DT() {
		return DateFormatUtil.formatDateTime(createTime);
	}
	
	public final String getCreateTime_D() {
		return DateFormatUtil.formatDate(createTime);
	}
	
	public final String getCreateTime_T() {
		return DateFormatUtil.formatTime(createTime);
	}
	
	
	public static Builder builder() {
		return new Builder(new User());
	}
	
	public static Builder builder(User copy) {
		Builder builder = new Builder(new User());
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
	
	public static class Builder {
		private final User user;
		
		protected Builder(User user) {this.user = user;}
		
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
		
		@Nonnull
		public User build() {
			return user;
		}
	}
}
