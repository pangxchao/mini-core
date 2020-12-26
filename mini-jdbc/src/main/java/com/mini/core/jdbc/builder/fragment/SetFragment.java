package com.mini.core.jdbc.builder.fragment;

public interface SetFragment<T extends SetFragment<T>> {
    T setNative(String column, String value, Object... arg);

    T set(String column, Object arg);
}
