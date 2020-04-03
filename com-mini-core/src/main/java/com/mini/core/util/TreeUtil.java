package com.mini.core.util;

import com.mini.core.util.tree.ITree;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public final class TreeUtil {
	public static <T extends ITree<T>> List<T> buildTree(@Nonnull List<T> list) {
		return TreeUtil.doBuildTree(list, 0);
	}
	
	public static <T extends ITree<T>> List<T> buildTree(@Nonnull List<T> list, long parentId) {
		return parentId > 0 ? list.stream().filter(item -> item.getId() == parentId)
				.peek(item -> item.setChildren(doBuildTree(list, parentId)))
				.collect(Collectors.toList()) : doBuildTree(list, 0);
	}
	
	private static <T extends ITree<T>> List<T> doBuildTree(@Nonnull List<T> list, long parentId) {
		return list.stream().filter(item -> eq(parentId, item.getParentId())).peek(item -> {
			item.setChildren(doBuildTree(list, item.getId())); //
		}).collect(Collectors.toList());
	}
	
	private static boolean eq(long a, Long b) {
		return (b == null ? 0 : b) == a;
	}
}
