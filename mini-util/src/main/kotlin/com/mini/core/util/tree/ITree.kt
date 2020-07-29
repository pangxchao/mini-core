@file:Suppress("unused")

package com.mini.core.util.tree

interface ITree<T : ITree<T>> {
    val children: MutableList<T>

    fun addChild(child: T) {
        children.add(child)
    }

    var parentId: Long?
    var name: String?
    var id: Long
}