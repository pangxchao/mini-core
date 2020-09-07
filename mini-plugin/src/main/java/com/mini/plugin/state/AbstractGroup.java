package com.mini.plugin.state;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public interface AbstractGroup<T extends AbstractData<T>, H extends AbstractGroup<T, H>> extends AbstractData<H> {

    void setMap(Map<String, T> map);

    @NotNull
    Map<String, T> getMap();

    default Collection<T> getValues() {
        return getMap().values();
    }

    default T put(String key, T value) {
        return getMap().put(key, value);
    }

    default T get(String key) {
        return getMap().get(key);
    }

    default T computeIfAbsent(String name, Function<? super String, ? extends T> function) {
        return this.getMap().computeIfAbsent(name, function);
    }

    default T putIfAbsent(String groupName, T item) {
        return this.getMap().putIfAbsent(groupName, item);
    }

    default T add(T t) {
        return put(t.getName(), t);
    }
}
