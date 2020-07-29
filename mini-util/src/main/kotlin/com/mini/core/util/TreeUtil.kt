@file:Suppress("unused")

package com.mini.core.util

object TreeUtil {
//    fun <T : ITree<T>?> buildTree(@Nonnull list: List<T>): List<T> {
//        return doBuildTree(list, 0)
//    }
//
//    fun <T : ITree<T>?> buildTree(@Nonnull list: List<T>, parentId: Long?): List<T> {
//
//        return if (parentId ?: 0 > 0) list.stream().filter { item: T ->
//            item!!.getId() == parentId
//        }.peek { item: T -> item!!.setChildren(doBuildTree(list, parentId)) }
//                .collect(Collectors.toList()) else doBuildTree(list, 0)
//    }
//
//    private fun <T : ITree<T>?> doBuildTree(@Nonnull list: List<T>, parentId: Long?): List<T> {
//        return list.stream().filter { v: T -> parentId == defaultIfNull(v!!.getParentId(), 0L) }
//                .peek { item: T -> item!!.setChildren(doBuildTree(list, item.getId())) }
//                .collect(Collectors.toList())
//    }
//
//    fun <T : ITree<T>?> getList(@Nonnull tree: List<T>): List<T> {
//        return tree.stream().flatMap(Function<T, Stream<out T>> { obj: T -> doGetList() })
//                .collect(Collectors.toList())
//    }
//
//    private fun <T : ITree<T>?> doGetList(item: T): Stream<T> {
//        return Stream.concat(Stream.of(item), Optional.ofNullable(item!!.getChildren())
//                .filter { c: List<T> -> !c.isEmpty() }.stream()
//                .flatMap { obj: List<T> -> obj.stream() }
//                .flatMap { obj: T -> doGetList() })
//    }
}