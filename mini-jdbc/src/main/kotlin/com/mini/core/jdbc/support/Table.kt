@file:Suppress("unused")

package com.mini.core.jdbc.support

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@MustBeDocumented
@kotlin.annotation.Target(CLASS)
@kotlin.annotation.Retention(RUNTIME)
annotation class Table(val value: String) 