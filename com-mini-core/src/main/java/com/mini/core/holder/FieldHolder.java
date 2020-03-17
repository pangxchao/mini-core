package com.mini.core.holder;

import com.mini.core.holder.jdbc.*;
import com.mini.core.holder.web.*;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.util.ThrowsUtil;
import com.mini.core.validate.ValidateUtil;

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

import static com.mini.core.validate.ValidateUtil.*;
import static java.lang.String.format;

public final class FieldHolder<T> implements Serializable {
	private final PropertyDescriptor descriptor;
	private final CreateAt create;
	private final UpdateAt update;
	private final Method setter;
	private final Method getter;
	private final Column column;
	private final Auto auto;
	private final Lock lock;
	private final Del del;
	private final Ref ref;
	private final Id id;
	// 参数验证相关
	private final IsMobilePhone isMobilePhone;
	private final IsNotBlank isNotBlank;
	private final IsNotNull isNotNull;
	private final IsRequire isRequire;
	private final IsChinese isChinese;
	private final IsLetter isLetter;
	private final IsMobile isMobile;
	private final IsNumber isNumber;
	private final IsIdCard isIdCard;
	private final IsPhone isPhone;
	private final IsEmail isEmail;
	private final IsRegex isRegex;
	private final Is $is;


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

		// IsMobilePhone注解属性
		isMobilePhone = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsMobilePhone.class); //
		}).orElse(null);
		// IsNotBlank注解属性
		isNotBlank = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsNotBlank.class); //
		}).orElse(null);
		// IsNotNull注解属性
		isNotNull = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsNotNull.class); //
		}).orElse(null);
		// IsRequire注解属性
		isRequire = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsRequire.class); //
		}).orElse(null);
		// IsChinese注解属性
		isChinese = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsChinese.class); //
		}).orElse(null);
		// IsLetter注解属性
		isLetter = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsLetter.class); //
		}).orElse(null);
		// IsMobile注解属性
		isMobile = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsMobile.class); //
		}).orElse(null);
		// IsNumber注解属性
		isNumber = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsNumber.class); //
		}).orElse(null);
		// IsIdCard注解属性
		isIdCard = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsIdCard.class); //
		}).orElse(null);
		// IsPhone注解属性
		isPhone = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsPhone.class); //
		}).orElse(null);
		// IsEmail注解属性
		isEmail = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsEmail.class); //
		}).orElse(null);
		// IsRegex注解属性
		isRegex = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(IsRegex.class); //
		}).orElse(null);
		// Include注解属性
		$is = Optional.ofNullable(field).map(f -> {
			return f.getAnnotation(Is.class); //
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
	 * 是否有 IsMobilePhone 注解
	 * @return true-是
	 */
	public final boolean hasIsMobilePhone() {
		return isMobilePhone != null;
	}

	/**
	 * 是否有 IsNotBlank 注解
	 * @return true-是
	 */
	public final boolean hasIsNotBlank() {
		return isNotBlank != null;
	}

	/**
	 * 是否有 IsNotNull 注解
	 * @return true-是
	 */
	public final boolean hasIsNotNull() {
		return isNotNull != null;
	}

	/**
	 * 是否有 IsRequire 注解
	 * @return true-是
	 */
	public final boolean hasIsRequire() {
		return isRequire != null;
	}

	/**
	 * 是否有 IsChinese 注解
	 * @return true-是
	 */
	public final boolean hasIsChinese() {
		return isChinese != null;
	}

	/**
	 * 是否有 IsLetter 注解
	 * @return true-是
	 */
	public final boolean hasIsLetter() {
		return isLetter != null;
	}

	/**
	 * 是否有 IsMobile 注解
	 * @return true-是
	 */
	public final boolean hasIsMobile() {
		return isMobile != null;
	}

	/**
	 * 是否有 IsNumber 注解
	 * @return true-是
	 */
	public final boolean hasIsNumber() {
		return isNumber != null;
	}

	/**
	 * 是否有 IsIdCard 注解
	 * @return true-是
	 */
	public final boolean hasIsIdCard() {
		return isIdCard != null;
	}

	/**
	 * 是否有 IsPhone 注解
	 * @return true-是
	 */
	public final boolean hasIsPhone() {
		return isPhone != null;
	}

	/**
	 * 是否有 IsEmail 注解
	 * @return true-是
	 */
	public final boolean hasIsEmail() {
		return isEmail != null;
	}

	/**
	 * 是否有 IsRegex 注解
	 * @return true-是
	 */
	public final boolean hasIsRegex() {
		return isRegex != null;
	}

	/**
	 * 是否有 Is 注解
	 * @return true-是
	 */
	public final boolean hasIs() {
		return $is != null;
	}

	public final FieldHolder<T> checkIsNotBlank(Object value) {
		if (!FieldHolder.this.hasIsNotBlank()) return this;
		isNotBlank((String) value, isNotBlank.error(),
				isNotBlank.message());
		return this;
	}

	public final FieldHolder<T> checkIsNotNull(Object value) {
		if (!FieldHolder.this.hasIsNotNull()) return this;
		ValidateUtil.isNotNull(value, isNotNull.error(),
				isNotNull.message());
		return this;
	}

	public final FieldHolder<T> checkIsRequire(Object value) {
		if (!FieldHolder.this.hasIsRequire()) return this;
		if (this.isRequire.require() || value != null) {
			isRequire((String) value, isRequire.error(),
					isRequire.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsChinese(Object value) {
		if (!FieldHolder.this.hasIsChinese()) return this;
		if (this.isChinese.require() || value != null) {
			isChinese((String) value, isChinese.error(),
					isChinese.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsLetter(Object value) {
		if (!FieldHolder.this.hasIsLetter()) return this;
		if (this.isLetter.require() || value != null) {
			isLetter((String) value, isLetter.error(),
					isLetter.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsMobile(Object value) {
		if (!FieldHolder.this.hasIsMobile()) return this;
		if (this.isMobile.require() || value != null) {
			isMobile((String) value, isMobile.error(),
					isMobile.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsMobilePhone(Object value) {
		if (!FieldHolder.this.hasIsMobilePhone()) return this;
		if (this.isMobilePhone.require() || value != null) {
			isMobilePhone((String) value, isMobilePhone.error(),
					isMobilePhone.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsNumber(Object value) {
		if (!FieldHolder.this.hasIsNumber()) return this;
		if (this.isNumber.require() || value != null) {
			isNumber((String) value, isNumber.error(),
					isNumber.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsIdCard(Object value) {
		if (!FieldHolder.this.hasIsIdCard()) return this;
		if (this.isIdCard.require() || value != null) {
			isIdCard((String) value, isIdCard.error(),
					isIdCard.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsPhone(Object value) {
		if (!FieldHolder.this.hasIsPhone()) return this;
		if (this.isPhone.require() || value != null) {
			isPhone((String) value, isPhone.error(),
					isPhone.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsEmail(Object value) {
		if (!FieldHolder.this.hasIsEmail()) return this;
		if (this.isEmail.require() || value != null) {
			isEmail((String) value, isEmail.error(),
					isEmail.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIsRegex(Object value) {
		if (!FieldHolder.this.hasIsRegex()) return this;
		if (this.isRegex.require() || value != null) {
			isRegex((String) value, isRegex.regex(), //
					isRegex.error(), isRegex.message());
		}
		return this;
	}

	public final FieldHolder<T> checkIs(Object value) {
		if (!FieldHolder.this.hasIs()) return this;
		if (this.$is.require() || value != null) {
			is(true, $is.error(), $is.message());
		}
		return this;
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
	 * @param builder  {@link SQLBuilder}
	 * @param consumer 获取字段值回调
	 */
	public final void whereId(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		Optional.ofNullable(this.id).ifPresent(lock -> {
			builder.where("%s = ?", getColumnName());
			consumer.accept(this);
		});
	}

	/**
	 * 添加相等条件限制
	 * @param builder  {@link SQLBuilder}
	 * @param consumer 获取字段值回调
	 */
	public final void whereLock(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		Optional.ofNullable(this.lock).ifPresent(lock -> {
			builder.where("%s = ?", getColumnName());
			consumer.accept(this);
		});
	}

	/**
	 * Insert Replace 添加修改字段键值
	 * @param builder  {@link SQLBuilder}
	 * @param consumer 获取字段值回调
	 */
	public final void values(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		builder.values(this.getColumnName());
		consumer.accept(this);
	}

	/**
	 * Update Set 字段键值
	 * @param builder  {@link SQLBuilder}
	 * @param consumer 获取字段值回调
	 */
	public final void set(SQLBuilder builder, Consumer<FieldHolder<T>> consumer) {
		builder.set("%s = ?", this.getColumnName());
		consumer.accept(this);
	}

	/**
	 * ON DUPLICATE KEY UPDATE 字段值
	 * @param builder {@link SQLBuilder}
	 */
	public final void onDuplicateKeyUpdate(SQLBuilder builder) {
		builder.onDuplicateKeyUpdate("%s = VALUES(%s)", //
				getColumnName(), getColumnName());
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
			} catch (NoSuchFieldException | SecurityException ignored) {
			}
		}
		return null;
	}
}
