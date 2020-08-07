@file:Suppress("unused")

package com.mini.core.jdbc.suppor


import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@MustBeDocumented
@kotlin.annotation.Repeatable
@kotlin.annotation.Target(CLASS)
@kotlin.annotation.Retention(RUNTIME)
annotation class Join(val type: JoinType = JoinType.DEF, val value: String) {
    enum class JoinType {
        DEF,
        LEFT,
        INNER,
        RIGHT,
        OUTER;
    }

    @MustBeDocumented
    @kotlin.annotation.Target(CLASS)
    @kotlin.annotation.Retention(RUNTIME)
    annotation class List(vararg val value: Join = [])
}