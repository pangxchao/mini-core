package com.mini.core.holder;

import com.mini.core.holder.jdbc.*;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.util.ThrowsUtil;

import javax.annotation.Nonnull;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.lang.String.format;

public final class FieldHolder<T> implements Serializable {
	private final PropertyDescriptor descriptor;
	private final CreateAt create;
	private final UpdateAt update;
	private final Method setter;
	private final Method getter;
	private final Column column;
	//private final Field field;
	private final Auto auto;
	private final Lock lock;
	private final Del del;
	private final Ref ref;
	private final Id id;
	
	private FieldHolder(@Nonnull Class<T> type, @Nonnull PropertyDescriptor des) {
		// 字段信息
		Field field = Optional.ofNullable(findField(des, type)) //
			.orElse(null);
		// 修改时间字段属性
		update = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(UpdateAt.class); //
		}).orElse(null);
		// 创建时间注解属性
		create = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(CreateAt.class); //
		}).orElse(null);
		// 获取字段信息
		column = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(Column.class); //
		}).orElse(null);
		// 数据库乐观锁字段属性
		lock = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(Lock.class); //
		}).orElse(null);
		// 自增长字段
		auto = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(Auto.class); //
		}).orElse(null);
		// 表示数据删除状态的字段
		del = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(Del.class); //
		}).orElse(null);
		// 外键注解属性
		ref = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(Ref.class); //
		}).orElse(null);
		// ID注解属性
		id = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(Id.class); //
		}).orElse(null);
		// Setter 方法
		setter = des.getWriteMethod();
		// Getter 方法
		getter = des.getReadMethod();
		// 属性描述
		descriptor = des;
	}
	
	/**
	 * 是否有 Getter 方法
	 * @return true-是
	 */
	public final boolean hasGetter() {
		return this.getter != null;
	}
	
	/**
	 * 是否有 Setter 方法
	 * @return true-是
	 */
	public final boolean hasSetter() {
		return this.setter != null;
	}
	
	/**
	 * 是否有 CreateAt 注解
	 * @return true-是
	 */
	@SuppressWarnings("All")
	public final boolean hasCreate() {
		return this.create != null;
	}
	
	/**
	 * 是否有 UpdateAt 注解
	 * @return true-是
	 */
	public final boolean hasUpdate() {
		return this.update != null;
	}
	
	/**
	 * 是否有 Column 注解
	 * @return true-是
	 */
	public final boolean hasColumn() {
		return this.column != null;
	}
	
	/**
	 * 是否有 Lock 注解
	 * @return true-是
	 */
	public final boolean hasLock() {
		return this.lock != null;
	}
	
	/**
	 * 是否有 Auto 注解
	 * @return true-是
	 */
	@SuppressWarnings("All")
	public final boolean hasAuto() {
		return this.auto != null;
	}
	
	/**
	 * 是否有 Ref 注解
	 * @return true-是
	 */
	public final boolean hasRef() {
		return this.ref != null;
	}
	
	/**
	 * 是否有 Del 注解
	 * @return true-是
	 */
	public final boolean hasDel() {
		return this.del != null;
	}
	
	/**
	 * 是否有 Id 注解
	 * @return true-是
	 */
	public final boolean hasId() {
		return this.id != null;
	}
	
	/**
	 * 调用属性的Setter方法
	 * @param instance 属性对象
	 * @param value    属性值
	 */
	public final void setValue(Object instance, Object value) {
		try {
			if (FieldHolder.this.hasSetter()) {
				setter.invoke(instance, value);
			}
		} catch (ReflectiveOperationException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	/**
	 * 调用属性的 Getter 方法
	 * @param instance 属性对象
	 * @return 方法返回值
	 */
	public final Object getValue(Object instance) {
		try {
			if (!FieldHolder.this.hasGetter()) {
				return null;
			}
			return getter.invoke(instance);
		} catch (ReflectiveOperationException e) {
			throw ThrowsUtil.hidden(e);
		}
	}
	
	@Nonnull
	public final String getColumnName() {
		return Optional.ofNullable(column)
			.map(Column::value)
			.orElseThrow();
	}
	
	@Nonnull
	public final String getFieldName() {
		return Optional.ofNullable(descriptor)
			.map(PropertyDescriptor::getName)
			.orElseThrow();
	}
	
	@Nonnull
	public final Class<?> getFieldType() {
		return Optional.ofNullable(descriptor)
			.map(PropertyDescriptor::getPropertyType)
			.orElseThrow();
	}
	
	@Nonnull
	public final String getRefColumn() {
		return Optional.ofNullable(ref)
			.map(Ref::column)
			.orElseThrow();
	}
	
	@Nonnull
	public final String getRefTable() {
		return Optional.ofNullable(ref)
			.map(Ref::table)
			.orElseThrow();
	}
	
	public final void select(SQLBuilder builder) {
		builder.select(getColumnName());
	}
	
	/**
	 * 添加 JOIN 语句
	 * @param builder {@link SQLBuilder}
	 */
	public final void join(SQLBuilder builder, Join.JoinType type) {
		type.execute(builder, format("%s ON %s = %s", //
			getRefTable(), getColumnName(), //
			getRefColumn()));
	}
	
	/**
	 * 添加相等条件限制
	 * @param builder {@link SQLBuilder}
	 */
	public final void whereId(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		Optional.ofNullable(this.id).ifPresent(lock -> {
			builder.where("%s = ?", getColumnName());
			consumer.accept(this);
		});
	}
	
	/**
	 * 添加相等条件限制
	 * @param builder {@link SQLBuilder}
	 */
	public final void whereLock(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		Optional.ofNullable(this.lock).ifPresent(lock -> {
			builder.where("%s = ?", getColumnName());
			consumer.accept(this);
		});
	}
	
	/**
	 * Insert Replace 添加修改字段键值
	 * @param builder {@link SQLBuilder}
	 */
	public final void values(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		builder.values(this.getColumnName());
		consumer.accept(this);
	}
	
	/**
	 * Update Set 字段键值
	 * @param builder {@link SQLBuilder}
	 */
	public final void set(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		builder.set("%s = ?", this.getColumnName());
		consumer.accept(this);
	}
	
	/**
	 * 排除删除标识限制
	 * @param builder {@link SQLBuilder}
	 */
	public final void whereDel(SQLBuilder builder) {
		Optional.ofNullable(this.del).ifPresent(del -> {
			builder.where("%s <> ?", getColumnName());
			builder.params(del.value());
		});
	}
	
	/**
	 * Update Set 字段键值
	 * @param builder {@link SQLBuilder}
	 */
	public final void setDel(SQLBuilder builder) {
		builder.set("%s = ?", this.getColumnName());
		builder.params(del.value());
	}
	
	public static synchronized <T> void create(@Nonnull ClassHolder<T> h) {
		try {
			Optional.ofNullable(Introspector.getBeanInfo(h.getType()))
				.map(BeanInfo::getPropertyDescriptors).stream()
				.flatMap(Stream::of)
				.filter(des -> !"class".equals(des.getName()))
				.map(d -> new FieldHolder<>(h.getType(), d))
				.forEach(h::addColumnHolder);
		} catch (IntrospectionException e) {
			ThrowsUtil.hidden(e);
		}
		
	}
	
	private static Field findField(PropertyDescriptor des, Class<?> type) {
		for (Class<?> t = type; t != null; t = t.getSuperclass()) {
			try {
				Field field = t.getDeclaredField(des.getName());
				if (field != null && des.getPropertyType() //
					.isAssignableFrom(field.getType())) {
					return field;
				}
			} catch (NoSuchFieldException | SecurityException ignored) {}
		}
		return null;
	}
}
