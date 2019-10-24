package com.mini.mvc.kotlin.dao

import com.mini.jdbc.BasicsDao
import com.mini.jdbc.SQLBuilder
import com.mini.jdbc.util.Paging
import com.mini.mvc.kotlin.entity.User
import com.mini.mvc.kotlin.entity.mapper.UserMapper
import com.mini.mvc.kotlin.entity.mapper.UserMapper.UserBuilder

interface UserDao : BasicsDao {
    var userMapper: UserMapper?

    /**
     * 添加实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    @Suppress("unused")
    fun insert(user: User): Int {
        return execute(object : SQLBuilder() {
            init {
                insert_into(User.TABLE)
                // 用户ID
                values(User.ID)
                params(user.id)
                // 用户名
                values(User.NAME)
                params(user.name)
                // MD5(密码)
                values(User.PASSWORD)
                params(user.password)
                // 用户手机号
                values(User.PHONE)
                params(user.phone)
                // 0-未认证，1-已谁
                values(User.PHONE_AUTH)
                params(user.phoneAuth)
                // 用户姓名
                values(User.FULL_NAME)
                params(user.fullName)
                // 用户邮箱地址
                values(User.EMAIL)
                params(user.email)
                // 0-未认证，1-已认证
                values(User.EMAIL_AUTH)
                params(user.emailAuth)
                // 用户头像地址
                values(User.HEAD_URL)
                params(user.headUrl)
                // 用户所属地区ID
                values(User.REGION_ID)
                params(user.regionId)
                // 用户注册时间
                values(User.CREATE_TIME)
                params(user.createTime)
            }
        })
    }

    /**
     * 添加实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    @Suppress("unused")
    fun replace(user: User): Int {
        return execute(object : SQLBuilder() {
            init {
                replace_into(User.TABLE)
                // 用户ID
                values(User.ID)
                params(user.id)
                // 用户名
                values(User.NAME)
                params(user.name)
                // MD5(密码)
                values(User.PASSWORD)
                params(user.password)
                // 用户手机号
                values(User.PHONE)
                params(user.phone)
                // 0-未认证，1-已谁
                values(User.PHONE_AUTH)
                params(user.phoneAuth)
                // 用户姓名
                values(User.FULL_NAME)
                params(user.fullName)
                // 用户邮箱地址
                values(User.EMAIL)
                params(user.email)
                // 0-未认证，1-已认证
                values(User.EMAIL_AUTH)
                params(user.emailAuth)
                // 用户头像地址
                values(User.HEAD_URL)
                params(user.headUrl)
                // 用户所属地区ID
                values(User.REGION_ID)
                params(user.regionId)
                // 用户注册时间
                values(User.CREATE_TIME)
                params(user.createTime)
            }
        })
    }

    /**
     * 修改实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    @Suppress("unused")
    fun update(user: User): Int {
        return execute(object : SQLBuilder() {
            init {
                update(User.TABLE)
                // 用户ID
                set("%s = ?", User.ID)
                params(user.id)
                // 用户名
                set("%s = ?", User.NAME)
                params(user.name)
                // MD5(密码)
                set("%s = ?", User.PASSWORD)
                params(user.password)
                // 用户手机号
                set("%s = ?", User.PHONE)
                params(user.phone)
                // 0-未认证，1-已谁
                set("%s = ?", User.PHONE_AUTH)
                params(user.phoneAuth)
                // 用户姓名
                set("%s = ?", User.FULL_NAME)
                params(user.fullName)
                // 用户邮箱地址
                set("%s = ?", User.EMAIL)
                params(user.email)
                // 0-未认证，1-已认证
                set("%s = ?", User.EMAIL_AUTH)
                params(user.emailAuth)
                // 用户头像地址
                set("%s = ?", User.HEAD_URL)
                params(user.headUrl)
                // 用户所属地区ID
                set("%s = ?", User.REGION_ID)
                params(user.regionId)
                // 用户注册时间
                set("%s = ?", User.CREATE_TIME)
                params(user.createTime)
                // 用户ID
                where("%s = ?", User.ID)
                params(user.id)
            }
        })
    }

    /**
     * 删除实体信息
     * @param user 实体信息
     * @return 执行结果
     */
    fun delete(user: User): Int {
        return execute(object : SQLBuilder() {
            init {
                delete().from(User.TABLE)
                // 用户ID
                where("%s = ?", User.ID)
                params(user.id)
            }
        })
    }

    /**
     * 根据ID删除实体信息
     * @param id 用户ID
     * @return 执行结果
     */
    @Suppress("unused")
    fun deleteById(id: Long): Int {
        return execute(object : SQLBuilder() {
            init {
                delete().from(User.TABLE)
                // 用户ID
                where("%s = ?", User.ID)
                params(id)
            }
        })
    }

    /**
     * 根据ID查询实体信息
     * @param id 用户ID
     * @return 实体信息
     */
    @Suppress("unused")
    fun queryById(id: Long): User {
        return queryOne<User>(object : UserBuilder() {
            init {
                // 用户ID
                where("%s = ?", User.ID)
                params(id)
            }
        }, userMapper)
    }

    /**
     * 查询所有实体信息
     * @return 实体信息列表
     */
    @Suppress("unused")
    fun queryAll(): List<User> {
        return query<User>(object : UserBuilder() {
            init {
                //
            }
        }, userMapper)
    }

    /**
     * 查询所有实体信息
     * @param paging 分布工具
     * @return 实体信息列表
     */
    @Suppress("unused")
    fun queryAll(paging: Paging): List<User> {
        return query<User>(paging, object : UserBuilder() {
            init {
                //
            }
        }, userMapper)
    }
}