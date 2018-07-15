package sn.mini.kotlin.util.lang

fun <T> Array<T>.join(separator: CharSequence = ""): String = //
        this.joinToString(separator, "", "", -1, "", null)
