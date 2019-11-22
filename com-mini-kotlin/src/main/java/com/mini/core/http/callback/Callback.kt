package com.mini.core.http.callback

import java.io.IOException

@FunctionalInterface
interface Callback<T> {
    @Throws(IOException::class)
    fun accept(t: T)
}