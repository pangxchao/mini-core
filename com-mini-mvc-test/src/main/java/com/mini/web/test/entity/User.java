package com.mini.web.test.entity;

import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.annotation.Id;
import com.mini.core.jdbc.annotation.Table;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.util.JdbcUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * User.java
 * @author xchao
 */
@Table("user_info")
public class User implements Serializable {
	/**
	 * 表名称 user_info
	 */
	public static final String TABLE = "user_info";

	/**
	 * 用户ID
	 */
	public static final String ID = "user_id";

	/**
	 * 用户名
	 */
	public static final String NAME = "user_name";

	/**
	 * MD5(密码)
	 */
	public static final String PASSWORD = "user_password";

	/**
	 * 用户手机号
	 */
	public static final String PHONE = "user_phone";

	/**
	 * 0-未认证，1-已谁
	 */
	public static final String PHONE_AUTH = "user_phone_auth";

	/**
	 * 用户姓名
	 */
	public static final String FULL_NAME = "user_full_name";

	/**
	 * 用户邮箱地址
	 */
	public static final String EMAIL = "user_email";

	/**
	 * 0-未认证，1-已认证
	 */
	public static final String EMAIL_AUTH = "user_email_auth";

	/**
	 * 用户头像地址
	 */
	public static final String HEAD_URL = "user_head_url";

	/**
	 * 用户所属地区ID
	 */
	public static final String REGION_ID = "user_region_id";

	/**
	 * 用户注册时间
	 */
	public static final String CREATE_TIME = "user_create_time";

	@Id
	@Column("user_id")
	private long id;

	@Column("user_name")
	private String name;

	@Column("user_password")
	private String password;

	@Column("user_phone")
	private String phone;

	@Column("user_phone_auth")
	private int phoneAuth;

	@Column("user_full_name")
	private String fullName;

	@Column("user_email")
	private String email;

	@Column("user_email_auth")
	private int emailAuth;

	@Column("user_head_url")
	private String headUrl;

	@Column("user_region_id")
	private Integer regionId;

	@Column("user_create_time")
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPhoneAuth() {
		return phoneAuth;
	}

	public void setPhoneAuth(int phoneAuth) {
		this.phoneAuth = phoneAuth;
	}

	@Nullable
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Nullable
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEmailAuth() {
		return emailAuth;
	}

	public void setEmailAuth(int emailAuth) {
		this.emailAuth = emailAuth;
	}

	@Nullable
	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public static User mapper(ResultSet rs, int number) throws SQLException {
		Builder builder = User.newBuilder();
		builder.id         = JdbcUtil.getLong(rs, ID);
		builder.name       = JdbcUtil.getString(rs, NAME);
		builder.password   = JdbcUtil.getString(rs, PASSWORD);
		builder.phone      = JdbcUtil.getString(rs, PHONE);
		builder.phoneAuth  = JdbcUtil.getInt(rs, PHONE_AUTH);
		builder.fullName   = JdbcUtil.getString(rs, FULL_NAME);
		builder.email      = JdbcUtil.getString(rs, EMAIL);
		builder.emailAuth  = JdbcUtil.getInt(rs, EMAIL_AUTH);
		builder.headUrl    = JdbcUtil.getString(rs, HEAD_URL);
		builder.regionId   = JdbcUtil.getInteger(rs, REGION_ID);
		builder.createTime = JdbcUtil.getTimestamp(rs, CREATE_TIME);
		return builder.build();
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

	public static class UserBuilder extends SQLBuilder {
		protected UserBuilder() {
			select(ID);
			select(NAME);
			select(PASSWORD);
			select(PHONE);
			select(PHONE_AUTH);
			select(FULL_NAME);
			select(EMAIL);
			select(EMAIL_AUTH);
			select(HEAD_URL);
			select(REGION_ID);
			select(CREATE_TIME);
			from(TABLE);
		}
	}
}
