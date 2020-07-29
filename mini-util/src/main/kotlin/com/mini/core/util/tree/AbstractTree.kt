@file:Suppress("unused")

package com.mini.core.util.tree

abstract class AbstractTree<T : ITree<T>> : ITree<T> {
    final override val children: MutableList<T> = mutableListOf()

    final override fun addChild(child: T) {
        super.addChild(child)
    }
}