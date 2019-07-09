package com.mini.util.map;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MiniHashMap<K> extends HashMap<K, Object> implements MiniMap<K>, Serializable {
    private static final long serialVersionUID = -6446689795836255466L;


    public MiniHashMap() {
    }

    public MiniHashMap(int initialCapacity) {
        super(initialCapacity);
    }


    public MiniHashMap(Map<? extends K, ?> m) {
        super(m);
    }

    public MiniHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
}
