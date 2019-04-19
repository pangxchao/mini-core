package com.mini.spring.model;

import java.util.HashMap;
import java.util.Map;

public class MapJsonModel extends HashMap<String, Object> {
    private static final long serialVersionUID = 5525118096722223821L;

    public MapJsonModel addData(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public MapJsonModel addAllData(Map<? extends String, ?> m) {
        super.putAll(m);
        return this;
    }

}
