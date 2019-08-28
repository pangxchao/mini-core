package com.mini.web.test.entity.mapper;

import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.web.test.entity.User;

import javax.inject.Named;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserMapper.java
 * @author xchao
 */
@Singleton
@Named("userMapper")
public class UserMapper implements IMapper<User> {
    @Override
    public User get(ResultSet rs, int number) throws SQLException {
        User user = new User();
        // 用户ID
        user.setId(rs.getLong(User.ID));
        // 用户名
        user.setName(rs.getString(User.NAME));
        // MD5(密码)
        user.setPassword(rs.getString(User.PASSWORD));
        // 用户手机号
        user.setPhone(rs.getString(User.PHONE));
        // 0-未认证，1-已谁
        user.setPhoneAuth(rs.getInt(User.PHONE_AUTH));
        // 用户姓名
        user.setFullName(rs.getString(User.FULL_NAME));
        // 用户邮箱地址
        user.setEmail(rs.getString(User.EMAIL));
        // 0-未认证，1-已认证
        user.setEmailAuth(rs.getInt(User.EMAIL_AUTH));
        // 用户头像地址
        user.setHeadUrl(rs.getString(User.HEAD_URL));
        // 用户所属地区ID
        user.setRegionId(rs.getInt(User.REGION_ID));
        // 用户注册时间
        user.setCreateTime(rs.getDate(User.CREATE_TIME));
        return user;
    }

    /**
     * UserBuilder.java
     * @author xchao
     */
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
