package com.mini.mvc.kotlin.entity.mapper

import com.mini.jdbc.SQLBuilder
import com.mini.jdbc.mapper.IMapper
import com.mini.mvc.kotlin.entity.User
import java.sql.ResultSet
import java.sql.SQLException
import javax.inject.Named
import javax.inject.Singleton

/**
 * UserMapper.java
 * @author xchao
 */
@Singleton
@Named("userMapper")
class UserMapper : IMapper<User> {
    @Throws(SQLException::class)
    override fun get(rs: ResultSet, number: Int): User {
        val user = User()
        // 用户ID
        user.id = rs.getLong(User.ID)
        // 用户名
        user.name = rs.getString(User.NAME)
        // MD5(密码)
        user.password = rs.getString(User.PASSWORD)
        // 用户手机号
        user.phone = rs.getString(User.PHONE)
        // 0-未认证，1-已谁
        user.phoneAuth = rs.getInt(User.PHONE_AUTH)
        // 用户姓名
        user.fullName = rs.getString(User.FULL_NAME)
        // 用户邮箱地址
        user.email = rs.getString(User.EMAIL)
        // 0-未认证，1-已认证
        user.emailAuth = rs.getInt(User.EMAIL_AUTH)
        // 用户头像地址
        user.headUrl = rs.getString(User.HEAD_URL)
        // 用户所属地区ID
        user.regionId = rs.getInt(User.REGION_ID)
        // 用户注册时间
        user.createTime = rs.getDate(User.CREATE_TIME)
        return user
    }

    /**
     * UserBuilder.java
     * @author xchao
     */
    open class UserBuilder : SQLBuilder() {
        init {
            // 用户ID
            select(User.ID)
            // 用户名
            select(User.NAME)
            // MD5(密码)
            select(User.PASSWORD)
            // 用户手机号
            select(User.PHONE)
            // 0-未认证，1-已谁
            select(User.PHONE_AUTH)
            // 用户姓名
            select(User.FULL_NAME)
            // 用户邮箱地址
            select(User.EMAIL)
            // 0-未认证，1-已认证
            select(User.EMAIL_AUTH)
            // 用户头像地址
            select(User.HEAD_URL)
            // 用户所属地区ID
            select(User.REGION_ID)
            // 用户注册时间
            select(User.CREATE_TIME)
            // 表名称
            from(User.TABLE)
        }
    }
}
