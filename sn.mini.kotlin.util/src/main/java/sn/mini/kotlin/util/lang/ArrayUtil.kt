package sn.mini.kotlin.util.lang

fun <T> Array<T>.join(separator: CharSequence = ", "): String {
    return this.joinToString(separator, "", "", -1, "", null)
}

class ArrayUtil() {}