package com.mini.web.config;

import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

@Component
public final class ListenerConfigure implements Serializable {
    private static final long serialVersionUID = -7621659406124770826L;
    private final Set<EventListener> listeners = new HashSet<>();

    /**
     * 添加一个监听器
     * @param listener 监听器
     * @return {@Code this}
     */
    public ListenerConfigure addListener(EventListener listener) {
        listeners.add(listener);
        return this;
    }

    /**
     * Gets the value of listenerList.
     * @return The value of listenerList
     */
    public Set<EventListener> getListeners() {
        return listeners;
    }

}
