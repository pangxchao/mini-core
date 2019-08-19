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

    public static void init(SQLBuilder builder) {
        // 用户ID
        builder.select(User.ID);
        // 用户名
        builder.select(User.NAME);
        // MD5(密码)
        builder.select(User.PASSWORD);
        // 用户手机号
        builder.select(User.PHONE);
        // 0-未认证，1-已谁
        builder.select(User.PHONE_AUTH);
        // 用户姓名
        builder.select(User.FULL_NAME);
        // 用户邮箱地址
        builder.select(User.EMAIL);
        // 0-未认证，1-已认证
        builder.select(User.EMAIL_AUTH);
        // 用户头像地址
        builder.select(User.HEAD_URL);
        // 用户所属地区ID
        builder.select(User.REGION_ID);
        // 用户注册时间
        builder.select(User.CREATE_TIME);
        // 表名称
        builder.from(User.TABLE);
    }
}
