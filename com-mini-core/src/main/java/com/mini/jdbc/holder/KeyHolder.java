package com.mini.jdbc.holder;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface KeyHolder {
    Object get();

    void set(ResultSet rs) throws SQLException;

    default <T> T getAs(Class<T> type) {
        Object value = KeyHolder.this.get();
        if (value == null) return null;

        Class<?> clazz = value.getClass();
        if (type.isAssignableFrom(clazz)) {
            return type.cast(value);
        } else return null;
    }

    default String getAsString() {
        return getAs(String.class);
    }

    default Long getAsLong() {
        Object value = KeyHolder.this.get();
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.longValue();
        }
        String str = value.toString();
        return Long.valueOf(str);
    }

    default long getAsLongVal() {
        Long val = this.getAsLong();
        return val == null ? 0 : val;
    }

    default Integer getAsInteger() {
        Object value = KeyHolder.this.get();
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.intValue();
        }
        String str = value.toString();
        return Integer.valueOf(str);
    }

    default int getAsIntVal() {
        Integer val = this.getAsInteger();
        return val == null ? 0 : val;
    }

    default Short getAsShort() {
        Object value = KeyHolder.this.get();
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.shortValue();
        }
        String str = value.toString();
        return Short.valueOf(str);
    }

    default short getAsShortVal() {
        Short val = this.getAsShort();
        return val == null ? (short) 0 : val;
    }

    default Byte getAsByte() {
        Object value = KeyHolder.this.get();
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.byteValue();
        }
        String str = value.toString();
        return Byte.valueOf(str);
    }

    default byte getAsByteVal() {
        Byte val = this.getAsByte();
        return val == null ? (byte) 0 : val;
    }

    default Double getAsDouble() {
        Object value = this.get();
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.doubleValue();
        }
        String str = value.toString();
        return Double.valueOf(str);
    }

    default double getAsDoubleVal() {
        Double val = this.getAsDouble();
        return val == null ? 0D : val;
    }


    default Float getAsFloat() {
        Object value = KeyHolder.this.get();
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.floatValue();
        }
        String str = value.toString();
        return Float.valueOf(str);
    }

    default float getAsFloatVal() {
        Float val = this.getAsFloat();
        return val == null ? 0F : val;
    }


    default Boolean getAsBoolean() {
        Object value = KeyHolder.this.get();
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

    default boolean getAsBooleanVal() {
        Boolean val = this.getAsBoolean();
        return val == null ? false : val;
    }
}
