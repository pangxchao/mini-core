package com.mini.core.util.holder;


import com.mini.core.util.ThrowableKt;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public final class ClassHolder<T> {
    private static final Map<Class<?>, ClassHolder<?>> M = new HashMap<>();
    private final Map<String, FieldHolder<T>> fields = new HashMap<>();
    private final Class<T> type;
    private final T instance;


    private ClassHolder(Class<T> type) throws ReflectiveOperationException {
        Constructor<T> constructor = type.getConstructor();
        instance = constructor.newInstance();
        this.type = type;
    }


    public Class<T> getType() {
        return type;
    }

    public T getInstance() {
        return instance;
    }

    public Map<String, FieldHolder<T>> getFields() {
        return fields;
    }

    public void addField(FieldHolder<T> field) {
        this.fields.put(field.getName(), field);
    }

    public <A extends Annotation> A[] getAnnotationsByType(Class<A> clazz) {
        return type.getAnnotationsByType(clazz);
    }

    public <A extends Annotation> A getAnnotation(Class<A> clazz) {
        return type.getAnnotation(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> ClassHolder<T> create(Class<T> type) {
        return (ClassHolder<T>) M.computeIfAbsent(type, key -> {
            try {
                ClassHolder<T> holder = new ClassHolder<>(type);
                FieldHolder.create(holder);
                return holder;
            } catch (ReflectiveOperationException e) {
                throw ThrowableKt.hidden(e);
            }
        });
    }


}