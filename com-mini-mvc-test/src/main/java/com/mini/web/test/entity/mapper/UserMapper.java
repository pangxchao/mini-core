package com.mini.web.test.entity.mapper;

import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.web.test.entity.User;
import java.lang.Override;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * UserMapper.java 
 * @author xchao 
 */
@Singleton
@Named("userMapper")
public final class UserMapper implements IMapper<User> {
  @Nonnull
  @Override
  public User get(ResultSet rs, int number) throws SQLException {
    User.Builder builder = User.builder();
    // 用户ID
    builder.id(rs.getLong(User.ID));
    // 用户名
    builder.name(rs.getString(User.NAME));
    // MD5(密码)
    builder.password(rs.getString(User.PASSWORD));
    // 用户手机号
    builder.phone(rs.getString(User.PHONE));
    // 0-未认证，1-已谁
    builder.phoneAuth(rs.getInt(User.PHONE_AUTH));
    // 用户姓名
    builder.fullName(rs.getString(User.FULL_NAME));
    // 用户邮箱地址
    builder.email(rs.getString(User.EMAIL));
    // 0-未认证，1-已认证
    builder.emailAuth(rs.getInt(User.EMAIL_AUTH));
    // 用户头像地址
    builder.headUrl(rs.getString(User.HEAD_URL));
    // 用户所属地区ID
    builder.regionId(rs.getInt(User.REGION_ID));
    // 用户注册时间
    builder.createTime(rs.getDate(User.CREATE_TIME));
    return builder.builder();
  }

  public static class UserBuilder extends SQLBuilder {
    public UserBuilder() {
      // 用户ID 
      select(User.ID);
      // 用户名 
      select(User.NAME);
      // MD5(密码) 
      select(User.PASSWORD);
      // 用户手机号 
      select(User.PHONE);
      // 0-未认证，1-已谁 
      select(User.PHONE_AUTH);
      // 用户姓名 
      select(User.FULL_NAME);
      // 用户邮箱地址 
      select(User.EMAIL);
      // 0-未认证，1-已认证 
      select(User.EMAIL_AUTH);
      // 用户头像地址 
      select(User.HEAD_URL);
      // 用户所属地区ID 
      select(User.REGION_ID);
      // 用户注册时间 
      select(User.CREATE_TIME);
      // 表名称 
      from(User.TABLE);
    }
  }
}
