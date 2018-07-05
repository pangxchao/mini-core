package sn.mini.kotlin.util.lang

fun Throwable.getLastCause(): Throwable? = this.cause ?: this.cause?.getLastCause()

fun Throwable.getPrevCause(): Throwable? = this.cause ?: this.cause?.getPrevCause()