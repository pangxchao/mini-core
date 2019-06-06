package com.mini.util.lang;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MiniMap<K> extends ConcurrentHashMap<K, Object> implements Serializable {
    private static final long serialVersionUID = 8401014798553700478L;

    public MiniMap() {
    }

    public MiniMap(int initialCapacity) {
        super(initialCapacity);
    }

    public MiniMap(Map<? extends K, ?> m) {
        super(m);
    }

    public MiniMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public <T> T getAs(K key, Class<T> clazz) {
        Object value = super.get(key);
        if (value == null) return null;
        if (clazz.isAssignableFrom(value.getClass())) {
            return clazz.cast(value);
        } else return null;

    }

    public String getAsString(K key) {
        return getAs(key, String.class);
    }

    public Long getAsLong(K key) {
        Object value = super.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.longValue();
        }
        String str = value.toString();
        return Long.valueOf(str);
    }

    public long getAsLongVal(K key) {
        Long val = getAsLong(key);
        return val == null ? 0 : val;
    }

    public Integer getAsInteger(K key) {
        Object value = super.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.intValue();
        }
        String str = value.toString();
        return Integer.valueOf(str);
    }

    public int getAsIntVal(K key) {
        Integer val = getAsInteger(key);
        return val == null ? 0 : val;
    }

    public Short getAsShort(K key) {
        Object value = super.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.shortValue();
        }
        String str = value.toString();
        return Short.valueOf(str);
    }

    public short getAsShortVal(K key) {
        Short val = getAsShort(key);
        return val == null ? (short) 0 : val;
    }

    public Byte getAsByte(K key) {
        Object value = super.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.byteValue();
        }
        String str = value.toString();
        return Byte.valueOf(str);
    }

    public byte getAsByteVal(K key) {
        Byte val = getAsByte(key);
        return val == null ? (byte) 0 : val;
    }

    public Double getAsDouble(K key) {
        Object value = super.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.doubleValue();
        }
        String str = value.toString();
        return Double.valueOf(str);
    }

    public double getAsDoubleVal(K key) {
        Double val = getAsDouble(key);
        return val == null ? 0D : val;
    }


    public Float getAsFloat(K key) {
        Object value = super.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.floatValue();
        }
        String str = value.toString();
        return Float.valueOf(str);
    }

    public float getAsFloatVal(K key) {
        Float val = getAsFloat(key);
        return val == null ? 0F : val;
    }


    public Boolean getAsBoolean(K key) {
        Object value = super.get(key);
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

    public boolean getAsBooleanVal(K key) {
        Boolean val = getAsBoolean(key);
        return val == null ? false : val;
    }
}
