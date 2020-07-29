@file:Suppress("unused")

package com.mini.core.jdbc.support

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD

@MustBeDocumented
@kotlin.annotation.Target(FIELD)
@kotlin.annotation.Retention(RUNTIME)
annotation class Lock 