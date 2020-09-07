@file:JvmName("ThrowableKt")
@file:Suppress("unused")

package com.mini.core.util

@Throws(RuntimeException::class)
fun <T : RuntimeException> Throwable.hidden(): T {
    throw this
}

