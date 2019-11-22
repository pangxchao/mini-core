package com.mini.core.jdbc.model

import kotlin.math.max

class Paging constructor(var page: Int = 0, var limit: Int = 0) {

    var total: Int = 0
        set(vaule) {
            field = max(0, vaule)
        }

    /**
     * 获取跳过的条数
     * @return 跳过的条数
     */
    val skip: Int
        get() {
            val p = page - 1
            return p * limit
        }
}