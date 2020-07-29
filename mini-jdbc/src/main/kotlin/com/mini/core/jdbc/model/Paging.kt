@file:Suppress("unused")

package com.mini.core.jdbc.model

import java.io.Serializable
import kotlin.math.max

/**
 * 分页控件
 *
 * @author xchao
 */
class Paging<T> private constructor() : Serializable {
    // 页码数
    var page: Int = 1
        private set(value) {
            field = max(1, value)
        }

    // 每页条数
    var limit: Int = 20
        private set(value) {
            field = max(1, value)
        }

    // 数据总条数
    var total: Int = 0
        set(value) {
            field = max(0, value)
        }

    // 数据库查询的起始位置
    var start: Int = 0
        get() = (page - 1) * limit
        private set

    // 查询数据结果
    var rows: List<T>? = null

    // 初始化构造方法
    constructor(page: Int = 1, limit: Int = 20) : this() {
        this.limit = limit
        this.page = page
    }

}