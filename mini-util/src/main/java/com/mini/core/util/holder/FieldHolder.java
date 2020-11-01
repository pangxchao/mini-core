package com.mini.core.util.holder;

import com.mini.core.util.ThrowableKt;

import javax.annotation.Nonnull;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public final class FieldHolder<T> implements Serializable {
    private final PropertyDescriptor descriptor;
    private final Method getter, setter;
    private final Field field;

    private FieldHolder(@Nonnull Class<T> type, @Nonnull PropertyDescriptor des) {
        this.field = findField(des, type);
        setter = des.getWriteMethod();
        getter = des.getReadMethod();
        descriptor = des;
    }

    public final <A extends Annotation> A getAnnotation(Class<A> clazz) {
        return field == null ? null : field.getAnnotation(clazz);
    }

    /**
     * 调用属性的Setter方法
     *
     * @param instance 属性对象
     * @param value    属性值
     */
    public final void setValue(Object instance, Object value) {
        try {
            if (this.setter == null) return;
            setter.invoke(instance, value);
        } catch (ReflectiveOperationException e) {
            throw ThrowableKt.hidden(e);
        }
    }

    /**
     * 调用属性的 Getter 方法
     *
     * @param instance 属性对象
     * @return 方法返回值
     */
    public final Object getValue(Object instance) {
        try {
            if (getter == null) return null;
            return getter.invoke(instance);
        } catch (ReflectiveOperationException e) {
            throw ThrowableKt.hidden(e);
        }
    }

    /**
     * 是否有 Getter 方法
     *
     * @return true-是
     */
    public final boolean hasGetter() {
        return this.getter != null;
    }

    /**
     * 是否有 Setter 方法
     *
     * @return true-是
     */
    public final boolean hasSetter() {
        return this.setter != null;
    }

    public final Field getField() {
        return field;
    }

    public final Class<?> getType() {
        return descriptor.getPropertyType();
    }

    public final String getName() {
        return descriptor.getName();
    }

    static synchronized <T> void create(@Nonnull ClassHolder<T> h) {
        try {
            ofNullable(Introspector.getBeanInfo(h.getType()))
                    .map(BeanInfo::getPropertyDescriptors).stream()
                    .flatMap(Stream::of)
                    .filter(des -> !"class".equals(des.getName()))
                    .map(d -> new FieldHolder<>(h.getType(), d))
                    .forEach(h::addField);
        } catch (IntrospectionException e) {
            throw ThrowableKt.hidden(e);
        }
    }

    private static Field findField(PropertyDescriptor des, Class<?> type) {
        for (Class<?> t = type; t != null; t = t.getSuperclass()) {
            try {
                final Field field = t.getDeclaredField(des.getName());
                if (des.getPropertyType().isAssignableFrom(field.getType())) {
                    return field;
                }
            } catch (NoSuchFieldException | SecurityException ignored) {
            }
        }
        return null;
    }
}