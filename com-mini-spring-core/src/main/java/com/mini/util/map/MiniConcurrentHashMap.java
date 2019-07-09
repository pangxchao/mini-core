package com.mini.util.map;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MiniConcurrentHashMap<K> extends ConcurrentHashMap<K, Object> implements MiniMap<K>, Serializable {
    private static final long serialVersionUID = 8401014798553700478L;

    public MiniConcurrentHashMap() {
    }

    public MiniConcurrentHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MiniConcurrentHashMap(Map<? extends K, ?> m) {
        super(m);
    }

    public MiniConcurrentHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
}
