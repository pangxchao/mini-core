@file:Suppress("unused")

package com.mini.plugin.builder.kotlinpoet

import com.squareup.kotlinpoet.TypeSpec


fun TypeSpec.Builder.ifAdd(bool: Boolean, init: TypeSpec.Builder.() -> Unit): TypeSpec.Builder {
    return if (bool) this.apply(init) else this
}

fun TypeSpec.Builder.ifElseAdd(bool: Boolean, ifInit: TypeSpec.Builder.() -> Unit, elseInit: TypeSpec.Builder.() -> Unit): TypeSpec.Builder {
    return if (bool) this.apply(ifInit) else this.apply(elseInit)
}

fun <U> TypeSpec.Builder.forAdd(collection: Collection<U>, init: TypeSpec.Builder.(U) -> Unit): TypeSpec.Builder {
    collection.forEach { this.apply { init(it) } }
    return this
}
