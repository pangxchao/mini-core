package com.mini.core.util.holder;

import com.mini.core.util.ThrowsUtil;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.hash;

public final class ClassHolder<T> implements Serializable, EventListener {
	private static final Map<Class<?>, ClassHolder<?>> M = new HashMap<>();
	private final Map<String, FieldHolder<T>> fields = new HashMap<>();
	private final Class<T> type;
	
	private ClassHolder(@Nonnull Class<T> type) {
		this.type = type;
	}
	
	public final <A extends Annotation> A[] getAnnotationsByType(Class<A> clazz) {
		return this.type.getAnnotationsByType(clazz);
	}
	
	public final <A extends Annotation> A getAnnotation(Class<A> clazz) {
		return type.getAnnotation(clazz);
	}
	
	public final ClassHolder<T> addField(@Nonnull FieldHolder<T> field) {
		this.fields.put(field.getName(), field);
		return this;
	}
	
	public final FieldHolder<T> getField(String fieldName) {
		return fields.get(fieldName);
	}
	
	public final boolean hasField(FieldHolder<T> holder) {
		return fields.get(holder.getName()) != null;
	}
	
	public final boolean hasField(String fieldName) {
		return fields.get(fieldName) != null;
	}
	
	public final Collection<FieldHolder<T>> fields() {
		return fields.values();
	}
	
	public final T createInstance() {
		try {
			var constructor = type.getConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			throw ThrowsUtil.hidden(e);
		}
		
	}
	
	public final Class<T> getType() {
		return type;
	}
	
	@Override
	public int hashCode() {
		return hash(fields);
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized <T> ClassHolder<T> create(Class<T> type) {
		return (ClassHolder<T>) M.computeIfAbsent(type, key -> {
			var holder = new ClassHolder<>(type);
			FieldHolder.create(holder);
			return holder;
		});
	}
}
