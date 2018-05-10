package sn.mini.kotlin.util.lang

fun Throwable.getLastCause(): Throwable? {
    return this.cause ?: this.cause?.getLastCause()
}

fun Throwable.getPrevCause(): Throwable? {
    return this.cause ?: this.cause?.getPrevCause()
}