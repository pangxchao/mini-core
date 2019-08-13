package com.mini.web.test.entity.base;

import java.io.Serializable;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import java.util.Date;

/**
 * BaseUser.java 
 * @author xchao 
 */
public interface BaseUser extends Serializable {
  /**
   * 用户ID. 
   * @return The value of id 
   */
  default long getId() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户ID. 
   * @param id The value of id 
   */
  default void setId(long id) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户名. 
   * @return The value of name 
   */
  default String getName() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户名. 
   * @param name The value of name 
   */
  default void setName(String name) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * MD5(密码). 
   * @return The value of password 
   */
  default String getPassword() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * MD5(密码). 
   * @param password The value of password 
   */
  default void setPassword(String password) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户手机号. 
   * @return The value of phone 
   */
  default String getPhone() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户手机号. 
   * @param phone The value of phone 
   */
  default void setPhone(String phone) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 0-未认证，1-已谁. 
   * @return The value of phoneAuth 
   */
  default int getPhoneAuth() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 0-未认证，1-已谁. 
   * @param phoneAuth The value of phoneAuth 
   */
  default void setPhoneAuth(int phoneAuth) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户姓名. 
   * @return The value of fullName 
   */
  default String getFullName() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户姓名. 
   * @param fullName The value of fullName 
   */
  default void setFullName(String fullName) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户邮箱地址. 
   * @return The value of email 
   */
  default String getEmail() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户邮箱地址. 
   * @param email The value of email 
   */
  default void setEmail(String email) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 0-未认证，1-已认证. 
   * @return The value of emailAuth 
   */
  default int getEmailAuth() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 0-未认证，1-已认证. 
   * @param emailAuth The value of emailAuth 
   */
  default void setEmailAuth(int emailAuth) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户头像地址. 
   * @return The value of headUrl 
   */
  default String getHeadUrl() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户头像地址. 
   * @param headUrl The value of headUrl 
   */
  default void setHeadUrl(String headUrl) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户所属地区ID. 
   * @return The value of regionId 
   */
  default int getRegionId() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户所属地区ID. 
   * @param regionId The value of regionId 
   */
  default void setRegionId(int regionId) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户注册时间. 
   * @return The value of createTime 
   */
  default Date getCreateTime() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  /**
   * 用户注册时间. 
   * @param createTime The value of createTime 
   */
  default void setCreateTime(Date createTime) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }
}
