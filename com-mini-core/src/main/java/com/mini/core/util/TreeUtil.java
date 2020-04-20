package com.mini.core.util;

import com.mini.core.util.tree.ITree;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public final class TreeUtil {
	
	public static <T extends ITree<T>> List<T> buildTree(@Nonnull List<T> list) {
		return TreeUtil.doBuildTree(list, 0);
	}
	
	public static <T extends ITree<T>> List<T> buildTree(@Nonnull List<T> list, Long parentId) {
		return n_eq(parentId) ? list.stream().filter(item -> eq(item.getId(), parentId))
				.peek(item -> item.setChildren(doBuildTree(list, parentId)))
				.collect(Collectors.toList()) : doBuildTree(list, 0);
	}
	
	private static <T extends ITree<T>> List<T> doBuildTree(@Nonnull List<T> list, long parentId) {
		return list.stream().filter(item -> eq(parentId, item.getParentId())).peek(item -> {
			item.setChildren(doBuildTree(list, item.getId())); //
		}).collect(Collectors.toList());
	}
	
	private static boolean eq(long a, Long b) {
		return a == (b == null ? 0 : b);
	}
	
	private static boolean n_eq(Long b) {
		return 0 != (b == null ? 0 : b);
	}
	
	public static <T extends ITree<T>> List<T> getList(@Nonnull List<T> tree) {
		return tree.stream().flatMap(TreeUtil::doGetList)
				.collect(Collectors.toList());
	}
	
	private static <T extends ITree<T>> Stream<T> doGetList(T item) {
		return concat(of(item), ofNullable(item.getChildren())
				.filter(c -> !c.isEmpty()).stream()
				.flatMap(Collection::stream)
				.flatMap(TreeUtil::doGetList));
	}
	
}
