package com.mini.mvc.kotlin.dao.impl

import com.mini.jdbc.JdbcTemplate
import com.mini.mvc.kotlin.dao.UserDao
import com.mini.mvc.kotlin.entity.mapper.UserMapper
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * UserDaoImpl.java
 * @author xchao
 */
@Singleton
@Named("userDao")
@Suppress("unused")
class UserDaoImpl : UserDao {

    @set:Inject
    var jdbcTemplate: JdbcTemplate? = null

    @set:Inject
    override var userMapper: UserMapper? = null


    override fun writeTemplate(): JdbcTemplate? {
        return jdbcTemplate
    }

    override fun readTemplate(): JdbcTemplate? {
        return jdbcTemplate
    }
}
