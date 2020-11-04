@file:JvmName("ThrowableKt")
@file:Suppress("unused")

package com.mini.core.util

import kotlin.jvm.Throws

@Throws(RuntimeException::class)
fun <T : RuntimeException> Throwable.hidden(): T {
    throw this
}

