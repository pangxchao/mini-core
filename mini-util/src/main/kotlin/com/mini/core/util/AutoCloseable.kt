@file:JvmName("AutoCloseableKt")
@file:Suppress("unused")

package com.mini.core.util


inline fun <T : AutoCloseable?, R> T.use(block: (T) -> R): R {
    try {
        return block(this)
    } finally {
        try {
            this?.close()
        } catch (e: Throwable) {
        }
    }
}
