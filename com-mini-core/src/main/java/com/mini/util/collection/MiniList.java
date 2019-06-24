package com.mini.util.collection;

import java.util.List;

public interface MiniList extends List<Object> {

    default <T> T getAs(int i, Class<T> clazz) {
        Object value = this.get(i);
        if (value == null) return null;
        if (clazz.isAssignableFrom(value.getClass())) {
            return clazz.cast(value);
        } else return null;

    }

    default String getAsString(int i) {
        return getAs(i, String.class);
    }

    default Long getAsLong(int i) {
        Object value = this.get(i);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.longValue();
        }
        String str = value.toString();
        return Long.valueOf(str);
    }

    default long getAsLongVal(int i) {
        Long val = getAsLong(i);
        return val == null ? 0 : val;
    }

    default Integer getAsInteger(int i) {
        Object value = this.get(i);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.intValue();
        }
        String str = value.toString();
        return Integer.valueOf(str);
    }

    default int getAsIntVal(int i) {
        Integer val = getAsInteger(i);
        return val == null ? 0 : val;
    }

    default Short getAsShort(int i) {
        Object value = this.get(i);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.shortValue();
        }
        String str = value.toString();
        return Short.valueOf(str);
    }

    default short getAsShortVal(int i) {
        Short val = getAsShort(i);
        return val == null ? (short) 0 : val;
    }

    default Byte getAsByte(int i) {
        Object value = this.get(i);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.byteValue();
        }
        String str = value.toString();
        return Byte.valueOf(str);
    }

    default byte getAsByteVal(int i) {
        Byte val = getAsByte(i);
        return val == null ? (byte) 0 : val;
    }

    default Double getAsDouble(int i) {
        Object value = this.get(i);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.doubleValue();
        }
        String str = value.toString();
        return Double.valueOf(str);
    }

    default double getAsDoubleVal(int i) {
        Double val = getAsDouble(i);
        return val == null ? 0D : val;
    }


    default Float getAsFloat(int i) {
        Object value = this.get(i);
        if (value == null) return null;
        if (value instanceof Number) {
            Number num = (Number) value;
            return num.floatValue();
        }
        String str = value.toString();
        return Float.valueOf(str);
    }

    default float getAsFloatVal(int i) {
        Float val = getAsFloat(i);
        return val == null ? 0F : val;
    }


    default Boolean getAsBoolean(int i) {
        Object value = this.get(i);
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

    default boolean getAsBooleanVal(int i) {
        Boolean val = getAsBoolean(i);
        return val == null ? false : val;
    }
}
