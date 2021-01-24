@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.mini.core.util

import com.mini.core.util.tree.ITree
import java.util.*

object TreeUtil {

    @JvmStatic
    @JvmOverloads
    fun <T : ITree<T>> buildTree(list: List<T>?, parentId: Long? = null): List<T>? {
        return list?.filter { parentId ?: 0 == it.parentId ?: 0 }?.map {
            it.children = buildTree(list, it.id)
            return@map it
        }?.takeIf { it.isNotEmpty() }
    }

    @JvmStatic
    fun <T : ITree<T>> getList(tree: List<T>?): List<T> {
        val list: MutableList<T> = mutableListOf()
        tree?.forEachIndexed { _: Int, child: T ->
            child.children?.let { it: List<T> ->
                getList(it).let { all ->
                    list.addAll(all)
                }
            }
            list.add(child)
        }
        return list
    }

}