package com.mini.web.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.mini.util.TypeUtil.castToLongVal;

public final class R {
    private static final Map<Integer, String> MAP = new ConcurrentHashMap<>();

    public static final int KEY_MARK = 1;
    public static final int KEY_PATH = 2;
    public static final int KEY_URL = 3;

    public static void put(int key, String value) {
        MAP.put(key, value);
    }

    public static String get(int key) {
        return MAP.get(key);
    }

    public static long getAsLong(int key) {
        return castToLongVal(get(key));
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
