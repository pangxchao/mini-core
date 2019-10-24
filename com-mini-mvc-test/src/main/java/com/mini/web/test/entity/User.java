package com.mini.web.test.entity;

import com.mini.web.test.entity.base.BaseUser;

import java.io.Serializable;
import java.util.Date;

/**
 * User.java
 * @author xchao
 */
public class User implements BaseUser, Serializable {
    private static final long serialVersionUID = -1L;

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

    private Date createTime;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int getPhoneAuth() {
        return phoneAuth;
    }

    @Override
    public void setPhoneAuth(int phoneAuth) {
        this.phoneAuth = phoneAuth;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int getEmailAuth() {
        return emailAuth;
    }

    @Override
    public void setEmailAuth(int emailAuth) {
        this.emailAuth = emailAuth;
    }

    @Override
    public String getHeadUrl() {
        return headUrl;
    }

    @Override
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public int getRegionId() {
        return regionId;
    }

    @Override
    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
