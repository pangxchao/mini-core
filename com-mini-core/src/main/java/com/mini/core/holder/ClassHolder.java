package com.mini.core.holder;

import com.mini.core.holder.jdbc.Join;
import com.mini.core.holder.jdbc.Table;
import com.mini.core.holder.web.Param;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.util.ThrowsUtil;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.*;

import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.Validate.isTrue;

public final class ClassHolder<T> implements Serializable, EventListener {
	private static final Map<Class<?>, ClassHolder<?>> M = new HashMap<>();
	private final Map<String, FieldHolder<T>> columns = new HashMap<>();
	private final Map<String, FieldHolder<T>> fields = new HashMap<>();
	private final Class<T> type;
	private final Join[] joins;
	private final Param param;
	private final Table table;
	
	@Override
	public int hashCode() {
		int result = Objects.hash(columns, fields, type, param, table);
		result = 31 * result + Arrays.hashCode(joins);
		return result;
	}
	
	private ClassHolder(@Nonnull Class<T> type) {
		// @Join 注解
		joins = type.getAnnotationsByType(Join.class);
		// @Table 注解
		table = type.getAnnotation(Table.class);
		// @Param 注解
		param = type.getAnnotation(Param.class);
		// 类型
		this.type = type;
	}
	
	public final Collection<FieldHolder<T>> columns() {
		return columns.values();
	}
	
	public final Collection<FieldHolder<T>> fields() {
		return fields.values();
	}
	
	public final void addColumnHolder(FieldHolder<T> holder) {
		this.fields.put(holder.getFieldName(), holder);
		Optional.of(holder).filter(FieldHolder::hasColumn)
				.map(FieldHolder::getColumnName)
				.ifPresent(n -> columns.put(n, holder));
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
	
	public final boolean hasTable() {
		return this.table != null;
	}
	
	public final boolean hasParam() {
		return this.param != null;
	}
	
	public final ClassHolder<T> verifyTable() {
		isTrue(hasTable(), "%s not found @Table", type);
		return this;
	}
	
	public final boolean hasField(String fieldName) {
		return fields.get(fieldName) != null;
	}
	
	public final boolean hasField(FieldHolder<T> holder) {
		return fields.get(holder.getFieldName()) != null;
	}
	
	public final boolean hasColumn(String columnName) {
		return columns.get(columnName) != null;
	}
	
	public final boolean hasColumn(FieldHolder<T> holder) {
		return hasColumn(holder.getColumnName());
	}
	
	public final boolean hasColumnsAnyMatchDel() {
		return columns().stream().anyMatch(FieldHolder::hasDel);
	}
	
	@Nonnull
	public final FieldHolder<T> getField(String fieldName) {
		return Optional.ofNullable(fields.get(fieldName))
				.orElseThrow();
	}
	
	@Nonnull
	public final FieldHolder<T> getColumn(String columnName) {
		return Optional.ofNullable(columns.get(columnName))
				.orElseThrow();
	}
	
	public final ClassHolder<T> replace(SQLBuilder builder) {
		builder.replaceInto(table.value());
		return this;
	}
	
	public final ClassHolder<T> insert(SQLBuilder builder) {
		builder.insertInto(table.value());
		return this;
	}
	
	public final ClassHolder<T> delete(SQLBuilder builder) {
		builder.delete(table.value());
		return this;
	}
	
	public final ClassHolder<T> update(SQLBuilder builder) {
		builder.update(table.value());
		return this;
	}
	
	public final ClassHolder<T> select(SQLBuilder builder) {
		this.columns().forEach(t -> t.select(builder));
		return this;
	}
	
	public final ClassHolder<T> from(SQLBuilder builder) {
		builder.from(table.value());
		return this;
	}
	
	public final ClassHolder<T> join(SQLBuilder builder) {
		of(joins).filter(j -> hasColumn(j.column())).forEach(j -> {
			columns.get(j.column()).join(builder, j.type()); //
		});
		return this;
	}
	
	public final Class<T> getType() {
		return type;
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
