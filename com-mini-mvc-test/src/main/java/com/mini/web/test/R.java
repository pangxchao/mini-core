package com.mini.web.test;

import com.mini.util.map.MiniConcurrentHashMap;
import com.mini.util.map.MiniMap;

public final class R {
    private static final MiniMap<Integer> PARAMETERS = new MiniConcurrentHashMap<>();

    public static final int KEY_MARK = 1;
    public static final int KEY_PATH = 2;
    public static final int KEY_URL = 3;

    public static void put(int key, Object value) {
        PARAMETERS.put(key, value);
    }

    public static String get(int key) {
        return PARAMETERS.getAsString(key);
    }

    public static String getMark() {
        return get(KEY_MARK);
    }

    public static String getPath() {
        return get(KEY_PATH);
    }

    public static String getUrl() {
        return get(KEY_URL);
    }
}
