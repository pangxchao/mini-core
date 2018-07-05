package sn.mini.kotlin.util.lang

fun <E> Collection<E>.join(separator: CharSequence = ", "): String = //
        this.joinToString(separator, "", "", -1, "", null)
