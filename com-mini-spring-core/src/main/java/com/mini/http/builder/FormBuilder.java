package com.mini.http.builder;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import javax.annotation.Nonnull;
import java.util.Iterator;

public final class FormBuilder<V> extends AbstractBuilder<FormBuilder<V>, V> {
    private final FormBody.Builder builder = new FormBody.Builder();

    @Override
    protected RequestBody getRequestBody() {
        return builder.build();
    }

    @Override
    protected FormBuilder<V> getSelf() {
        return this;
    }

    public FormBuilder<V> add(@Nonnull String name, Object value) {
        Object v = value == null ? "" : value;
        builder.add(name, String.valueOf(v));
        return getSelf();
    }

    public FormBuilder<V> addEncoded(@Nonnull String name, Object value) {
        Object v = value == null ? "" : value;
        builder.addEncoded(name, String.valueOf(v));
        return getSelf();
    }

    public <T> FormBuilder<V> addIterable(String name, Iterable<T> array) {
        for (T t : array) add(name, t);
        return getSelf();
    }

    public <T> FormBuilder<V> addIterator(String name, Iterator<T> array) {
        while (array.hasNext()) add(name, array.next());
        return getSelf();
    }

    public <T> FormBuilder<V> addArray(String name, T[] array) {
        for (T t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, long[] array) {
        for (long t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, int[] array) {
        for (int t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, short[] array) {
        for (short t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, byte[] array) {
        for (byte t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, double[] array) {
        for (double t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, float[] array) {
        for (float t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, boolean[] array) {
        for (boolean t : array) add(name, t);
        return getSelf();
    }

    public FormBuilder<V> addArray(String name, char[] array) {
        for (char t : array) add(name, t);
        return getSelf();
    }

}

