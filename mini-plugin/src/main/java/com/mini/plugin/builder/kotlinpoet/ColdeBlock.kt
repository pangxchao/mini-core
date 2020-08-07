@file:Suppress("unused")

package com.mini.plugin.builder.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock


fun CodeBlock.Builder.ifAdd(bool: Boolean, init: CodeBlock.Builder.() -> Unit): CodeBlock.Builder {
    return if (bool) this.apply(init) else this
}

fun CodeBlock.Builder.ifElseAdd(bool: Boolean, ifInit: CodeBlock.Builder.() -> Unit, elseInit: CodeBlock.Builder.() -> Unit): CodeBlock.Builder {
    return if (bool) this.apply(ifInit) else this.apply(elseInit)
}

fun <U> CodeBlock.Builder.forAdd(collection: Collection<U>, init: CodeBlock.Builder.(U) -> Unit): CodeBlock.Builder {
    collection.forEach { this.apply { init(it) } }
    return this
}
