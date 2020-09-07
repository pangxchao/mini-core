@file:JvmName("AutoCloseableKt")
@file:Suppress("unused")

package com.mini.plugin.extension


public inline fun <T : AutoCloseable?, R> T.use(block: (T) -> R): R {
    try {
        return block(this)
    } finally {
        try {
            this?.close()
        } catch (e: Throwable) {
        }
    }
}
