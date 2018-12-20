package com.mini.util.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SQLValues {
    private final Map<String, Object> values = new HashMap<>();

    public SQLValues() {}

    public SQLValues(String name, Object value) {
        put(name, value);
    }

    public SQLValues put(String name, Object value) {
        values.put(name, value);
        return this;
    }

    public SQLValues putNull(String name) {
        return put(name, null);
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public void remove(String key) {
        values.remove(key);
    }

    public void clear() {
        values.clear();
    }

    public boolean containsKey(String key) {
        return values.containsKey(key);
    }

    public Object get(String key) {
        return values.get(key);
    }

    public String getAsString(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        return value.toString();
    }

    public Long getAsLong(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.longValue();
        }
        String str = value.toString();
        return Long.valueOf(str);
    }

    public Integer getAsInteger(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.intValue();
        }
        String str = value.toString();
        return Integer.valueOf(str);
    }

    public Short getAsShort(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.shortValue();
        }
        String str = value.toString();
        return Short.valueOf(str);
    }

    public Byte getAsByte(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.byteValue();
        }
        String str = value.toString();
        return Byte.valueOf(str);
    }

    public Double getAsDouble(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.doubleValue();
        }
        String str = value.toString();
        return Double.valueOf(str);
    }

    public Float getAsFloat(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.floatValue();
        }
        String str = value.toString();
        return Float.valueOf(str);
    }


    public Boolean getAsBoolean(String key) {
        Object value = values.get(key);
        if (value == null) return null;
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            Number num = (Number) value;
            return !num.equals(0);
        }
        String str = value.toString();
        return Boolean.valueOf(str);
    }

    public byte[] getAsByteArray(String key) {
        Object value = values.get(key);
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else {
            return null;
        }
    }

    public Set<Map.Entry<String, Object>> valueSet() {
        return values.entrySet();
    }

    public Set<String> keySet() {
        return values.keySet();
    }
}
