package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public interface AbstractMap<T extends AbstractData<T>> extends Serializable {

    void setMap(Map<String, T> map);

    @NotNull
    Map<String, T> getMap();

    T get(String key);

    @Nullable
    default T put(String key, T value) {
        return getMap().put(key, value);
    }

    @SuppressWarnings("UnusedReturnValue")
    default T remove(String key) {
        return getMap().remove(key);
    }

    default void putAll(@NotNull Map<? extends String, ? extends T> m) {
        getMap().putAll(m);
    }

    @NotNull
    default Collection<T> values() {
        return getMap().values();
    }

    default T computeIfAbsent(String groupName, Function<? super String, ? extends T> function) {
        return getMap().computeIfAbsent(groupName, function);
    }

    default T putIfAbsent(String groupName, T item) {
        return getMap().putIfAbsent(groupName, item);
    }

    default T add(@NotNull T value) {
        return put(value.getName(), value);
    }
}
