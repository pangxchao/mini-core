@file:JvmName("ThrowableKt")
@file:Suppress("unused")

package com.mini.plugin.extension

@Throws(RuntimeException::class)
fun <T : RuntimeException> Throwable.hidden(): T {
    throw this
}

