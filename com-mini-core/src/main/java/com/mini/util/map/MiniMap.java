package com.mini.util.map;

import java.util.Map;

public interface MiniMap<K> extends Map<K, Object> {
    default <T> T getAs(K key, Class<T> clazz) {
        Object value = this.get(key);
        if (value == null) return null;
        if (clazz.isAssignableFrom(value.getClass())) {
            return clazz.cast(value);
        } else return null;

    }

    default String getAsString(K key) {
        return getAs(key, String.class);
    }

    default Long getAsLong(K key) {
        Object value = this.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.longValue();
        }
        String str = value.toString();
        return Long.valueOf(str);
    }

    default long getAsLongVal(K key) {
        Long val = getAsLong(key);
        return val == null ? 0 : val;
    }

    default Integer getAsInteger(K key) {
        Object value = this.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.intValue();
        }
        String str = value.toString();
        return Integer.valueOf(str);
    }

    default int getAsIntVal(K key) {
        Integer val = getAsInteger(key);
        return val == null ? 0 : val;
    }

    default Short getAsShort(K key) {
        Object value = this.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.shortValue();
        }
        String str = value.toString();
        return Short.valueOf(str);
    }

    default short getAsShortVal(K key) {
        Short val = getAsShort(key);
        return val == null ? (short) 0 : val;
    }

    default Byte getAsByte(K key) {
        Object value = this.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.byteValue();
        }
        String str = value.toString();
        return Byte.valueOf(str);
    }

    default byte getAsByteVal(K key) {
        Byte val = getAsByte(key);
        return val == null ? (byte) 0 : val;
    }

    default Double getAsDouble(K key) {
        Object value = this.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.doubleValue();
        }
        String str = value.toString();
        return Double.valueOf(str);
    }

    default double getAsDoubleVal(K key) {
        Double val = getAsDouble(key);
        return val == null ? 0D : val;
    }


    default Float getAsFloat(K key) {
        Object value = this.get(key);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.floatValue();
        }
        String str = value.toString();
        return Float.valueOf(str);
    }

    default float getAsFloatVal(K key) {
        Float val = getAsFloat(key);
        return val == null ? 0F : val;
    }


    default Boolean getAsBoolean(K key) {
        Object value = this.get(key);
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

    default boolean getAsBooleanVal(K key) {
        Boolean val = getAsBoolean(key);
        return val == null ? false : val;
    }
}
