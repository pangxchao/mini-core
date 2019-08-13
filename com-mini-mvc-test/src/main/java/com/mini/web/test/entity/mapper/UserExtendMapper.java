package com.mini.web.test.entity.mapper;

import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.web.test.entity.Region;
import com.mini.web.test.entity.User;
import com.mini.web.test.entity.UserExtend;

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
public class UserExtendMapper implements IMapper<UserExtend> {
    @Override
    public UserExtend get(ResultSet rs, int number) throws SQLException {
        UserExtend userExtend = new UserExtend();
        // 用户ID
        userExtend.setId(rs.getLong(User.ID));
        // 用户名
        userExtend.setName(rs.getString(User.NAME));
        // MD5(密码)
        userExtend.setPassword(rs.getString(User.PASSWORD));
        // 用户手机号
        userExtend.setPhone(rs.getString(User.PHONE));
        // 0-未认证，1-已谁
        userExtend.setPhoneAuth(rs.getInt(User.PHONE_AUTH));
        // 用户姓名
        userExtend.setFullName(rs.getString(User.FULL_NAME));
        // 用户邮箱地址
        userExtend.setEmail(rs.getString(User.EMAIL));
        // 0-未认证，1-已认证
        userExtend.setEmailAuth(rs.getInt(User.EMAIL_AUTH));
        // 用户头像地址
        userExtend.setHeadUrl(rs.getString(User.HEAD_URL));
        // 用户所属地区ID
        userExtend.setRegionId(rs.getInt(User.REGION_ID));
        // 用户注册时间
        userExtend.setCreateTime(rs.getDate(User.CREATE_TIME));
        userExtend.setRegionName(rs.getString(Region.NAME));
        userExtend.setRegionIdUri(rs.getString(Region.ID_URI));
        userExtend.setRegionNameUri(rs.getString(Region.NAME_URI));
        return userExtend;
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
        // 地区名称
        builder.select(Region.NAME);
        // 地区ID URI
        builder.select(Region.ID_URI);
        // 地区Name URI
        builder.select(Region.NAME_URI);
        // 表名称
        builder.from(User.TABLE);
        // 联合地区表
        builder.join("%s ON %s = %s", Region.TABLE, User.REGION_ID, Region.ID);
    }
}
