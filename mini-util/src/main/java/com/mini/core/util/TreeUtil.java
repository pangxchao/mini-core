package com.mini.core.util;

import com.mini.core.util.tree.ITree;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNullElse;
import static java.util.Optional.ofNullable;

public class TreeUtil {
    @Nonnull
    private static <T extends ITree<T>> List<T> doBuild(@Nullable List<T> list, Long parentId) {
        final java.util.ArrayList<T> result = new java.util.ArrayList<>();
        ofNullable(list).stream().flatMap(Collection::stream).forEach(item -> {
            Long itemParentId = requireNonNullElse(item.getParentId(), 0L);
            Long targetParentId = requireNonNullElse(parentId, 0L);
            if (Objects.equals(itemParentId, targetParentId)) {
                item.setChildren(doBuild(list, item.getId()));
                result.add(item);
            }
        });
        return result;
    }

    @Nonnull
    public static <T extends ITree<T>> List<T> build(@Nullable List<T> list, Long parentId) {
        if (parentId == null || parentId == 0) {
            return doBuild(list, parentId);
        }
        final java.util.ArrayList<T> result = new java.util.ArrayList<>();
        ofNullable(list).stream().flatMap(Collection::stream).forEach(item -> {
            if (Objects.equals(parentId, item.getId())) {
                item.setChildren(doBuild(list, item.getId()));
                result.add(item);
            }
        });
        return result;
    }

    @Nonnull
    public static <T extends ITree<T>> List<T> build(@Nullable List<T> list) {
        return doBuild(list, null);
    }

    @Nonnull
    public static <T extends ITree<T>> List<T> getList(@Nullable List<T> list) {
        final java.util.ArrayList<T> result = new java.util.ArrayList<>();
        ofNullable(list).stream().flatMap(Collection::stream).forEach(item -> {
            result.addAll(getList(item.getChildren()));
            item.setChildren(null);
            result.add(item);
        });
        return result;
    }
}