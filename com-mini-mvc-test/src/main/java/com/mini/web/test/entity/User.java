package com.mini.web.test.entity;

import java.io.Serializable;
import java.lang.Override;
import java.lang.String;
import java.util.Date;

/**
 * User.java 
 * @author xchao 
 */
public class User implements Serializable {
  /**
   *  表名称 user_info 
   */
  public static final String TABLE = "user_info";

  /**
   * 用户ID 
   */
  public static final String ID = "user_id" ;

  /**
   * 用户名 
   */
  public static final String NAME = "user_name" ;

  /**
   * MD5(密码) 
   */
  public static final String PASSWORD = "user_password" ;

  /**
   * 用户手机号 
   */
  public static final String PHONE = "user_phone" ;

  /**
   * 0-未认证，1-已谁 
   */
  public static final String PHONE_AUTH = "user_phone_auth" ;

  /**
   * 用户姓名 
   */
  public static final String FULL_NAME = "user_full_name" ;

  /**
   * 用户邮箱地址 
   */
  public static final String EMAIL = "user_email" ;

  /**
   * 0-未认证，1-已认证 
   */
  public static final String EMAIL_AUTH = "user_email_auth" ;

  /**
   * 用户头像地址 
   */
  public static final String HEAD_URL = "user_head_url" ;

  /**
   * 用户所属地区ID 
   */
  public static final String REGION_ID = "user_region_id" ;

  /**
   * 用户注册时间 
   */
  public static final String CREATE_TIME = "user_create_time" ;

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

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

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

  public String getHeadUrl() {
    return headUrl;
  }

  public void setHeadUrl(String headUrl) {
    this.headUrl = headUrl;
  }

  public int getRegionId() {
    return regionId;
  }

  public void setRegionId(int regionId) {
    this.regionId = regionId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public static Builder builder() {
    return new Builder();
  }

  protected abstract static class AbstractBuilder<T> {
    private final User user = new User();

    protected abstract T getThis();

    public User builder() {
      return this.user;
    }

    public final T id(long id) {
      user.setId(id);
      return getThis();
    }

    public final T name(String name) {
      user.setName(name);
      return getThis();
    }

    public final T password(String password) {
      user.setPassword(password);
      return getThis();
    }

    public final T phone(String phone) {
      user.setPhone(phone);
      return getThis();
    }

    public final T phoneAuth(int phoneAuth) {
      user.setPhoneAuth(phoneAuth);
      return getThis();
    }

    public final T fullName(String fullName) {
      user.setFullName(fullName);
      return getThis();
    }

    public final T email(String email) {
      user.setEmail(email);
      return getThis();
    }

    public final T emailAuth(int emailAuth) {
      user.setEmailAuth(emailAuth);
      return getThis();
    }

    public final T headUrl(String headUrl) {
      user.setHeadUrl(headUrl);
      return getThis();
    }

    public final T regionId(int regionId) {
      user.setRegionId(regionId);
      return getThis();
    }

    public final T createTime(Date createTime) {
      user.setCreateTime(createTime);
      return getThis();
    }
  }

  public static final class Builder extends AbstractBuilder<Builder> {
    @Override
    protected Builder getThis() {
      return this;
    }
  }
}
