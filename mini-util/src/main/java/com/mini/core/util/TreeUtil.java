package com.mini.core.util;

import com.mini.core.util.tree.ITree;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNullElse;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public final class TreeUtil {

    public static <T extends ITree<T>> List<T> buildTree(@Nonnull List<T> list) {
        return TreeUtil.doBuildTree(list, 0);
    }

    public static <T extends ITree<T>> List<T> buildTree(@Nonnull List<T> list, Long parentId) {
        return requireNonNullElse(parentId, 0L) > 0 ? list.stream().filter(item -> {
            return Objects.equals(item.getId(), parentId); //
        }).peek(item -> item.setChildren(doBuildTree(list, parentId)))
                .collect(toList()) : doBuildTree(list, 0);
    }

    private static <T extends ITree<T>> List<T> doBuildTree(@Nonnull List<T> list, long parentId) {
        return list.stream().filter(v -> parentId == requireNonNullElse(v.getParentId(), 0L))
                .peek(item -> item.setChildren(doBuildTree(list, item.getId())))
                .collect(toList());
    }

    public static <T extends ITree<T>> List<T> getList(@Nonnull List<T> tree) {
        return tree.stream().flatMap(TreeUtil::doGetList)
                .collect(toList());
    }

    private static <T extends ITree<T>> Stream<T> doGetList(T item) {
        return concat(of(item), ofNullable(item.getChildren())
                .filter(c -> !c.isEmpty()).stream()
                .flatMap(Collection::stream)
                .flatMap(TreeUtil::doGetList));
    }
}