package com.mini.web.test.entity;

import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.annotation.Comment;
import com.mini.core.jdbc.annotation.Id;
import com.mini.core.jdbc.annotation.Table;
import com.mini.core.util.DateFormatUtil;
import com.mini.core.web.annotation.Param;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.Date;

/**
 * User.java
 * @author xchao
 */
@Data
@Param
@Table("user_info")
@SuperBuilder(toBuilder = true)
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
	
	@Tolerate
	public User() {}
	
	public final String getCreateTime_DT() {
		return DateFormatUtil.formatDateTime(createTime);
	}
	
	public final String getCreateTime_D() {
		return DateFormatUtil.formatDate(createTime);
	}
	
	public final String getCreateTime_T() {
		return DateFormatUtil.formatTime(createTime);
	}
	
}
