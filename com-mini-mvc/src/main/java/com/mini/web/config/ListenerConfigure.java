package com.mini.web.config;

import javax.inject.Singleton;
import java.io.Serializable;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

@Singleton
public final class ListenerConfigure implements Serializable {
    private static final long serialVersionUID = -7621659406124770826L;
    private final Set<Class<? extends EventListener>> listeners = new HashSet<>();

    /**
     * 添加一个监听器
     * @param listener 监听器
     * @return {@Code this}
     */
    public ListenerConfigure addListener(Class<? extends EventListener> listener) {
        listeners.add(listener);
        return this;
    }

    /**
     * Gets the value of listenerList.
     * @return The value of listenerList
     */
    public Set<Class<? extends EventListener>> getListeners() {
        return listeners;
    }

}
