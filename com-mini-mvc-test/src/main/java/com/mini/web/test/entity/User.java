package com.mini.web.test.entity;

import com.mini.core.holder.jdbc.*;
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
	
	@CreateAt
	@Column(USER_CREATE_TIME)
	private Date createTime;
	
	public User() {
	}
	
	private User(Builder builder) {
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
	}
	
	public final String getCreateTime_DT() {
		return DateFormatUtil.formatDateTime(createTime);
	}
	
	public final String getCreateTime_D() {
		return DateFormatUtil.formatDate(createTime);
	}
	
	public final String getCreateTime_T() {
		return DateFormatUtil.formatTime(createTime);
	}
	
	public static Builder newBuilder() {
		return new Builder();
	}
	
	public static Builder newBuilder(User copy) {
		Builder builder = new Builder();
		builder.id         = copy.getId();
		builder.name       = copy.getName();
		builder.password   = copy.getPassword();
		builder.phone      = copy.getPhone();
		builder.phoneAuth  = copy.getPhoneAuth();
		builder.fullName   = copy.getFullName();
		builder.email      = copy.getEmail();
		builder.emailAuth  = copy.getEmailAuth();
		builder.headUrl    = copy.getHeadUrl();
		builder.regionId   = copy.getRegionId();
		builder.createTime = copy.getCreateTime();
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
		
		private Integer regionId;
		
		private Date createTime;
		
		private Builder() {
		}
		
		public final Builder setId(long id) {
			this.id = id;
			return this;
		}
		
		public final Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public final Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public final Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}
		
		public final Builder setPhoneAuth(int phoneAuth) {
			this.phoneAuth = phoneAuth;
			return this;
		}
		
		public final Builder setFullName(String fullName) {
			this.fullName = fullName;
			return this;
		}
		
		public final Builder setEmail(String email) {
			this.email = email;
			return this;
		}
		
		public final Builder setEmailAuth(int emailAuth) {
			this.emailAuth = emailAuth;
			return this;
		}
		
		public final Builder setHeadUrl(String headUrl) {
			this.headUrl = headUrl;
			return this;
		}
		
		public final Builder setRegionId(Integer regionId) {
			this.regionId = regionId;
			return this;
		}
		
		public final Builder setCreateTime(Date createTime) {
			this.createTime = createTime;
			return this;
		}
		
		@Nonnull
		public final User build() {
			return new User(this);
		}
	}
}
