@file:Suppress("unused")

package com.mini.core.jdbc.suppor

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FIELD


@MustBeDocumented
@kotlin.annotation.Retention(RUNTIME)
@kotlin.annotation.Target(FIELD, CLASS)
annotation class Comment(val value: String)