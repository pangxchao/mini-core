@file:Suppress("unused")

package com.mini.plugin.builder.kotlinpoet

import com.squareup.kotlinpoet.FunSpec


fun FunSpec.Builder.ifAdd(bool: Boolean, init: FunSpec.Builder.() -> Unit): FunSpec.Builder {
    return if (bool) this.apply(init) else this
}

fun FunSpec.Builder.ifElseAdd(bool: Boolean, ifInit: FunSpec.Builder.() -> Unit, elseInit: FunSpec.Builder.() -> Unit): FunSpec.Builder {
    return if (bool) this.apply(ifInit) else this.apply(elseInit)
}

fun <U> FunSpec.Builder.forAdd(collection: Collection<U>, init: FunSpec.Builder.(U) -> Unit): FunSpec.Builder {
    collection.forEach { this.apply { init(it) } }
    return this
}
