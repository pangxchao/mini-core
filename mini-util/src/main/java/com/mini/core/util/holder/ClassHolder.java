package com.mini.core.util.holder;


import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ClassHolder<T> {
    private static final Map<Class<?>, ClassHolder<?>> M = new HashMap<>();
    private final Map<String, FieldHolder<T>> fields = new HashMap<>();
    private final Class<T> type;
    private T instance;


    private ClassHolder(Class<T> type) {
        this.type = type;
    }

    public final String getName() {
        return type.getSimpleName();
    }

    public Class<T> getType() {
        return type;
    }

    public final T getInstance() throws ReflectiveOperationException {
        if (Objects.isNull(ClassHolder.this.instance)) {
            var constructor = type.getConstructor();
            instance = constructor.newInstance();
        }
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
            ClassHolder<T> holder = new ClassHolder<>(type);
            FieldHolder.create(holder);
            return holder;
        });
    }


}