@file:Suppress("unused")

package com.mini.plugin.builder.kotlinpoet

import com.squareup.kotlinpoet.PropertySpec


fun PropertySpec.Builder.ifAdd(bool: Boolean, init: PropertySpec.Builder.() -> Unit): PropertySpec.Builder {
    return if (bool) this.apply(init) else this
}

fun PropertySpec.Builder.ifElseAdd(bool: Boolean, ifInit: PropertySpec.Builder.() -> Unit, elseInit: PropertySpec.Builder.() -> Unit): PropertySpec.Builder {
    return if (bool) this.apply(ifInit) else this.apply(elseInit)
}

fun <U> PropertySpec.Builder.forAdd(collection: Collection<U>, init: PropertySpec.Builder.(U) -> Unit): PropertySpec.Builder {
    collection.forEach { this.apply { init(it) } }
    return this
}