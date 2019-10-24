@file:Suppress("unused")

package com.mini.mvc.kotlin.entity

import java.io.Serializable
import java.util.*


/**
 * User.java
 * @author xchao
 */
data class User(
    var id: Long = 0,
    var name: String? = null,
    var password: String? = null,
    var phone: String? = null,
    var phoneAuth: Int = 0,
    var fullName: String? = null,
    var email: String? = null,
    var emailAuth: Int = 0,
    var headUrl: String? = null,
    var regionId: Int = 0,
    var createTime: Date? = null
) : Serializable {
    companion object UserCompanion {
        /**
         * 表名称 user_info
         */
        const val TABLE = "user_info"

        /**
         * 用户ID
         */
        const val ID = "user_id"

        /**
         * 用户名
         */
        const val NAME = "user_name"

        /**
         * MD5(密码)
         */
        const val PASSWORD = "user_password"

        /**
         * 用户手机号
         */
        const val PHONE = "user_phone"

        /**
         * 0-未认证，1-已谁
         */
        const val PHONE_AUTH = "user_phone_auth"

        /**
         * 用户姓名
         */
        const val FULL_NAME = "user_full_name"

        /**
         * 用户邮箱地址
         */
        const val EMAIL = "user_email"

        /**
         * 0-未认证，1-已认证
         */
        const val EMAIL_AUTH = "user_email_auth"

        /**
         * 用户头像地址
         */
        const val HEAD_URL = "user_head_url"

        /**
         * 用户所属地区ID
         */
        const val REGION_ID = "user_region_id"

        /**
         * 用户注册时间
         */
        const val CREATE_TIME = "user_create_time"
    }
}

var s: String? = null
var User.o: String?
    get() = s
    set(value) {
        s = value
    }
