@file:Suppress("unused")

package com.mini.core.jdbc.support


import com.mini.core.jdbc.builder.SQLBuilder
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FIELD

@MustBeDocumented
@kotlin.annotation.Repeatable
@kotlin.annotation.Target(FIELD)
@kotlin.annotation.Retention(RUNTIME)
annotation class Join(val type: JoinType = JoinType.DEF, val value: String) {
    enum class JoinType {
        DEF {
            override fun execute(builder: SQLBuilder, join: String) {
                builder.JOIN(join)
            }
        },
        LEFT {
            override fun execute(builder: SQLBuilder, join: String) {
                builder.LEFT_JOIN(join)
            }
        },
        INNER {
            override fun execute(builder: SQLBuilder, join: String) {
                builder.INNER_JOIN(join)
            }
        },
        RIGHT {
            override fun execute(builder: SQLBuilder, join: String) {
                builder.RIGHT_JOIN(join)
            }
        },
        OUTER {
            override fun execute(builder: SQLBuilder, join: String) {
                builder.OUTER_JOIN(join)
            }
        };

        abstract fun execute(builder: SQLBuilder, join: String)
    }

    @MustBeDocumented
    @kotlin.annotation.Target(CLASS)
    @kotlin.annotation.Retention(RUNTIME)
    annotation class List(vararg val value: Join = [])
}